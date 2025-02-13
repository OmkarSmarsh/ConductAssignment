package com.example.ConductAssignment.service;

import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class VectorScanService {
  public boolean match(String emailContent, String policyRegex) {
    // Compile the regex pattern
    Pattern pattern = Pattern.compile(policyRegex, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(emailContent);

    // Check if the regex pattern matches the email content
    return matcher.find();
  }
}

