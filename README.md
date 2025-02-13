# Getting Started

### Commands to check kafka:
docker exec -it kafka /usr/bin/kafka-topics --bootstrap-server localhost:9092 --list

docker exec -it kafka /usr/bin/kafka-consumer-groups --bootstrap-server localhost:9092 --all-groups --describe

======================================
### API's

#### API to send all emails present in src/main/resources/emails/ to 'emails' kafka topic

GET http://localhost:8080/sendEmails

#### API to process single email against the queues

POST http://localhost:8080/process/email

Body:
{
"id": "email-001",
"subject": "Important Update",
"sender": "admin@example.com",
"recipients": ["user1@example.com", "user3@example.com"],
"content": "GITHUB | Please review the latest confidential changes."
}


#### Queue Matcher API

GET http://localhost:8080/queues/for-email?recipients=user1@example.com,user2@example.com


======================================

### Queues and Policies data is stored in MongoDB

db.policies.insertMany([
{ "policyId": "ignorePolicy1", "type": "ignore", "regex": "atlassian|github" },
{ "policyId": "ignorePolicy2", "type": "ignore", "regex": "datadog|kibana" },
{ "policyId": "ignorePolicy3", "type": "ignore", "regex": "mongodb|kafka" },

{ "policyId": "flagPolicy1", "type": "flag", "regex": "financial|sensitive" },
{ "policyId": "flagPolicy2", "type": "flag", "regex": "bank account|credit card" },
{ "policyId": "flagPolicy3", "type": "flag", "regex": "confidential|private" }
]);


db.queues.insertMany([
{
"queueId": "q1",
"monitoredPopulation": ["user1@example.com"],
"policies": ["ignorePolicy1", "flagPolicy1"]
},
{
"queueId": "q2",
"monitoredPopulation": ["user2@example.com"],
"policies": ["ignorePolicy1", "ignorePolicy2", "flagPolicy1", "flagPolicy2"]
},
{
"queueId": "q3",
"monitoredPopulation": ["user3@example.com"],
"policies": ["ignorePolicy1", "ignorePolicy2", "ignorePolicy3", "flagPolicy1", "flagPolicy2", "flagPolicy3"]
}
]);

