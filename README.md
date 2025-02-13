# Getting Started

docker exec -it kafka /usr/bin/kafka-topics --bootstrap-server localhost:9092 --list

docker exec -it kafka /usr/bin/kafka-consumer-groups --bootstrap-server localhost:9092 --all-groups --describe

