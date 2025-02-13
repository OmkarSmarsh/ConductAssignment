package com.example.ConductAssignment.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class EmailKafkaProducer {
  private static final String EMAIL_TOPIC = "emails";
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final ObjectMapper objectMapper;

  public EmailKafkaProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
    this.kafkaTemplate = kafkaTemplate;
    this.objectMapper = objectMapper;
  }

  public void sendEmailsFromDirectory(String directoryPath) throws IOException {
    File folder = new File(directoryPath);
    File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));

    if (files != null) {
      for (File file : files) {
        String emailJson = objectMapper.readTree(file).toString();
        kafkaTemplate.send(EMAIL_TOPIC, emailJson);
        System.out.println("Sent email: " + emailJson);
      }
    }
  }
}

