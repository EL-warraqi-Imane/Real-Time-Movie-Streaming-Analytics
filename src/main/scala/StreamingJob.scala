import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.streaming.Trigger
import com.mongodb.client.{MongoClients, MongoClient, MongoCollection, MongoDatabase}
import org.bson.Document
import scala.jdk.CollectionConverters._

object StreamingJob {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("MovieStreamingAnalytics")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    // ✅ Schéma du JSON reçu de Kafka
    val schema = new StructType()
      .add("user_id", IntegerType)
      .add("movie_id", IntegerType)
      .add("title", StringType)
      .add("category", StringType)
      .add("rating", DoubleType)
      .add("duration", IntegerType)
      .add("timestamp", StringType)

    // ✅ Lecture du flux Kafka
    val kafkaStream = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "movie_events")
      .option("startingOffsets", "latest")
      .load()

    // ✅ Parser le JSON et convertir timestamp
    val parsed = kafkaStream
      .selectExpr("CAST(value AS STRING) as json")
      .select(from_json($"json", schema).as("data"))
      .select("data.*")
      .withColumn("event_time", to_timestamp($"timestamp"))

    // ✅ Agrégations par fenêtre et catégorie
    val agg = parsed
      .withWatermark("event_time", "2 minutes")
      .groupBy(window($"event_time", "1 minute", "30 seconds"), $"category")
      .agg(
        count("*").alias("count_views"),
        sum("rating").alias("sum_rating"),
        avg("rating").alias("avg_rating"),
        sum("duration").alias("total_duration")
      )
      .select(
        $"window.start".alias("window_start"),
        $"window.end".alias("window_end"),
        $"category",
        $"count_views",
        $"sum_rating",
        $"avg_rating",
        $"total_duration"
      )

    // ✅ Écriture dans MongoDB via foreachBatch
    val query = agg.writeStream
      .foreachBatch { (batchDF: org.apache.spark.sql.DataFrame, batchId: Long) =>
        if (!batchDF.isEmpty) {
          var mongoClient: MongoClient = null
          try {
            mongoClient = MongoClients.create("mongodb://127.0.0.1:27017")
            val database: MongoDatabase = mongoClient.getDatabase("streamingDBFILM")
            val collection: MongoCollection[Document] = database.getCollection("movie_aggregates")

            // ✅ Conversion DataFrame → Documents MongoDB
            val documents = batchDF.collect().map { row =>
              new Document()
                .append("window_start", row.getAs[java.sql.Timestamp]("window_start"))
                .append("window_end", row.getAs[java.sql.Timestamp]("window_end"))
                .append("category", row.getAs[String]("category"))
                .append("count_views", row.getAs[Long]("count_views"))
                .append("sum_rating", row.getAs[Double]("sum_rating"))
                .append("avg_rating", row.getAs[Double]("avg_rating"))
                .append("total_duration", row.getAs[Double]("total_duration"))
                .append("batch_id", batchId)
                .append("processed_at", new java.util.Date())
            }.toList

            // ✅ Insertion dans MongoDB
            if (documents.nonEmpty) {
              collection.insertMany(documents.asJava)
              println(s"✅ Batch $batchId : ${documents.length} documents insérés dans MongoDB")
            }

          } catch {
            case e: Exception =>
              println(s"❌ Erreur lors de l'insertion (Batch $batchId): ${e.getMessage}")
              e.printStackTrace()
          } finally {
            if (mongoClient != null) mongoClient.close()
          }
        } else {
          println(s"⚠️  Batch $batchId est vide - Aucune donnée à insérer")
        }
      }
      .option("checkpointLocation", "checkpoints/mongodb")
      .trigger(Trigger.ProcessingTime("30 seconds"))
      .start()

    println("🚀 Streaming job démarré - En attente des données Kafka...")
    println("📊 Les agrégations seront écrites dans MongoDB toutes les 30 secondes")
    println("💾 Database: streamingDB, Collection: movie_aggregates")

    query.awaitTermination()
  }
}
