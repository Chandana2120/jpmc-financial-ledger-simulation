# J.P. Morgan Chase & Co. Software Engineering Simulation
### Specialized Focus: Blockchain Infrastructure & Data Integrity

## 📌 Project Overview
This repository contains my progress through the J.P. Morgan Chase Software Engineering Virtual Experience. My goal is to apply enterprise-grade Java development practices to the foundational requirements of financial ledgers and decentralized data systems.

---

## 🏗️ Phase 1: Infrastructure & Ledger Foundation (Task 1)

### **Objective**
The primary goal of this phase was to establish a high-performance backend environment for the **Midas Core** service, simulating the node setup required for a distributed financial network.

### **Technical Implementation**
* **Environment Scaffolding:** Configured a Spring Boot application using **Java 17**, ensuring compatibility with modern enterprise security standards.
* **Dependency Management:** Utilized **Maven** to integrate essential libraries for data persistence (JPA/H2) and real-time messaging.
* **Real-Time Data Streaming:** Integrated **Apache Kafka** by configuring a dedicated `trader-updates` topic. This simulates the "Gossip Protocol" or data broadcast layer used in blockchain networks to keep nodes synchronized.
* **Automated Verification:** Successfully passed the `TaskOneTests` suite, confirming that the service scaffold is ready to handle transaction logic.

### **Blockchain Alignment**
In this task, I focused on the "Network Layer." Just as a blockchain node requires a specific configuration to talk to other peers, this setup ensures the Midas Core service can reliably produce and consume financial events via Kafka, maintaining the integrity of the transaction stream.

---

## 🛠️ Technologies Used
* **Language:** Java 17
* **Framework:** Spring Boot 3.2.5
* **Build Tool:** Maven
* **Messaging:** Apache Kafka
* **Database:** H2 (In-memory for rapid ledger simulation)

---

## 🚀 How to Run (Task 1)
1.  **Clone:** `git clone https://github.com/Chandana2120/jpmc-financial-ledger-simulation.git`
2.  **Build:** Run `mvn clean install` to resolve dependencies.
3.  **Verify:** Run `TaskOneTests.java` to view the system output.