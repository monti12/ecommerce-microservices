# Ecommerce Microservices Platform

A production-style Spring Boot Microservices project demonstrating modern cloud-native architecture patterns including Service Discovery, API Gateway, JWT Authentication, Distributed Tracing, Kafka Messaging, Saga Pattern, Circuit Breaker, Rate Limiting, and Email Notifications.

---

## Architecture

```text
                        +----------------+
                        |   API Gateway  |
                        |    Port 8085   |
                        +--------+-------+
                                 |
                                 |
          -------------------------------------------------
          |               |              |               |
          v               v              v               v

 +----------------+ +---------------+ +--------------+ +----------------------+
 | Order Service  | | Inventory      | | Payment      | | Notification Service |
 | Port 8084      | | Service 8081   | | Service 8082 | | Port 8083           |
 +-------+--------+ +-------+--------+ +------+-------+ +----------+-----------+
         |                  |                 |                     |
         |                  |                 |                     |
         -----------------------------------------------------------
                                 |
                                 v

                       +-------------------+
                       | Eureka Server     |
                       | Port 8761         |
                       +-------------------+

                                 |
                                 v

                       +-------------------+
                       | Kafka             |
                       | Port 9092         |
                       +-------------------+

                                 |
                                 v

                       +-------------------+
                       | Zipkin            |
                       | Port 9411         |
                       +-------------------+

                                 |
                                 v

                       +-------------------+
                       | PostgreSQL        |
                       +-------------------+
```

---

## Implemented Features

### Microservices

* Order Service
* Inventory Service
* Payment Service
* Notification Service
* Authentication Service
* API Gateway
* Eureka Server

---

### Service Discovery

Implemented using Netflix Eureka.

Features:

* Dynamic service registration
* Dynamic service lookup
* Client-side load balancing

---

### API Gateway

Implemented using Spring Cloud Gateway.

Features:

* Single entry point
* JWT validation
* Route forwarding
* Rate limiting
* Load balancing

---

### JWT Authentication

Implemented using Spring Security and JWT.

Features:

* User login
* Token generation
* Token validation
* Role-based authorization

Example:

```http
Authorization: Bearer <JWT_TOKEN>
```

---

### OpenFeign Communication

Used for synchronous service-to-service communication.

Examples:

```text
Order Service -> Inventory Service
Order Service -> Payment Service
```

---

### Resilience4j

Implemented:

* Circuit Breaker
* Retry
* Time Limiter
* Fallback Methods

Features:

* Fault tolerance
* Automatic retries
* Service degradation handling

---

### Distributed Tracing

Implemented using:

* Micrometer Tracing
* Brave
* Zipkin

Features:

* Trace ID propagation
* Span tracking
* Cross-service request tracing

Example:

```text
INFO [order-service,6a217763e60ed199ae0c4d0ed6032000,9245357e041c2fe9]
```

---

### Kafka Messaging

Implemented asynchronous communication.

Producer:

* Order Service

Consumer:

* Notification Service

Topic:

```text
order-created-topic
```

Flow:

```text
Order Created
      |
      v
Kafka Topic
      |
      v
Notification Service
      |
      v
Email Sent
```

---

### Saga Pattern (Orchestration)

Implemented in Order Service.

Flow:

```text
Create Order
      |
      v
Reserve Inventory
      |
      v
Process Payment
      |
      v
Confirm Order
      |
      v
Publish Notification Event
```

Compensation:

```text
Payment Failure
      |
      v
Release Inventory
      |
      v
Mark Order Failed
```

---

### Email Notifications

Implemented using:

* Spring Mail
* Gmail SMTP

Features:

* Order confirmation email
* Kafka-based asynchronous notifications

---

### API Gateway Rate Limiting

Implemented using:

* Redis
* Spring Cloud Gateway

Features:

```text
5 Requests Allowed
↓
429 Too Many Requests
```

Example response:

```http
HTTP/1.1 429 Too Many Requests
```

---

## Technology Stack

### Backend

* Java 17
* Spring Boot 3.x
* Spring Cloud 2023.x

### Security

* Spring Security
* JWT

### Database

* PostgreSQL

### Messaging

* Apache Kafka

### Service Discovery

* Eureka

### Gateway

* Spring Cloud Gateway

### Fault Tolerance

* Resilience4j

### Tracing

* Micrometer
* Brave
* Zipkin

### Email

* Spring Mail

### Rate Limiting

* Redis

### Build Tool

* Maven

### Version Control

* Git
* GitHub

---

## Running the Project

### Start Infrastructure

1. PostgreSQL
2. Kafka
3. Redis
4. Zipkin

### Start Services

1. Eureka Server
2. Authentication Service
3. Inventory Service
4. Payment Service
5. Notification Service
6. Order Service
7. API Gateway

---

## Sample Flow

### Login

```http
POST /auth/login
```

Returns:

```json
{
  "token": "JWT_TOKEN"
}
```

---

### Create Order

```http
POST /orders
```

Flow:

```text
Order Created
↓
Inventory Reserved
↓
Payment Processed
↓
Kafka Event Published
↓
Email Notification Sent
```

---

## Author

Akshat Singhal

GitHub:
https://github.com/monti12
