# <p align="center">W-net Internet </p>

## Context

This is an internet provider management system.

# ER Diagram

<img src="/images/diagramaER.svg" alt="Description of image" style="height: 500px; width: 600px;" />

## Run project locally

> ⚠️ You need to have [Java](https://www.oracle.com/java/) installed on your machine.

> ⚠️ You need to have [Docker](https://www.docker.com/get-started/) installed on your machine.

1. Clone the repository:

```BASH
git clone git@github.com:mairess/w-net-internet.git
```

2. Install dependencies:

```BASH
mvn install -DskipTests
```

3. Start the database:

```BASH
docker compose up database -d --build 
```

4. Run the API:

```BASH
mvn spring-boot:run
```

5. View available routes at:

```BASH
http://localhost:8080/swagger-ui/index.html
```

## Run project with Docker

> ⚠️ You need to have [Docker](https://www.docker.com/get-started/) installed on your machine.

1. Clone the repository:

```BASH
git clone git@github.com:mairess/w-net-internet.git
```

2. Start the API and the database:

```BASH
docker compose up -d --build 
```

3. View available routes at:

```BASH
http://localhost:8080/swagger-ui/index.html
```

# To do

- [ ] Tests
- [x] authentication/authorization