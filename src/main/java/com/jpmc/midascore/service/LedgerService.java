package com.jpmc.midascore.service;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LedgerService {
    private final UserRepository userRepository;
    private final TransactionRecordRepository transactionRepository;

    public LedgerService(UserRepository userRepository, TransactionRecordRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public void process(Transaction transaction) {
        UserRecord sender = userRepository.findById(transaction.getSenderId());
        UserRecord recipient = userRepository.findById(transaction.getRecipientId());

        // Rule 1 & 2: Sender and Recipient must exist
        if (sender != null && recipient != null) {
            // Rule 3: Sender must have enough balance
            if (sender.getBalance() >= transaction.getAmount()) {

                // Perform the transfer
                sender.setBalance(sender.getBalance() - transaction.getAmount());
                recipient.setBalance(recipient.getBalance() + transaction.getAmount());

                // Save updated users and the transaction record
                userRepository.save(sender);
                userRepository.save(recipient);
                transactionRepository.save(new TransactionRecord(sender, recipient, transaction.getAmount()));

                System.out.println(">>> TRANSACTION SUCCESS: " + transaction.getAmount());
            } else {
                System.out.println(">>> TRANSACTION FAILED: Insufficient Balance");
            }
        } else {
            System.out.println(">>> TRANSACTION FAILED: Invalid User ID");
        }
    }
}