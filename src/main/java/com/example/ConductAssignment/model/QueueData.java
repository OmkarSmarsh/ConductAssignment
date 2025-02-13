package com.example.ConductAssignment.model;

import java.util.List;

public class QueueData {
  private String queueId;
  private List<String> monitoredPopulation;
  private List<Policy> policies;

  public QueueData(String queueId, List<String> monitoredPopulation, List<Policy> policies) {
    this.queueId = queueId;
    this.monitoredPopulation = monitoredPopulation;
    this.policies = policies;
  }

  public String getQueueId() {
    return queueId;
  }

  public List<String> getMonitoredPopulation() {
    return monitoredPopulation;
  }

  public List<Policy> getPolicies() {
    return policies;
  }
}


