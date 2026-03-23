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
public class TaskThreeTests {
    static final Logger logger = LoggerFactory.getLogger(TaskThreeTests.class);

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPopulator userPopulator;

    @Autowired
    private FileLoader fileLoader;

    @Test
    void task_three_verifier() throws InterruptedException {
        userPopulator.populate();
        String[] transactionLines = fileLoader.loadStrings("/test_data/mnbvcxz.vbnm");
        for (String transactionLine : transactionLines) {
            kafkaProducer.send(transactionLine);
        }

        logger.info("Processing transactions, please wait...");
        Thread.sleep(15000); // Increased to 15s to be safe

        // --- THE ANSWER SECTION ---
        UserRecord waldorf = userRepository.findByName("waldorf");
        if (waldorf != null) {
            float balance = waldorf.getBalance();
            int roundedBalance = (int) Math.floor(balance);

            logger.info("----------------------------------------------------------");
            logger.info("FINAL WALDORF BALANCE: " + balance);
            logger.info(">>> QUIZ ANSWER (ROUNDED DOWN): " + roundedBalance);
            logger.info("----------------------------------------------------------");
        } else {
            logger.error("User 'waldorf' not found in database!");
        }

        logger.info("kill this test once you find the answer");
        while (true) {
            Thread.sleep(20000);
            logger.info("...");
        }
    }
} // This is the bracket that was likely missing!