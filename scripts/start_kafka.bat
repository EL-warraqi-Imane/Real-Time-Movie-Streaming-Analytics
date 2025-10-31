#bin\windows\kafka-server-start.bat config\kraft\server.properties
#Producter kafka
bin\windows\kafka-topics.bat --create --topic events-topic --bootstrap-server localhost:9092



bin\windows\kafka-console-producer.bat --bootstrap-server localhost:9092 --topic events-topic