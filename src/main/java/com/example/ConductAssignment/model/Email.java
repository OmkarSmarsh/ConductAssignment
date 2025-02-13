package com.example.ConductAssignment.model;

import java.util.List;

public class Email {
  private String id;
  private String subject;
  private String sender;
  private List<String> recipients;
  private String content;

  public Email() {}

  public Email(String id, String subject, String sender, List<String> recipients, String content) {
    this.id = id;
    this.subject = subject;
    this.sender = sender;
    this.recipients = recipients;
    this.content = content;
  }

  public String getId() { return id; }
  public String getSubject() { return subject; }
  public String getSender() { return sender; }
  public List<String> getRecipients() { return recipients; }
  public String getContent() { return content; }
}

