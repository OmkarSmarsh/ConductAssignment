package com.example.ConductAssignment.config;

import com.example.ConductAssignment.model.Email;
import com.example.ConductAssignment.service.EmailProcessingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;

import java.util.List;
import java.util.Map;

@Configuration
@EnableKafkaStreams
public class KafkaStreamsConfig {
  private final EmailProcessingService emailProcessingService;
  private final ObjectMapper objectMapper;

  public KafkaStreamsConfig(EmailProcessingService emailProcessingService, ObjectMapper objectMapper) {
    this.emailProcessingService = emailProcessingService;
    this.objectMapper = objectMapper;
  }

  @Bean
  public KStream<String, String> kafkaStream(StreamsBuilder streamsBuilder) {
    System.out.println("[KafkaStreams] Initializing Kafka Streams...");

    KStream<String, String> emailStream = streamsBuilder.stream("emails", Consumed.with(Serdes.String(), Serdes.String()));

    emailStream.peek((key, value) -> System.out.println("[KafkaStreams] Received email: " + value)) // Log received message

        .flatMapValues(emailJson -> {
          try {
            // Convert JSON string to Email object
            Email email = objectMapper.readValue(emailJson, Email.class);
            System.out.println("[KafkaStreams] Processing email ID: " + email.getId());

            // Process email through EmailProcessingService
            Map<String, List<String>> results = emailProcessingService.processEmail(email);

            // Log matched policies
            results.forEach((queueId, policies) ->
                System.out.println("[KafkaStreams] Email ID: " + email.getId() + " matched in Queue: " + queueId + " with Policies: " + policies)
            );

            // Transform results into Kafka messages
            return results.entrySet().stream()
                .map(entry -> {
                  String result = String.format(
                      "{\"emailId\": \"%s\", \"queueId\": \"%s\", \"matchedPolicies\": %s}",
                      email.getId(), entry.getKey(), entry.getValue()
                  );
                  System.out.println("[KafkaStreams] Sending result to queue-results: " + result);
                  return result;
                })
                .toList();
          } catch (Exception e) {
            System.err.println("[KafkaStreams] Error processing email: " + e.getMessage());
            return List.of(); // Return empty list if error occurs
          }
        })

        .peek((key, value) -> System.out.println("[KafkaStreams] Final message sent to queue-results: " + value)) // Log final output
        .to("queue-results", Produced.with(Serdes.String(), Serdes.String()));

    System.out.println("[KafkaStreams] Kafka Streams setup completed.");

    return emailStream;
  }
}
