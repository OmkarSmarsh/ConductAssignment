package com.example.ConductAssignment.controller;

import com.example.ConductAssignment.service.EmailKafkaProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class EmailController {
  private final EmailKafkaProducer emailKafkaProducer;

  public EmailController(EmailKafkaProducer emailKafkaProducer) {
    this.emailKafkaProducer = emailKafkaProducer;
  }

  @GetMapping("/sendEmails")
  public String sendEmails(@RequestParam(defaultValue = "src/main/resources/emails/") String path) {
    try {
      emailKafkaProducer.sendEmailsFromDirectory(path);
      return "Emails sent to Kafka!";
    } catch (IOException e) {
      return "Error: " + e.getMessage();
    }
  }
}

