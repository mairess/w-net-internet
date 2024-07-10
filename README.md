# <p align="center">W-net Internet </p>

## Context

This is an internet provider management system.

# ER Diagram

<img src="/images/diagramaER.svg" alt="Description of image" style="height: 500px; width: 600px;" />

## Run locally

To run this project locally, follow these steps:

### Prerequisites

Make sure you have the following installed on your machine:

[Java](https://www.oracle.com/java/)

[Docker](https://www.docker.com/get-started/)

[Apache kafka](https://kafka.apache.org/documentation/#quickstart)

Also, set up the following env variables for the mail service:

```
MAIL_HOST=smtp.domainExample.com 

MAIL_PORT=your-port-number-like 000

MAIL_USERNAME=myMail@domainExample.com

MAIL_PASSWORD=your-app-password
```

### Steps:

1. Clone the repository:

```BASH
git clone git@github.com:mairess/w-net-internet.git

cd w-net-internet
```

2. Install dependencies:

```BASH
mvn install -DskipTests
```

3. Start ZooKeeper:

```BASH
# Start ZooKeeper:

bin/zookeeper-server-start.sh config/zookeeper.properties
```

4. Start Kafka:

```BASH
# Start Kafka:

bin/kafka-server-start.sh config/server.properties
```

5. Start the database:

```BASH
docker compose up database -d --build 
```

6. Run the API:

```BASH
mvn spring-boot:run
```

7. Access the API documentation and available routes on your web browser at:

```BASH
http://localhost:8080/swagger-ui/index.html
```

## Run with Docker

### Prerequisites

Make sure you have the following installed on your machine:

[Docker](https://www.docker.com/get-started/)

Also, set up the following env variables for the mail service:

```
MAIL_HOST=smtp.domainExample.com 

MAIL_PORT=your-port-number-like 000

MAIL_USERNAME=myMail@domainExample.com

MAIL_PASSWORD=your-app-password
```

### Steps:

1. Clone the repository:

```BASH
git clone git@github.com:mairess/w-net-internet.git

cd w-net-internet
```

2. Start application:

```BASH
docker compose up -d --build 
```

3. Access the API documentation and available routes on your web browser at:

```BASH
http://localhost:8080/swagger-ui/index.html
```

# To do

- [ ] Tests
- [x] authentication/authorization