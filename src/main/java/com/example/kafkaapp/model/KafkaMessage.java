package com.example.kafkaapp.model;

import lombok.Data;

@Data
public class KafkaMessage {
    private Long Id;
    private String message;
}
