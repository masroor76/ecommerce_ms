## 🛍️ E-Commerce Microservices System (Work in Progress)

A **modern, hybrid microservices-based e-commerce backend** being developed using **Java 25 (Spring Boot + Spring WebFlux)** and **Node.js (Express)**.
This project integrates **Reactive APIs, Kafka messaging, Redis caching, and polyglot persistence** to demonstrate a high-performance, scalable architecture aligned with modern cloud standards.

---

### 🧱 Project Overview

This in-progress project aims to build a **secure, reactive-first e-commerce system** combining **Java** and **Node.js** microservices.
The **Spring Cloud Gateway** serves as the **reactive API entry point**, delegating JWT validation to the **Auth Service**, before routing requests to backend services.
Core business services use **PostgreSQL** and **MongoDB** databases, while **Kafka** handles asynchronous event communication.

---

### 🔹 Microservices (In Progress)

| Service             | Technology                                | Description                                                                                         | DB         | Status          |
| ------------------- | ----------------------------------------- | --------------------------------------------------------------------------------------------------- | ---------- | --------------- |
| **API Gateway**     | Java 25 + Spring Cloud Gateway (Reactive) | Reactive routing, load balancing, and security filtering. Delegates JWT validation to Auth Service. | –          | ✅ Functional    |
| **Auth Service**    | Java 25 + Spring Boot                     | Handles JWT generation/validation, integrates with Redis for token caching.                         | Redis      | 🛠️ In Progress |
| **Eureka Server**   | Java 25 + Spring Boot                     | Service registry for discovery and scaling.                                                         | –          | ✅ Functional    |
| **User Service**    | Java 25 + Spring Boot (Synchronous)       | Manages user profiles, registration, and authentication details.                                    | PostgreSQL | 🛠️ In Progress |
| **Product Service** | Java 25 + Spring Boot (Synchronous)       | Manages products, inventory, and catalog updates. Publishes product stock events to Kafka.          | PostgreSQL | ✅ Functional    |
| **Order Service**   | Node.js + Express                         | Handles orders, payments, and fulfillment. Consumes Kafka product/order events.                     | MongoDB    | 🛠️ In Progress |

---

### ⚙️ Technology Stack

| Category              | Technologies                                                   |
| --------------------- | -------------------------------------------------------------- |
| **Languages**         | Java 25, Node.js (ES2022)                                      |
| **Frameworks**        | Spring Boot, Spring Cloud Gateway (Reactive), Express.js       |
| **Architecture**      | Hybrid Microservices (Reactive Gateway + Synchronous Services) |
| **Security**          | Spring Security + JWT                                          |
| **Databases**         | PostgreSQL (User, Product) + MongoDB (Order)                   |
| **Caching**           | Redis                                                          |
| **Async Messaging**   | Apache Kafka                                                   |
| **Service Discovery** | Netflix Eureka Server                                          |
| **Containerization**  | Docker & Docker Compose                                        |
| **Build Tools**       | Maven (Java) + npm (Node.js)                                   |
| **Version Control**   | Git & GitHub                                                   |

---

### 🧭 System Architecture

```
                          +----------------------+
                          |       CLIENT         |
                          | (Web / Mobile App)   |
                          +----------+-----------+
                                     |
                                     v
                         +-----------------------+
                         |     API GATEWAY       |
                         | (Reactive, Java 25)   |
                         |  Spring Cloud Gateway |
                         +----------+------------+
                                     |
                                     v
                         +----------------------+
                         |     AUTH SERVICE     |
                         |  Spring Boot + JWT   |
                         | Redis for Token Cache|
                         +----------+-----------+
                                     |
                                     v
          +------------------------------------------------+
          |                EUREKA SERVER                   |
          |        (Service Registration & Discovery)      |
          +------------------------------------------------+
                   |                 |                 |
                   v                 v                 v
         +---------------+  +---------------+  +---------------+
         | USER SERVICE   |  | PRODUCT SVC   |  | ORDER SVC     |
         | Spring Boot    |  | Spring Boot   |  | Node.js (Exp) |
         | PostgreSQL     |  | PostgreSQL    |  | MongoDB       |
         +-------+--------+  +-------+-------+  +-------+-------+
                 |                  |                  |
                 v                  v                  v
         +-----------+      +-----------+      +-----------+
         |PostgreSQL |      |PostgreSQL |      |  MongoDB  |
         +-----------+      +-----------+      +-----------+

                           +----------------------+
                           |      KAFKA BUS       |
                           | (Event Messaging)    |
                           +----------+-----------+
                                      |
                                      v
                              +---------------+
                              |     REDIS     |
                              | (Token Cache) |
                              +---------------+
```

**Flow Summary:**

1. Requests from clients enter via the **Reactive API Gateway** (Spring Cloud Gateway).
2. The **Auth Service** validates JWTs and uses Redis for fast token/session caching.
3. Authorized requests are routed to backend services (synchronous Java services and Node.js Order service).
4. Services use **Kafka** to publish/consume business events asynchronously.
5. Each microservice persists data independently in its respective **PostgreSQL** or **MongoDB** database.
6. **Eureka Server** handles dynamic service registration and discovery.

---

### 🚀 Current Development Progress

| Feature                    | Status          | Notes                              |
| -------------------------- | --------------- | ---------------------------------- |
| Reactive Gateway (WebFlux) | ✅ Completed     | Fully reactive routing layer       |
| JWT Authentication         | 🛠️ In Progress | Integrated with Auth Service       |
| Redis Cache                | 🛠️ In Progress | Token/session caching planned      |
| Kafka Messaging            | ✅ Functional    | Product–Order communication setup  |
| Polyglot Databases         | ✅ Implemented   | PostgreSQL (Java) + MongoDB (Node) |
| Dockerization              | 🛠️ In Progress | Compose setup in development       |
| CI/CD Integration          | ⏳ Planned       | To be added with GitHub Actions    |

---

### 🧠 Design Highlights

* 🌀 **Reactive Gateway, Synchronous Core:** Efficient request handling with high throughput.
* 🔐 **JWT + Redis Security Layer:** Fast and scalable authentication.
* 💬 **Kafka Messaging:** Enables loose coupling and event-driven updates.
* 🗄️ **Polyglot Persistence:** PostgreSQL for relational entities, MongoDB for flexible order data.
* ☁️ **Eureka Discovery:** Dynamic service registration for scaling and resilience.
* 🧰 **Docker-Ready Architecture:** Multi-container orchestration with Kafka, Redis, MongoDB, and PostgreSQL.

---

### 🧩 How to Run (Development)

1. **Start Infrastructure (Kafka, Redis, MongoDB, PostgreSQL)**

   ```bash
   docker-compose up -d kafka zookeeper redis postgres mongo
   ```

2. **Start Eureka Server**

   ```bash
   cd eureka_server
   mvn spring-boot:run
   ```

3. **Start Auth and Gateway Services**

   ```bash
   cd auth && mvn spring-boot:run
   cd ../api_gateway && mvn spring-boot:run
   ```

4. **Start Backend Services**

   ```bash
   cd user && mvn spring-boot:run
   cd ../products && mvn spring-boot:run
   cd ../orders && npm start
   ```

5. **Access**

   * **Eureka Dashboard:** [http://localhost:8761](http://localhost:8761)
   * **API Gateway Entry:** [http://localhost:8080](http://localhost:8080)

---

### 🔮 Next Steps

* Complete **JWT–Redis integration** for stateless authentication.
* Finalize **Docker Compose orchestration** for all services.
* Add **Spring Cloud Config Server** for external configuration.
* Integrate **Zipkin/Sleuth** for tracing.
* Build a **React-based front-end** to interact with the APIs.

---

### 🤝 Contributing

This project is actively being developed.
Contributions, feedback, and collaboration are welcome — feel free to fork, branch, and submit pull requests!

---

### 📜 License

This project is licensed under the **MIT License** — open for learning, research, and development purposes.

---
