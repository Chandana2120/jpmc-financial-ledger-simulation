package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Transaction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    // We added 'groupId' so Kafka knows who is listening
    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-core-group")
    public void listen(Transaction transaction) {
        // This will print the amount in the console clearly
        System.out.println(">>> AMOUNT RECEIVED: " + transaction.getAmount());
    }
}