package com.example.ConductAssignment.controller;

import com.example.ConductAssignment.model.Email;
import com.example.ConductAssignment.service.EmailProcessingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/process")
public class EmailProcessingController {
  private final EmailProcessingService emailProcessingService;

  public EmailProcessingController(EmailProcessingService emailProcessingService) {
    this.emailProcessingService = emailProcessingService;
  }

  @PostMapping("/email")
  public Map<String, List<String>> processEmail(@RequestBody Email email) {
    return emailProcessingService.processEmail(email);
  }
}
