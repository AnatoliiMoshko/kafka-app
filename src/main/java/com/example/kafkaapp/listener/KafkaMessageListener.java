package com.example.kafkaapp.listener;

import com.example.kafkaapp.model.KafkaMessage;
import com.example.kafkaapp.service.KafkaMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageListener {
    private final KafkaMessageService kafkaMessageService;

    @KafkaListener(topics = "${app.kafka.kafkaMessageTopic}",
            groupId = "${app.kafka.kafkaMessageGroupId",
            containerFactory = "kafkaMessageConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload KafkaMessage message,
                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                       @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {
        log.info("Received message: {}", message);
        log.info("Key: {}; Topic: {}; Partition: {}; Timestamp: {}", key, topic, partition, timestamp);

        kafkaMessageService.add(message);
    }
}
