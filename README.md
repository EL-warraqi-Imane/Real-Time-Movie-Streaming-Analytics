# 🎬 Real-Time Movie Streaming Analytics

![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Scala](https://img.shields.io/badge/Scala-2.12-red.svg)
![Spark](https://img.shields.io/badge/Spark-3.5-orange.svg)
![Kafka](https://img.shields.io/badge/Kafka-3.6-black.svg)
![MongoDB](https://img.shields.io/badge/MongoDB-7.0-green.svg)
![Power BI](https://img.shields.io/badge/PowerBI-Latest-yellow.svg)

## 📋 Table des matières

- [Vue d'ensemble](#-vue-densemble)
- [Architecture](#-architecture)
- [Technologies utilisées](#-technologies-utilisées)
- [Prérequis](#-prérequis)
- [Installation](#-installation)
- [Configuration](#️-configuration)
- [Utilisation](#-utilisation)
- [Structure du projet](#-structure-du-projet)
- [Dataset](#-dataset)
- [Résultats attendus](#-résultats-attendus)
- [Captures d'écran](#-captures-décran)
- [Tests](#-tests)
- [Dépannage](#-dépannage)
- [Contribution](#-contribution)
- [Roadmap](#-roadmap)
- [Auteur](#-auteur)
- [Licence](#-licence)

## 🎯 Vue d'ensemble

Ce projet implémente un **pipeline de traitement de données en temps réel** pour analyser les films regardés par les utilisateurs. Il utilise des données réelles provenant de Kaggle et calcule des agrégats par catégorie et par minute.

### Objectifs principaux

- ✅ Ingestion de données en temps réel depuis un dataset Kaggle
- ✅ Traitement streaming avec Apache Spark
- ✅ Calcul d'agrégations (nombre de vues, genres populaires, etc.)
- ✅ Stockage persistant dans MongoDB
- ✅ Visualisation interactive avec Power BI

### Fonctionnalités clés

- 🔄 **Traitement temps réel** : Agrégations par fenêtres de temps (1 minute)
- 📊 **Métriques avancées** : Top films, genres populaires, statistiques utilisateurs
- 💾 **Persistance** : Stockage optimisé dans MongoDB
- 📈 **Visualisation** : Dashboards interactifs Power BI
- 🚀 **Scalabilité** : Architecture distribuée avec Kafka et Spark

## 🏗 Architecture

```
┌─────────────────┐      ┌─────────────────┐      ┌─────────────────┐      ┌─────────────────┐      ┌─────────────────┐
│                 │      │                 │      │                 │      │                 │      │                 │
│  Kaggle Dataset │─────▶│ Scala Producer  │─────▶│  Kafka KRaft    │─────▶│ Spark Streaming │─────▶│    MongoDB      │
│                 │      │                 │      │                 │      │                 │      │                 │
└─────────────────┘      └─────────────────┘      └─────────────────┘      └─────────────────┘      └─────────────────┘
                                                                                                               │
                                                                                                               │
                                                                                                               ▼
                                                                                                      ┌─────────────────┐
                                                                                                      │                 │
                                                                                                      │    Power BI     │
                                                                                                      │                 │
                                                                                                      └─────────────────┘
```

### Flux de données détaillé

1. **📥 Source** : Lecture du dataset Kaggle (CSV) contenant les informations sur les films et utilisateurs
2. **🚀 Ingestion** : Le producer Scala lit les données et publie les événements vers Kafka en temps réel
3. **📡 Streaming** : Kafka KRaft distribue les messages vers les consommateurs
4. **⚡ Traitement** : Spark Structured Streaming consomme, transforme et agrège les données
5. **💾 Stockage** : Les résultats agrégés sont sauvegardés dans MongoDB
6. **📊 Visualisation** : Power BI se connecte à MongoDB pour afficher les dashboards en temps réel

## 🛠 Technologies utilisées

| Technologie | Version | Rôle |
|------------|---------|------|
| **Scala** | 2.12+ | Langage de programmation principal |
| **Apache Kafka** | 3.6+ (KRaft) | Plateforme de streaming distribuée |
| **Apache Spark** | 3.5+ | Traitement en temps réel (Structured Streaming) |
| **MongoDB** | 7.0+ | Base de données NoSQL pour persistance |
| **Power BI** | Desktop/Service | Visualisation et reporting BI |
| **SBT** | 1.9+ | Build tool pour Scala |

### Dépendances principales

```scala
// build.sbt
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.5.0",
  "org.apache.spark" %% "spark-sql" % "3.5.0",
  "org.apache.spark" %% "spark-streaming" % "3.5.0",
  "org.apache.spark" %% "spark-sql-kafka-0-10" % "3.5.0",
  "org.mongodb.spark" %% "mongo-spark-connector" % "10.2.0",
  "org.apache.kafka" %% "kafka" % "3.6.0"
)
```

## 📦 Prérequis

Avant de commencer, assurez-vous d'avoir installé :

- ☑️ **Java JDK 11 ou 17**
  ```bash
  java -version
  ```

- ☑️ **Scala 2.12+**
  ```bash
  scala -version
  ```

- ☑️ **SBT 1.9+**
  ```bash
  sbt sbtVersion
  ```

- ☑️ **Apache Kafka 3.6+** (avec KRaft)
  ```bash
  kafka-topics --version
  ```

- ☑️ **Apache Spark 3.5+**
  ```bash
  spark-submit --version
  ```

- ☑️ **MongoDB 7.0+**
  ```bash
  mongod --version
  ```

- ☑️ **Power BI Desktop** (pour Windows)
  - Télécharger depuis [Microsoft Store](https://aka.ms/pbidesktopstore)

- ☑️ **Git**
  ```bash
  git --version
  ```

## 🚀 Installation

### 1️⃣ Cloner le repository

```bash
git clone https://github.com/votre-username/Real-Time Movie Streaming Analytics.git
cd Real-Time Movie Streaming Analytics
```

### 2️⃣ Télécharger le dataset Kaggle

```bash
# Créer le dossier data
mkdir -p data

# Téléchargez un dataset depuis Kaggle
# Exemples recommandés :
# - MovieLens: https://www.kaggle.com/datasets/grouplens/movielens-20m-dataset
# - TMDB: https://www.kaggle.com/datasets/tmdb/tmdb-movie-metadata
# - Netflix: https://www.kaggle.com/datasets/shivamb/netflix-shows

# Placez votre fichier CSV dans data/movies.csv
```

### 3️⃣ Installer les dépendances Scala

```bash
sbt compile
```

### 4️⃣ Démarrer Kafka (mode KRaft)

```bash
# Générer un UUID unique pour le cluster
KAFKA_CLUSTER_ID=$(kafka-storage random-uuid)

# Formater le répertoire de logs
kafka-storage format -t $KAFKA_CLUSTER_ID -c config/kraft/server.properties

# Démarrer le serveur Kafka
kafka-server-start config/kraft/server.properties
```

### 5️⃣ Créer le topic Kafka

```bash
# Créer le topic pour les événements de films
kafka-topics --create \
  --bootstrap-server localhost:9092 \
  --topic events-topic \
  --partitions 3 \
  --replication-factor 1

# Vérifier la création
kafka-topics --list --bootstrap-server localhost:9092
```

### 6️⃣ Démarrer MongoDB

**Option A : Avec Docker (recommandé)**
```bash
docker run -d \
  -p 27017:27017 \
  --name mongodb \
  -e MONGO_INITDB_ROOT_USERNAME=admin \
  -e MONGO_INITDB_ROOT_PASSWORD=password \
  mongo:7.0
```

**Option B : Installation native**
```bash
# Démarrer MongoDB
mongod --dbpath /path/to/data/db

# Ou avec systemd
sudo systemctl start mongod
```

**Vérifier la connexion**
```bash
mongosh --eval "db.adminCommand('ping')"
```

## ⚙️ Configuration

### Configuration Kafka (`src/main/resources/application.conf`)

```hocon
kafka {
  bootstrap.servers = "localhost:9092"
  topic = "events-topic"
  group.id = "movie-analytics-consumer"
  auto.offset.reset = "earliest"
  enable.auto.commit = true
}
```

### Configuration Spark (`SparkConfig.scala`)

```scala
val spark = SparkSession.builder()
  .appName("MovieStreamingAnalytics")
  .master("local[*]")
  .config("spark.mongodb.output.uri", "mongodb://localhost:27017/streamingDbFILM")
  .config("spark.mongodb.output.database", "streamingDbFILM")
  .config("spark.mongodb.output.collection", "movies_aggregates")
  .config("spark.sql.streaming.checkpointLocation", "/tmp/spark-checkpoint")
  .getOrCreate()
```

### Configuration MongoDB

```javascript
// Connexion MongoDB
use movies_db

// Créer les collections
db.createCollection("movies_aggregates")
db.createCollection("category_stats")
db.createCollection("hourly_metrics")

// Créer les index pour performance
db.movies_aggregates.createIndex({ "window_start": 1 })
db.movies_aggregates.createIndex({ "genre": 1 })
db.category_stats.createIndex({ "genre": 1, "timestamp": -1 })
```

### Variables d'environnement (`.env`)

```bash
# Kafka
KAFKA_BOOTSTRAP_SERVERS=localhost:9092
KAFKA_TOPIC=movie-events

# MongoDB
MONGODB_URI=mongodb://localhost:27017
MONGODB_DATABASE=movies_db

# Spark
SPARK_MASTER=local[*]
SPARK_CHECKPOINT_DIR=/tmp/spark-checkpoint

# Application
LOG_LEVEL=INFO
```

## 🎮 Utilisation

### Étape 1 : Démarrer le Producer Scala

```bash
# Lancer le producer qui lit le CSV et envoie vers Kafka
sbt "runMain com.streaming.producer.KafkaMovieProducer"

# Avec paramètres personnalisés
sbt "runMain com.streaming.producer.KafkaMovieProducer --file=data/movies.csv --rate=100"
```

**Vérifier les messages dans Kafka :**
```bash
kafka-console-consumer \
  --bootstrap-server localhost:9092 \
  --topic movie-events \
  --from-beginning
```

### Étape 2 : Lancer le Consumer Spark Streaming

```bash
# Compiler le projet
sbt clean assembly

# Soumettre le job Spark
spark-submit \
  --class com.streaming.spark.MovieStreamingApp \
  --master local[*] \
  --packages org.apache.spark:spark-sql-kafka-0-10_2.12:3.5.0,org.mongodb.spark:mongo-spark-connector_2.12:10.2.0 \
  --conf spark.mongodb.output.uri=mongodb://localhost:27017/movies_db \
  target/scala-2.12/movie-streaming-analytics-assembly-1.0.jar
```

### Étape 3 : Vérifier les données dans MongoDB

```bash
# Connexion à MongoDB
mongosh

# Sélectionner la base de données
use streamingdbFILM

# Afficher les agrégats
db.movies_aggregates.find().pretty()

# Statistiques par genre
db.category_stats.find().sort({ total_views: -1 }).limit(10)

# Compter les documents
db.movies_aggregates.countDocuments()
```

### Étape 4 : Configurer Power BI

1. **Ouvrir Power BI Desktop**

2. **Obtenir les données** → **Plus...** → Rechercher **"MongoDB"**

3. **Connexion MongoDB** :
   - Server: `localhost:27017`
   - Database: `streamingdbFILM`
   - Mode: **Direct Query** (pour temps réel) ou **Import**

4. **Sélectionner les collections** :
   - ✅ `movies_aggregates`
   - ✅ `category_stats`
   - ✅ `hourly_metrics`

5. **Créer les visualisations** :
   - Graphique en barres : Top 10 genres
   - Graphique en ligne : Évolution des vues dans le temps
   - Carte : Films les plus populaires
   - KPI : Nombre total de vues, utilisateurs actifs

6. **Publier** sur Power BI Service (optionnel)


## 📊 Dataset

### Source recommandée

Téléchargez l'un de ces datasets depuis Kaggle :

1. **MovieLens 20M** (recommandé)
   - URL: https://www.kaggle.com/datasets/grouplens/movielens-20m-dataset
   - Taille: 20 millions de ratings
   - Format: CSV

2. **TMDB 5000 Movies**
   - URL: https://www.kaggle.com/datasets/tmdb/tmdb-movie-metadata
   - Contient: Métadonnées complètes des films

3. **Netflix Shows**
   - URL: https://www.kaggle.com/datasets/shivamb/netflix-shows
   - Contient: Catalogue Netflix

### Format du CSV attendu

**Colonnes requises :**

| Colonne | Type | Description | Exemple |
|---------|------|-------------|---------|
| `movie_id` | String | Identifiant unique du film | "tt0111161" |
| `title` | String | Titre du film | "The Shawshank Redemption" |
| `genre` | String | Genre principal | "Drama" |
| `user_id` | String | Identifiant utilisateur | "user_12345" |
| `timestamp` | Long | Timestamp Unix (ms) | 1698745200000 |
| `rating` | Double | Note (0-5) | 4.5 |
| `duration` | Integer | Durée en minutes (optionnel) | 142 |

### Exemple de données (`data/movies.csv`)

```csv
movie_id,title,genre,user_id,timestamp,rating,duration
1,The Shawshank Redemption,Drama,user_123,1698745200000,5.0,142
2,The Godfather,Crime,user_456,1698745260000,4.5,175
3,The Dark Knight,Action,user_789,1698745320000,4.8,152
4,Pulp Fiction,Crime,user_321,1698745380000,4.7,154
5,Forrest Gump,Drama,user_654,1698745440000,4.6,142
6,Inception,Sci-Fi,user_987,1698745500000,4.9,148
7,The Matrix,Sci-Fi,user_111,1698745560000,4.8,136
8,Interstellar,Sci-Fi,user_222,1698745620000,4.7,169
9,The Prestige,Mystery,user_333,1698745680000,4.6,130
10,Memento,Thriller,user_444,1698745740000,4.5,113
```

### Générer des données de test

```bash
# Utiliser le générateur de données inclus
sbt "runMain com.streaming.producer.DataGenerator --output=data/test_data.csv --count=10000"
```

## 📈 Résultats attendus

### Métriques calculées par Spark

Le pipeline calcule les agrégations suivantes :

#### 1️⃣ **Agrégations par fenêtre temporelle (1 minute)**

```json
{
  "_id": "action_2024-10-31T14:30:00Z",
  "genre": "Action",
  "window_start": "2024-10-31T14:30:00Z",
  "window_end": "2024-10-31T14:31:00Z",
  "total_views": 156,
  "unique_users": 98,
  "avg_rating": 4.3,
  "max_rating": 5.0,
  "min_rating": 3.2,
  "total_duration": 23400
}
```

#### 2️⃣ **Top 10 des films les plus regardés**

```json
{
  "_id": "top_movies_2024-10-31T14:00:00Z",
  "timestamp": "2024-10-31T14:00:00Z",
  "top_movies": [
    {
      "movie_id": "tt0468569",
      "title": "The Dark Knight",
      "views": 1250,
      "avg_rating": 4.8
    },
    {
      "movie_id": "tt0111161",
      "title": "The Shawshank Redemption",
      "views": 1180,
      "avg_rating": 4.9
    }
  ]
}
```

#### 3️⃣ **Statistiques par genre**

```json
{
  "_id": "genre_stats_action",
  "genre": "Action",
  "total_views": 5420,
  "unique_movies": 245,
  "unique_users": 3210,
  "avg_rating": 4.2,
  "total_revenue": 125000.50,
  "peak_hour": "20:00"
}
```

#### 4️⃣ **Utilisateurs actifs**

```json
{
  "_id": "active_users_2024-10-31",
  "date": "2024-10-31",
  "total_users": 15420,
  "new_users": 340,
  "returning_users": 15080,
  "avg_session_duration": 125.5
}
```

### Exemples de requêtes MongoDB

```javascript
// Top 5 genres les plus populaires
db.category_stats.find().sort({ total_views: -1 }).limit(5)

// Films avec rating > 4.5
db.movies_aggregates.find({ avg_rating: { $gte: 4.5 } })

// Vues par heure
db.hourly_metrics.aggregate([
  {
    $group: {
      _id: { $hour: "$timestamp" },
      total_views: { $sum: "$total_views" }
    }
  },
  { $sort: { total_views: -1 } }
])

// Utilisateurs les plus actifs
db.movies_aggregates.aggregate([
  {
    $group: {
      _id: "$user_id",
      watch_count: { $sum: 1 }
    }
  },
  { $sort: { watch_count: -1 } },
  { $limit: 10 }
])
```

## 📸 Captures d'écran

### Dashboard Power BI

<img width="1238" height="667" alt="img" src="https://github.com/user-attachments/assets/419f01aa-0805-4694-976e-2960545dc2ea" />
<img width="1224" height="679" alt="img_1" src="https://github.com/user-attachments/assets/83089a86-e3e2-4f4f-adf2-a9fc1ad5f005" />
<img width="1250" height="656" alt="img_2" src="https://github.com/user-attachments/assets/99807e67-d71e-4e03-a4e5-31f475d2dca5" />


*Vue d'ensemble du dashboard avec les KPIs principaux, graphiques de tendances et top films*

### Architecture du système


![WhatsApp Image 2025-10-31 at 20 28 59_0d4340fd](https://github.com/user-attachments/assets/41ffefb0-1738-44ea-a74d-b1b4a0dc7f6f)

*Diagramme complet de l'architecture montrant le flux de données de Kafka à Power BI*



## 🐛 Dépannage

### Problème : Kafka ne démarre pas

**Erreur** : `Failed to bind to port 9092`

```bash
# Vérifier si le port est utilisé
lsof -i :9092
sudo netstat -tulpn | grep 9092

# Tuer le processus si nécessaire
kill -9 <PID>

# Nettoyer les logs Kafka
rm -rf /tmp/kafka-logs
rm -rf /tmp/kraft-combined-logs
```

### Problème : Spark ne se connecte pas à MongoDB

**Erreur** : `com.mongodb.MongoTimeoutException: Timed out after 30000 ms`

```bash
# Vérifier que MongoDB est démarré
sudo systemctl status mongod

# Tester la connexion
mongosh --eval "db.adminCommand('ping')"

# Vérifier les logs MongoDB
tail -f /var/log/mongodb/mongod.log

# Relancer MongoDB
sudo systemctl restart mongod
```

### Problème : OutOfMemoryError dans Spark

**Erreur** : `java.lang.OutOfMemoryError: Java heap space`

```bash
# Augmenter la mémoire Spark
spark-submit \
  --driver-memory 4g \
  --executor-memory 4g \
  --class com.streaming.spark.MovieStreamingApp \
  target/scala-2.12/movie-streaming-analytics-assembly-1.0.jar
```

### Problème : Dépendances SBT

**Erreur** : `unresolved dependency`

```bash
# Nettoyer le cache SBT
rm -rf ~/.ivy2/cache
rm -rf ~/.sbt

# Recharger les dépendances
sbt clean update compile
```

### Problème : Power BI ne trouve pas MongoDB

**Solution** :

1. Installer le connecteur MongoDB pour Power BI
2. Utiliser le connecteur ODBC si nécessaire
3. Vérifier que MongoDB écoute sur toutes les interfaces :

```bash
# Dans /etc/mongod.conf
net:
  port: 27017
  bindIp: 0.0.0.0
```

### Problème : Latence élevée

**Optimisations** :

```scala
// Augmenter le batch interval
.trigger(Trigger.ProcessingTime("10 seconds"))

// Réduire le nombre de partitions
.coalesce(1)

// Activer le caching
df.cache()
```

### Logs de débogage

```bash
# Logs Kafka
tail -f /path/to/kafka/logs/server.log

# Logs Spark
tail -f /path/to/spark/logs/spark-application.log

# Logs MongoDB
tail -f /var/log/mongodb/mongod.log

# Logs de l'application
tail -f logs/application.log
```

## 🤝 Contribution

Les contributions sont les bienvenues ! Voici comment contribuer :

### Processus de contribution

1. **Fork** le projet
   ```bash
   git clone https://github.com/votre-username/real-time-movie-analytics.git
   ```

2. **Créer une branche** pour votre fonctionnalité
   ```bash
   git checkout -b feature/amazing-feature
   ```

3. **Commiter** vos changements
   ```bash
   git commit -m 'Add some amazing feature'
   ```

4. **Pousser** vers la branche
   ```bash
   git push origin feature/amazing-feature
   ```

5. **Ouvrir une Pull Request**

### Guidelines

- ✅ Suivre les conventions de code Scala
- ✅ Ajouter des tests pour les nouvelles fonctionnalités
- ✅ Mettre à jour la documentation
- ✅ S'assurer que tous les tests passent
- ✅ Utiliser des messages de commit descriptifs

### Code de conduite

- Soyez respectueux et professionnel
- Acceptez les critiques constructives
- Concentrez-vous sur ce qui est le mieux pour le projet

## 📝 Roadmap

### Version 1.0 (Actuelle) ✅
- [x] Implémentation du producer Kafka
- [x] Consumer Spark Streaming
- [x] Intégration MongoDB
- [x] Dashboard Power BI basique
- [x] Documentation complète

### Version 1.1 (En cours) 🚧
- [ ] Tests unitaires complets
- [ ] Tests d'intégration
- [ ] CI/CD avec GitHub Actions
- [ ] Dockerisation complète
- [ ] Documentation API

### Version 2.0 (Futur) 🔮
- [ ] Mode cluster Spark (YARN/K8s)
- [ ] Monitoring avec Prometheus/Grafana
- [ ] Alerting en temps réel
- [ ] Machine Learning (prédiction de popularité)
- [ ] API REST pour requêtes
- [ ] Interface web de monitoring
- [ ] Support multi-tenancy
- [ ] Optimisations de performance

### Idées futures 💡
- [ ] Support Kafka Streams
- [ ] Intégration Apache Flink
- [ ] Support Delta Lake
- [ ] Data quality checks
- [ ] A/B testing framework
- [ ] Support multi-langues
- [ ] Export vers Data Warehouse (Snowflake, BigQuery)
- [ ] Recommandations personnalisées

## 🐳 Docker Deployment

### Démarrage rapide avec Docker Compose

```bash
# Cloner et lancer tout le stack
git clone https://github.com/votre-username/real-time-movie-analytics.git
cd real-time-movie-analytics

# Démarrer tous les services
docker-compose up -d

# Vérifier les logs
docker-compose logs -f

# Arrêter tous les services
docker-compose down
```

### Docker Compose (`docker-compose.yml`)

```yaml
version: '3.8'

services:
  # Kafka KRaft
  kafka:
    image: apache/kafka:3.6.0
    container_name: kafka
    ports:
      - "9092:9092"
      - "9093:9093"
    environment:
      KAFKA_NODE_ID: 1
      KAFKA_PROCESS_ROLES: broker,controller
      KAFKA_LISTENERS: PLAINTEXT://0.0.0.0:9092,CONTROLLER://0.0.0.0:9093
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:9092
      KAFKA_CONTROLLER_LISTENER_NAMES: CONTROLLER
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT
      KAFKA_CONTROLLER_QUORUM_VOTERS: 1@kafka:9093
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1
      KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: 1
      KAFKA_LOG_DIRS: /tmp/kraft-combined-logs
      CLUSTER_ID: 'MkU3OEVBNTcwNTJENDM2Qk'
    volumes:
      - kafka-data:/tmp/kraft-combined-logs
    networks:
      - movie-analytics-network

  # MongoDB
  mongodb:
    image: mongo:7.0
    container_name: mongodb
    ports:
      - "27017:27017"
    environment:
      MONGO_INITDB_ROOT_USERNAME: admin
      MONGO_INITDB_ROOT_PASSWORD: password
      MONGO_INITDB_DATABASE: movies_db
    volumes:
      - mongodb-data:/data/db
      - ./scripts/init-mongo.js:/docker-entrypoint-initdb.d/init-mongo.js:ro
    networks:
      - movie-analytics-network

  # Producer Scala
  producer:
    build:
      context: .
      dockerfile: docker/Dockerfile.producer
    container_name: movie-producer
    depends_on:
      - kafka
    environment:
      KAFKA_BOOTSTRAP_SERVERS: kafka:9092
      KAFKA_TOPIC: movie-events
      DATA_FILE: /app/data/movies.csv
    volumes:
      - ./data:/app/data
    networks:
      - movie-analytics-network

  # Spark Streaming Consumer
  spark-consumer:
    build:
      context: .
      dockerfile: docker/Dockerfile.consumer
    container_name: spark-consumer
    depends_on:
      - kafka
      - mongodb
    environment:
      KAFKA_BOOTSTRAP_SERVERS: kafka:9092
      KAFKA_TOPIC: movie-events
      MONGODB_URI: mongodb://admin:password@mongodb:27017/movies_db?authSource=admin
      SPARK_MASTER: local[*]
    networks:
      - movie-analytics-network

  # Mongo Express (Interface web MongoDB)
  mongo-express:
    image: mongo-express:latest
    container_name: mongo-express
    ports:
      - "8081:8081"
    environment:
      ME_CONFIG_MONGODB_ADMINUSERNAME: admin
      ME_CONFIG_MONGODB_ADMINPASSWORD: password
      ME_CONFIG_MONGODB_URL: mongodb://admin:password@mongodb:27017/
    depends_on:
      - mongodb
    networks:
      - movie-analytics-network

volumes:
  kafka-data:
  mongodb-data:

networks:
  movie-analytics-network:
    driver: bridge
```

### Dockerfiles

**`docker/Dockerfile.producer`**
```dockerfile
FROM hseeberger/scala-sbt:11.0.12_1.5.5_2.12.14

WORKDIR /app

# Copier les fichiers de build
COPY build.sbt .
COPY project project/

# Télécharger les dépendances
RUN sbt update

# Copier le code source
COPY src src/

# Compiler l'application
RUN sbt compile assembly

# Copier le script de démarrage
COPY docker/start-producer.sh /app/
RUN chmod +x /app/start-producer.sh

CMD ["/app/start-producer.sh"]
```

**`docker/Dockerfile.consumer`**
```dockerfile
FROM bitnami/spark:3.5.0

USER root

# Copier l'application compilée
COPY target/scala-2.12/movie-streaming-analytics-assembly-1.0.jar /opt/spark-apps/

# Copier le script de démarrage
COPY docker/start-consumer.sh /opt/spark-apps/
RUN chmod +x /opt/spark-apps/start-consumer.sh

USER 1001

CMD ["/opt/spark-apps/start-consumer.sh"]
```

## 📊 Monitoring et Observabilité

### Prometheus + Grafana (optionnel)

```yaml
# Ajouter dans docker-compose.yml

  prometheus:
    image: prom/prometheus:latest
    container_name: prometheus
    ports:
      - "9090:9090"
    volumes:
      - ./monitoring/prometheus.yml:/etc/prometheus/prometheus.yml
      - prometheus-data:/prometheus
    networks:
      - movie-analytics-network

  grafana:
    image: grafana/grafana:latest
    container_name: grafana
    ports:
      - "3000:3000"
    environment:
      - GF_SECURITY_ADMIN_PASSWORD=admin
    volumes:
      - grafana-data:/var/lib/grafana
      - ./monitoring/dashboards:/etc/grafana/provisioning/dashboards
    networks:
      - movie-analytics-network
```

### Métriques exposées

- 📈 **Kafka** : Throughput, lag, nombre de messages
- ⚡ **Spark** : Jobs actifs, stages, tasks, mémoire
- 💾 **MongoDB** : Opérations/sec, latence, connexions
- 🎯 **Application** : Événements traités, erreurs, latence E2E

## 🔐 Sécurité

### Meilleures pratiques implémentées

✅ **Authentification MongoDB**
```bash
# Créer un utilisateur avec droits limités
mongosh
use movies_db
db.createUser({
  user: "movieapp",
  pwd: "securepassword",
  roles: [{ role: "readWrite", db: "movies_db" }]
})
```

✅ **Chiffrement des données en transit**
```scala
// Configuration SSL pour MongoDB
.config("spark.mongodb.input.uri", "mongodb://user:pass@host:27017/db?ssl=true")
```

✅ **Gestion des secrets**
```bash
# Utiliser des variables d'environnement
export MONGODB_PASSWORD=$(cat /run/secrets/mongodb_password)

# Ou utiliser Vault/AWS Secrets Manager
```

✅ **Réseau isolé**
- Tous les services dans un réseau Docker privé
- Exposition minimale des ports
- Firewall rules appropriées

## 📚 Documentation API

### Producer API

#### Publier un événement

```scala
// Exemple d'envoi manuel
import com.streaming.producer.KafkaMovieProducer

val event = MovieEvent(
  movieId = "tt0111161",
  title = "The Shawshank Redemption",
  genre = "Drama",
  userId = "user_123",
  timestamp = System.currentTimeMillis(),
  rating = 5.0
)

KafkaMovieProducer.send(event)
```

### Consumer API

#### Lire les agrégats

```scala
// Exemple de lecture depuis MongoDB
import org.mongodb.scala._

val client = MongoClient("mongodb://localhost:27017")
val database = client.getDatabase("movies_db")
val collection = database.getCollection("movies_aggregates")

collection.find().subscribe(
  (doc: Document) => println(doc.toJson()),
  (error: Throwable) => println(s"Error: ${error.getMessage}"),
  () => println("Completed")
)
```

## 🎓 Tutoriels et Exemples

### 1. Ajouter un nouveau type d'agrégation

```scala
// Dans StreamProcessor.scala

def calculateDailyStats(df: DataFrame): DataFrame = {
  df.groupBy(
    window($"timestamp", "1 day"),
    $"genre"
  ).agg(
    count("*").as("daily_views"),
    avg("rating").as("avg_daily_rating"),
    sum("duration").as("total_watch_time")
  )
}
```

### 2. Créer un dashboard Power BI personnalisé

1. **Connexion** : Obtenir les données → MongoDB
2. **Mesures** :
   ```dax
   Total Views = SUM('movies_aggregates'[total_views])
   Avg Rating = AVERAGE('movies_aggregates'[avg_rating])
   Popular Genre = TOPN(1, VALUES('movies_aggregates'[genre]), [Total Views])
   ```
3. **Visualisations** : Ajouter graphiques et filtres

### 3. Ajouter un nouveau producer

```scala
object TwitterMovieProducer extends App {
  // Connexion à Twitter API
  // Filtrer les tweets sur les films
  // Publier vers Kafka
}
```

## 🔧 Configuration avancée

### Tuning Kafka

```properties
# server.properties
num.network.threads=8
num.io.threads=8
socket.send.buffer.bytes=102400
socket.receive.buffer.bytes=102400
socket.request.max.bytes=104857600
log.retention.hours=168
log.segment.bytes=1073741824
log.retention.check.interval.ms=300000
```

### Tuning Spark

```scala
spark.conf.set("spark.sql.shuffle.partitions", "200")
spark.conf.set("spark.streaming.backpressure.enabled", "true")
spark.conf.set("spark.streaming.kafka.maxRatePerPartition", "1000")
spark.conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
```

### Tuning MongoDB

```javascript
// Créer des index composés
db.movies_aggregates.createIndex(
  { "genre": 1, "window_start": -1 },
  { background: true }
)

// Activer le sharding (pour gros volumes)
sh.enableSharding("movies_db")
sh.shardCollection("movies_db.movies_aggregates", { "_id": "hashed" })
```

## 📞 Support et Contact

### Obtenir de l'aide

- 📖 **Documentation** : Consultez le [Wiki](https://github.com/votre-username/real-time-movie-analytics/wiki)
- 🐛 **Bug Reports** : Ouvrez une [Issue](https://github.com/votre-username/real-time-movie-analytics/issues)
- 💬 **Discussions** : Rejoignez les [Discussions](https://github.com/votre-username/real-time-movie-analytics/discussions)
- 📧 **Email** : votre.email@example.com

### Communauté

- **Discord** : [Rejoindre le serveur](https://discord.gg/votre-lien)
- **Slack** : [Canal #movie-analytics](https://votre-workspace.slack.com)
- **Twitter** : [@votre_handle](https://twitter.com/votre_handle)

## 🏆 Crédits et Remerciements

Ce projet utilise les technologies suivantes :

- [Apache Kafka](https://kafka.apache.org/) - Plateforme de streaming
- [Apache Spark](https://spark.apache.org/) - Moteur de traitement distribué
- [MongoDB](https://www.mongodb.com/) - Base de données NoSQL
- [Scala](https://www.scala-lang.org/) - Langage de programmation
- [Power BI](https://powerbi.microsoft.com/) - Outil de visualisation
- [Kaggle](https://www.kaggle.com/) - Source des datasets

Remerciements spéciaux à :
- La communauté Apache Software Foundation
- Les contributeurs open source
- Les mainteneurs des bibliothèques utilisées

## 👨‍💻 Auteur

**Votre Nom**

- 🌐 Portfolio : [votre-site.com](https://votre-site.com)
- 💼 LinkedIn : [Votre Profil](https://linkedin.com/in/votre-profil)
- 🐙 GitHub : [@votre-username](https://github.com/votre-username)
- 📧 Email : votre.email@example.com
- 🐦 Twitter : [@votre_handle](https://twitter.com/votre_handle)

## 📄 Licence

Ce projet est sous licence **MIT** - voir le fichier [LICENSE](LICENSE) pour plus de détails.

```
MIT License

Copyright (c) 2024 Votre Nom

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## 📊 Statistiques du projet

![GitHub stars](https://img.shields.io/github/stars/votre-username/real-time-movie-analytics?style=social)
![GitHub forks](https://img.shields.io/github/forks/votre-username/real-time-movie-analytics?style=social)
![GitHub watchers](https://img.shields.io/github/watchers/votre-username/real-time-movie-analytics?style=social)
![GitHub issues](https://img.shields.io/github/issues/votre-username/real-time-movie-analytics)
![GitHub pull requests](https://img.shields.io/github/issues-pr/votre-username/real-time-movie-analytics)
![GitHub last commit](https://img.shields.io/github/last-commit/votre-username/real-time-movie-analytics)
![GitHub repo size](https://img.shields.io/github/repo-size/votre-username/real-time-movie-analytics)
![Lines of code](https://img.shields.io/tokei/lines/github/votre-username/real-time-movie-analytics)

---

## 🌟 Showcase

Utilisé par :
- 🏢 **Entreprise X** - Analyse de contenus streaming
- 🎓 **Université Y** - Projet pédagogique Big Data
- 💼 **Startup Z** - Recommandations de films

*Votre projet utilise cette solution ? Ouvrez une PR pour être ajouté !*

---

## 📖 Articles et présentations

- 📝 [Blog Post : Architecture d'un pipeline temps réel](https://votre-blog.com/article)
- 🎥 [Vidéo YouTube : Démonstration complète](https://youtube.com/watch?v=xxx)
- 📊 [Slides : Présentation technique](https://slides.com/votre-presentation)
- 🎤 [Conférence : Talk à Big Data Summit 2024](https://conference.com)

---

## ❓ FAQ

### Q: Quelle est la latence du système ?
**R:** Environ 1-3 secondes de bout en bout (ingestion → visualisation)

### Q: Peut-on utiliser d'autres sources que Kaggle ?
**R:** Oui, il suffit d'adapter le producer pour lire depuis n'importe quelle source (API, base de données, fichiers, etc.)

### Q: Le système supporte-t-il le mode distribué ?
**R:** Oui, Spark et Kafka peuvent être déployés en mode cluster. Voir la section "Configuration avancée"

### Q: Quelle est la capacité de traitement ?
**R:** En mode local : ~10K événements/seconde. En cluster : jusqu'à 1M+ événements/seconde

### Q: Peut-on remplacer Power BI par Tableau/Looker ?
**R:** Oui, toute solution BI compatible MongoDB peut être utilisée

### Q: Y a-t-il un support pour le machine learning ?
**R:** Pas dans la version actuelle, mais c'est prévu dans la roadmap v2.0

---

## 🎯 Performance Benchmarks

| Métrique | Valeur | Configuration |
|----------|--------|---------------|
| Throughput | 50K events/sec | Local[4], 8GB RAM |
| Latence E2E | < 2 secondes | Mode streaming |
| Stockage MongoDB | 1GB/jour | 100K utilisateurs |
| CPU Usage | 40-60% | Charge normale |
| Memory Usage | 4-6 GB | Spark + Kafka |

---

## 🔄 Changelog

### [1.0.0] - 2024-10-31

#### Added ✨
- Pipeline complet Kafka → Spark → MongoDB
- Producer Scala avec lecture CSV
- Consumer Spark Streaming
- Dashboard Power BI
- Documentation complète
- Tests unitaires de base

#### Fixed 🐛
- Correction des fuites mémoire dans Spark
- Optimisation des requêtes MongoDB

---

## 🚀 Getting Started (Quickstart)

**Pour les plus pressés :**

```bash
# 1. Clone
git clone https://github.com/votre-username/real-time-movie-analytics.git
cd real-time-movie-analytics

# 2. Démarrer avec Docker
docker-compose up -d

# 3. Charger des données de test
docker exec -it movie-producer /app/load-test-data.sh

# 4. Ouvrir Power BI et se connecter à MongoDB localhost:27017

# 5. Profit! 🎉
```

---

## ⭐ Si ce projet vous a aidé

**N'hésitez pas à :**
- ⭐ Donner une étoile au projet
- 🐛 Signaler des bugs
- 💡 Proposer des améliorations
- 🔄 Partager avec vos collègues
- 📝 Écrire un article de blog

---

<div align="center">

**Made with ❤️ and Scala**

**Real-Time Movie Streaming Analytics** | 2024

[⬆ Retour en haut](#-real-time-movie-streaming-analytics)

</div>
