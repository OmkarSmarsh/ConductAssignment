package com.example.ConductAssignment.model;

public class Policy {
  private String policyId;
  private String type;   // "ignore" or "flag"
  private String regex;  // Regex pattern for matching

  public Policy(String policyId, String type, String regex) {
    this.policyId = policyId;
    this.type = type;
    this.regex = regex;
  }

  public String getPolicyId() {
    return policyId;
  }

  public String getType() {
    return type;
  }

  public String getRegex() {
    return regex;
  }
}

