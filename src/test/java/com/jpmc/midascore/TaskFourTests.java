package com.jpmc.midascore;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class TaskFourTests {
    static final Logger logger = LoggerFactory.getLogger(TaskFourTests.class);

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private UserRepository userRepository; // Added this

    @Autowired
    private UserPopulator userPopulator;

    @Autowired
    private FileLoader fileLoader;

    @Test
    void task_four_verifier() throws InterruptedException {
        userPopulator.populate();
        String[] transactionLines = fileLoader.loadStrings("/test_data/alskdjfh.fhdjsk");
        for (String transactionLine : transactionLines) {
            kafkaProducer.send(transactionLine);
        }

        // Give it 15 seconds to call the external API for all transactions
        logger.info("Processing transactions with external incentives...");
        Thread.sleep(15000);

        // --- THE ANSWER SECTION ---
        UserRecord wilbur = userRepository.findByName("wilbur");
        if (wilbur != null) {
            float balance = wilbur.getBalance();
            int roundedBalance = (int) Math.floor(balance);

            logger.info("----------------------------------------------------------");
            logger.info("FINAL WILBUR BALANCE: " + balance);
            logger.info(">>> QUIZ ANSWER (ROUNDED DOWN): " + roundedBalance);
            logger.info("----------------------------------------------------------");
        } else {
            logger.error("User 'wilbur' not found in database!");
        }
        // ---------------------------

        logger.info("kill this test once you find the answer");
        while (true) {
            Thread.sleep(20000);
            logger.info("...");
        }
    }
}