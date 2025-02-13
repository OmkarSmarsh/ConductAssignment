package com.example.ConductAssignment.service;

import com.example.ConductAssignment.model.QueueData;
import com.example.ConductAssignment.model.Policy;
import com.mongodb.client.*;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MongoDbService {
  private final MongoCollection<Document> queueCollection;
  private final MongoCollection<Document> policyCollection;

  public MongoDbService() {
    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    MongoDatabase database = mongoClient.getDatabase("policyDB");
    this.queueCollection = database.getCollection("queues");
    this.policyCollection = database.getCollection("policies");
  }

  public List<QueueData> getQueuesForEmail(List<String> recipients) {
    List<QueueData> matchedQueues = new ArrayList<>();

    for (Document queueDoc : queueCollection.find()) {
      String queueId = queueDoc.getString("queueId");
      List<String> monitoredPopulation = (List<String>) queueDoc.get("monitoredPopulation");
      List<String> policyIds = (List<String>) queueDoc.get("policies");

      // Check if the email's recipients match the queue's monitored population
      boolean isMonitored = recipients.stream().anyMatch(monitoredPopulation::contains);
      if (!isMonitored) continue;

      // Fetch full policy details from `policies` collection
      List<Policy> policies = new ArrayList<>();
      for (String policyId : policyIds) {
        Document policyDoc = policyCollection.find(new Document("policyId", policyId)).first();
        if (policyDoc != null) {
          policies.add(new Policy(
              policyDoc.getString("policyId"),
              policyDoc.getString("type"),
              policyDoc.getString("regex")
          ));
        }
      }

      matchedQueues.add(new QueueData(queueId, monitoredPopulation, policies));
    }
    return matchedQueues;
  }
}


