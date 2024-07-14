# W-net Internet

<a href="https://codecov.io/gh/mairess/w-net-internet" > 
 <img src="https://codecov.io/gh/mairess/w-net-internet/graph/badge.svg?token=ukuYKkowJP"/>
 </a>

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

1. Clone repository:

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
bin/zookeeper-server-start.sh config/zookeeper.properties
```

4. Start Kafka:

```BASH
bin/kafka-server-start.sh config/server.properties
```

5. Start database:

```BASH
docker compose up database -d --build 
```

6. Run API:

```BASH
mvn spring-boot:run
```

7. Run tests:

```BASH
mvn test
```

8. Access API documentation and available routes on your web browser at:

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

1. Clone repository:

```BASH
git clone git@github.com:mairess/w-net-internet.git

cd w-net-internet
```

2. Run API:

```BASH
docker compose up -d --build 
```

3. Run tests:

```BASH
mvn test
```

4. Access API documentation and available routes on your web browser at:

```BASH
http://localhost:8080/swagger-ui/index.html
```