package com.example.ConductAssignment.service;

import com.example.ConductAssignment.model.Email;
import com.example.ConductAssignment.model.QueueData;
import com.example.ConductAssignment.model.Policy;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmailProcessingService {
  private final MongoDbService mongoDbService;
  private final VectorScanService vectorScanService;

  public EmailProcessingService(MongoDbService mongoDbService, VectorScanService vectorScanService) {
    this.mongoDbService = mongoDbService;
    this.vectorScanService = vectorScanService;
  }

  public Map<String, List<String>> processEmail(Email email) {
    Map<String, Boolean> policyCache = new HashMap<>(); // Cache for policy execution results
    Map<String, List<String>> results = new HashMap<>();
    List<QueueData> queues = mongoDbService.getQueuesForEmail(email.getRecipients());

    for (QueueData queue : queues) {
      boolean isIgnored = false;
      List<String> matchedFlags = new ArrayList<>();

      // Process ignore and flag policies
      for (Policy policy : queue.getPolicies()) {
        boolean isMatched;

        // Check cache before executing policy
        if (policyCache.containsKey(policy.getPolicyId())) {
          isMatched = policyCache.get(policy.getPolicyId());
        } else {
          isMatched = vectorScanService.match(email.getContent(), policy.getRegex());
          policyCache.put(policy.getPolicyId(), isMatched);
        }

        if (isMatched) {
          if (policy.getType().equals("ignore")) {
            isIgnored = true;
            break; // Stop processing this queue if an ignore policy matches
          } else if (policy.getType().equals("flag")) {
            matchedFlags.add(policy.getPolicyId());
          }
        }
      }

      if (!isIgnored && !matchedFlags.isEmpty()) {
        results.put(queue.getQueueId(), matchedFlags);
      }
    }

    return results;
  }
}
