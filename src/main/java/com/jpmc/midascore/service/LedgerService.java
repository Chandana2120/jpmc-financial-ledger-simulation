package com.jpmc.midascore.service;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class LedgerService {
    private final UserRepository userRepository;
    private final TransactionRecordRepository transactionRepository;
    private final RestTemplate restTemplate; // 1. The "Telephone" added here

    public LedgerService(UserRepository userRepository,
                         TransactionRecordRepository transactionRepository,
                         RestTemplate restTemplate) { // 2. Injecting the Bean we made in Application.java
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public void process(Transaction transaction) {
        UserRecord sender = userRepository.findById(transaction.getSenderId());
        UserRecord recipient = userRepository.findById(transaction.getRecipientId());

        if (sender != null && recipient != null) {
            if (sender.getBalance() >= transaction.getAmount()) {

                // 3. THE REST API CALL
                // We send the transaction to the external service to see if there is a reward
                String url = "http://localhost:8080/incentive";
                Incentive incentiveResponse = restTemplate.postForObject(url, transaction, Incentive.class);

                float incentiveAmount = (incentiveResponse != null) ? incentiveResponse.getAmount() : 0f;

                // 4. THE UPDATED BALANCES
                sender.setBalance(sender.getBalance() - transaction.getAmount());
                // Notice: Recipient gets the transaction amount + the extra incentive!
                recipient.setBalance(recipient.getBalance() + transaction.getAmount() + incentiveAmount);

                userRepository.save(sender);
                userRepository.save(recipient);

                // 5. THE NEW RECORD (This fixes the red error!)
                transactionRepository.save(new TransactionRecord(sender, recipient, transaction.getAmount(), incentiveAmount));

                System.out.println(">>> TRANSACTION SUCCESS: " + transaction.getAmount() + " (Incentive: " + incentiveAmount + ")");
            } else {
                System.out.println(">>> TRANSACTION FAILED: Insufficient Balance");
            }
        } else {
            System.out.println(">>> TRANSACTION FAILED: Invalid User ID");
        }
    }
}