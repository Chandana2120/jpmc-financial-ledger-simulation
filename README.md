# J.P. Morgan Chase & Co. Software Engineering Simulation
### Specialized Focus: Blockchain Infrastructure & Data Integrity

## 📌 Project Overview
This repository contains my progress through the J.P. Morgan Chase Software Engineering Virtual Experience. My goal is to apply enterprise-grade Java development practices to the foundational requirements of financial ledgers and decentralized data systems.

---

##  Phase 1: Infrastructure & Ledger Foundation

### **Objective**
The primary goal of this phase was to establish a high-performance backend environment for the **Midas Core** service, simulating the node setup required for a distributed financial network.

### **Technical Implementation**
* **Environment Scaffolding:** Configured a Spring Boot application using **Java 17**, ensuring compatibility with modern enterprise security standards.
* **Dependency Management:** Utilized **Maven** to integrate essential libraries for data persistence (JPA/H2) and real-time messaging.
* **Real-Time Data Streaming:** Integrated **Apache Kafka** by configuring a dedicated `trader-updates` topic. This simulates the "Gossip Protocol" or data broadcast layer used in blockchain networks to keep nodes synchronized.

### **Blockchain Alignment**
In this task, I focused on the "Network Layer." Just as a blockchain node requires a specific configuration to talk to other peers, this setup ensures the Midas Core service can reliably produce and consume financial events via Kafka.

---

##  Phase 2: Event-Driven Architecture & Data Ingestion 

### **Objective**
The goal was to implement the "Listener" logic for the Midas Core service, enabling it to consume, deserialize, and process incoming financial transactions from the Kafka stream.

### **Technical Implementation**
* **Kafka Consumer Component:** Created a specialized `KafkaConsumer` class within the `com.jpmc.midascore.component` package to act as an asynchronous message listener.
* **JSON Serialization & Deserialization:** Configured `JsonSerializer` and `JsonDeserializer` in the `application.yml` to transform raw network bytes into structured Java `Transaction` objects.
* **Payload Processing:** Implemented logic to extract and verify specific transaction fields (Sender ID, Receiver ID, and Amount) from the data stream.
* **Verification:** Successfully captured and validated high-frequency transaction amounts (e.g., 122.86, 42.87, 161.79, 22.22) through automated testing.

### **Blockchain Alignment**
In this phase, I focused on "Transaction Validation." Just as a blockchain node must listen to the network and correctly decode broadcasted transactions before adding them to a block, this implementation ensures Midas Core can accurately interpret the ledger's incoming data packets.

---

## 🛠️ Technologies Used
* **Language:** Java 17
* **Framework:** Spring Boot 3.2.5
* **Build Tool:** Maven
* **Messaging:** Apache Kafka
* **Serialization:** Jackson JSON
* **Database:** H2 (In-memory for rapid ledger simulation)

---

## 🚀 How to Run & Verify
1.  **Clone:** `git clone https://github.com/Chandana2120/jpmc-financial-ledger-simulation.git`
2.  **Build:** Run `mvn clean install` to resolve dependencies.
3.  **Task 1 Verification:** Run `TaskOneTests.java` to verify infrastructure.
4.  **Task 2 Verification:** Run `TaskTwoTests.java` to observe the real-time transaction ingestion in the console output.

---

##  Phase 3: Relational Persistence & Transaction Validation 

### **Objective**
The goal of this phase was to transform Midas Core into a resilient system by integrating an H2 database to validate and permanently record transactions.

### **Technical Implementation**
* **Data Modeling:** Developed a `TransactionRecord` JPA entity with **Many-to-One** relationships to the `UserRecord` entity, creating a linked ledger.
* **Service Layer Logic:** Built a `LedgerService` to enforce critical business rules:
    1. Verify Sender and Recipient existence.
    2. Enforce balance checks (Sender Balance ≥ Transaction Amount).
    3. Execute atomic balance updates for both parties.
* **Data Persistence:** Integrated **Spring Data JPA** repositories to manage CRUD operations on the in-memory H2 database.
* **Validation:** Processed a high-volume transaction stream via Kafka and successfully verified the final state of the ledger (e.g., Waldorf's closing balance).

### **Blockchain Alignment**
This phase implements the **"State Transition Function."** Just as a blockchain node updates its world state (account balances) only after validating a transaction against the current ledger rules, Midas Core now ensures data integrity before any record is committed to the database.

---

##  Phase 4: External Service Integration 

### **Objective**
The final phase focused on integrating Midas Core with an external **Incentive API** to incorporate a rewards system. This task simulated a microservices environment where different teams manage separate pieces of business logic.

### **Technical Implementation**
* **REST API Consumption:** Configured a `RestTemplate` bean to enable synchronous HTTP communication between Midas Core and the external Incentive Service.
* **JSON Mapping:** Created an `Incentive` POJO to automatically deserialize JSON responses from the external API into Java objects.
* **Service Orchestration:** Updated the `LedgerService` to perform a three-step processing flow:
  1. Validate the transaction internally.
  2. Query the external REST endpoint (`/incentive`) for potential rewards.
  3. Apply both the transaction amount and the external incentive to the recipient's balance.
* **Schema Evolution:** Modified the `TransactionRecord` entity to store the `incentive` amount, ensuring a full audit trail of all funds entering the system.

### **Key Learnings**
* **Decoupling:** Learned how REST APIs act as a contract between services, allowing the Incentive team to update their logic without requiring changes to the Midas Core codebase.
* **Resiliency:** Handled connection logic and verified system state across multiple running services.
* **Final Validation:** Successfully processed a complex data stream and verified the final ledger state (Wilbur's balance: **3089**).

---

##  Phase 5: User-Facing API Exposure 

### **Objective**
The final phase transformed Midas Core into a functional server by exposing a REST API. This allows external users or frontend applications to query live account balances directly from the ledger.

### **Technical Implementation**
* **REST Controller Development:** Implemented a `@RestController` to handle incoming GET requests at the `/balance` endpoint.
* **Dynamic Data Retrieval:** Integrated the `UserRepository` within the controller to perform real-time database lookups based on a `userId` request parameter.
* **JSON Serialization:** Leveraged Spring Boot's default Jackson integration to automatically serialize Java `Balance` objects into JSON format for web consumption.
* **System Configuration:** Configured the application to operate on port **33400**, ensuring it runs as a dedicated service alongside the Kafka listeners and database layers.
* **Error Handling:** Implemented logic to return a default balance of `0.0` for non-existent user IDs, ensuring API stability and predictable responses.

### **Key Architectual Learnings**
* **Server-Side Development:** Transitioned the application from a data processor to a data provider.
* **Full-Stack Backend Logic:** Completed the full data lifecycle: **Ingest** (Kafka) $\rightarrow$ **Validate** (Service Layer) $\rightarrow$ **Persist** (JPA/H2) $\rightarrow$ **Expose** (REST).
* **Final Verification:** Successfully passed the `TaskFiveTests` suite, confirming the accuracy of the ledger's state after thousands of simulated transactions.

### **Project Status: COMPLETE **
The Midas Core system is now a robust, event-driven financial engine ready for enterprise-level transaction processing.