import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import scala.io.Source
import java.time.Instant
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import scala.util.Random

object Producer extends App {
  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](props)
  val rand = new Random()

  // Lire movies et ratings
  val movies = Source.fromFile("C:/Users/ELITEBOOK/IdeaProjects/SCALA/src/main/data/movies.csv").getLines().drop(1).map { line =>
    val cols = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1)
    (cols(0).toInt, (cols(1), cols(2))) // movieId -> (title, genres)
  }.toMap

  val ratings = Source.fromFile("C:/Users/ELITEBOOK/IdeaProjects/SCALA/src/main/data/ratings.csv").getLines().drop(1).map { line =>
    val cols = line.split(",")
    (cols(0).toInt, cols(1).toInt, cols(2).toDouble, cols(3).toLong) // userId, movieId, rating, timestamp
  }.toList

  println("Producteur Kafka démarré avec données réelles...")

  while(true) {
    val evt = ratings(rand.nextInt(ratings.size))
    val movie = movies(evt._2)
    val title = movie._1
    val genres = movie._2.split("\\|")
    val firstGenre = genres(0)
    val duration = rand.nextInt(101) + 80  // durée aléatoire entre 80 et 180 min

    // Construire le JSON
    val json =
      ("user_id" -> evt._1) ~
        ("movie_id" -> evt._2) ~
        ("title" -> title) ~
        ("category" -> firstGenre) ~
        ("genres" -> genres.toList) ~
        ("rating" -> evt._3) ~
        ("duration" -> duration) ~
        ("timestamp" -> Instant.now.toString)

    val jsonString = compact(render(json))
    val record = new ProducerRecord[String,String]("movie_events", jsonString)

    producer.send(record)
    println(s"Envoyé: $jsonString")

    Thread.sleep(500)
  }

  producer.close()
}
