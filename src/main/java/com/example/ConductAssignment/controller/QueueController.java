package com.example.ConductAssignment.controller;

import com.example.ConductAssignment.model.QueueData;
import com.example.ConductAssignment.service.MongoDbService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/queues")
public class QueueController {
  private final MongoDbService mongoDbService;

  public QueueController(MongoDbService mongoDbService) {
    this.mongoDbService = mongoDbService;
  }

  @GetMapping("/for-email")
  public List<QueueData> getQueuesForEmail(@RequestParam List<String> recipients) {
    return mongoDbService.getQueuesForEmail(recipients);
  }
}

