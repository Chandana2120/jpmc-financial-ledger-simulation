package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.service.LedgerService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private final LedgerService ledgerService;

    // Spring automatically provides the LedgerService here
    public KafkaConsumer(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-core-group")
    public void listen(Transaction transaction) {
        // We still print for debugging, but now we trigger the banking logic
        System.out.println(">>> PROCESSING TRANSACTION: " + transaction.getAmount());

        // This call checks balances and saves to H2 database
        ledgerService.process(transaction);
    }
}