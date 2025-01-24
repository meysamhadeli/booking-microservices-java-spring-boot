<div align="center" style="margin-bottom:20px">
  <img src="assets/logo.png" alt="booking-microservices-java-spring-boot" />
    <div align="center">
                 <a href="https://github.com/meysamhadeli/booking-microservices-java-spring-boot/blob/main/LICENSE"><img alt="build-status"          src="https://img.shields.io/github/license/meysamhadeli/booking-microservices-java-spring-boot?color=%234275f5&style=flat-square"/></a>
    </div>
</div>

> 🚀 **A practical and imaginary microservices for implementing an infrastructure for up and running distributed system with the latest technology and architecture like Vertical Slice Architecture, Event Driven Architecture, CQRS, DDD, gRpc, MongoDB, RabbitMq in Java Spring Boot.**

> 💡 **This project is not business-oriented and most of my focus was in the thechnical part for implement a distributed system with a sample project. In this project I implemented some concept in microservices like Messaging, Tracing, Event Driven Architecture, Vertical Slice Architecture, CQRS, DDD and gRpc.**

**You can find `.Net` ported of this project in this link:**
🔗 [booking-microservices](https://github.com/meysamhadeli/booking-microservices)

<div>
  <a href="https://gitpod.io/#https://github.com/meysamhadeli/booking-microservices-java-spring-boot"><img alt="Open in Gitpod" src="https://gitpod.io/button/open-in-gitpod.svg"/></a>
</div>
<div>
  <a href='https://codespaces.new/meysamhadeli/booking-microservices-java-spring-boot?quickstart=1'><img alt='Open in GitHub Codespaces' src='https://github.com/codespaces/badge.svg'></a>
</div>

# Table of Contents

- [The Goals of This Project](#the-goals-of-this-project)
- [Plan](#plan)
- [Technologies - Libraries](#technologies---libraries)
- [The Domain and Bounded Context - Service Boundary](#the-domain-and-bounded-context---service-boundary)
- [Structure of Project](#structure-of-project)
- [How to Run](#how-to-run)
  - [Docker Compose](#docker-compose)
  - [Build](#build)
  - [Run](#run)
  - [Test](#test)
- [Documentation Apis](#documentation-apis)
- [Support](#support)
- [Contribution](#contribution)

## The Goals of This Project

- :sparkle: Using `Vertical Slice Architecture` for `architecture` level.
- :sparkle: Using `Spring MVC` as a `Web Framework`.
- :sparkle: Using `Domain Driven Design (DDD)` to implement all `business processes` in microservices.
- :sparkle: Using `Spring AMQP` on top of `Rabbitmq` for `Event Driven Architecture` between our microservices.
- :sparkle: Using `gRPC` for `internal communication` between our microservices.
- :sparkle: Using `CQRS` implementation with `Mediator` library.
- :sparkle: Using `Spring Data JPA` for `data persistence` and `ORM` in `write side` with `Postgres`.
- :sparkle: Using `Spring Data MongoDB` for `data persistence` and `ORM` in `read side` with `MongoDB`.
- :sparkle: Using `Inbox Pattern` for ensuring message idempotency for receiver and `Exactly once Delivery`.
- :sparkle: Using `Outbox Pattern` for ensuring no message is lost and there is at `At Least One Delivery`.
- :sparkle: Using `Unit Testing` for testing small units and mocking our dependencies with `Mockito`.
- :sparkle: Using `End-To-End Testing` and `Integration Testing` for testing `features` with all dependencies using `testcontainers`.
- :sparkle: Using `Spring Validator` and a `Validation Pipeline Behaviour` on top of `Mediator`.
- :sparkle: Using `Springdoc Openapi` for generating `OpenAPI documentation` in Spring Boot.
- :sparkle: Using `OpenTelemetry Collector` for collecting `Metrics`, `Tracings` and `Structured Logs`.
- :sparkle: Using `Kibana` for `Logging` top of `OpenTelemetry Collector`.
- :sparkle: Using `Jaeger` for `Distributed Tracing` top of `OpenTelemetry Collector`.
- :sparkle: Using `OpenTelemetry` for monitoring on top of `Prometheus` and `Grafana`.
- :sparkle: Using `Keycloak` for authentication and authorization base on `OpenID-Connect` and `OAuth2`.
- :sparkle: Using `Spring Cloud Gateway` as a microservices `gateway`.


## Plan

> 🌀This project is a work in progress, new features will be added over time.🌀

I will try to register future goals and additions in the [Issues](https://github.com/meysamhadeli/booking-microservices-java-spring-boot/issues) section of this repository.

High-level plan is represented in the table

| Feature           | Status         |
| ----------------- | -------------- |
| API Gateway       | Completed ✔️   |
| Keycloak Service  | Completed ✔️   |
| Flight Service    | Completed ✔️   |
| Passenger Service | Completed ✔️   |
| Booking Service   | Completed ✔️   |
| Building Blocks   | Completed ✔️   |


## Technologies - Libraries
- ✔️ **[Spring Boot](https://github.com/spring-projects/spring-boot/tree/main)** - A framework for building production-grade Spring-based applications with minimal effort.
- ✔️ **[Spring AMQP](https://github.com/spring-projects/spring-amqp)** - Simplifies the development of messaging applications using RabbitMQ.
- ✔️ **[Spring Data JPA](https://github.com/spring-projects/spring-data-jpa)** - A library to simplify data access with JPA-based repositories.
- ✔️ **[Spring Data MongoDB](https://github.com/spring-projects/spring-data-mongodb)** - Provides integration with MongoDB for Spring applications.
- ✔️ **[Spring Security](https://github.com/spring-projects/spring-security)** - A framework for securing Spring-based applications, providing authentication and authorization capabilities.
- ✔️ **[Keycloak](https://github.com/keycloak/keycloak)** - An identity provider and access management solution.
- ✔️ **[PostgreSQL](https://github.com/pgjdbc/pgjdbc)** A JDBC driver for connecting Java applications to PostgreSQL databases.
- ✔️ **[Springdoc OpenAPI](https://github.com/springdoc/springdoc-openapi)** - Automates the generation of OpenAPI documentation for Spring Boot applications.
- ✔️ **[Swagger Core](https://github.com/swagger-api/swagger-core)** - A library for building and interacting with Swagger/OpenAPI definitions.
- ✔️ **[OpenTelemetry Collector](https://github.com/open-telemetry/opentelemetry-collector/tree/main)** - A collector for collecting telemetry data such as metrics, logs, and traces.
- ✔️ **[Lombok](https://github.com/projectlombok/lombok)** - A Java library that helps to reduce boilerplate code by generating getters, setters, and other common methods.
- ✔️ **[Flyway](https://github.com/flyway/flyway)** - A database migration tool for versioning and automating schema changes.
- ✔️ **[JPA Buddy](https://jpa-buddy.com)** - A productivity tool for working with JPA and Hibernate in IntelliJ IDEA.
- ✔️ **[UUID Creator](https://github.com/f4b6a3/uuid-creator)** - A library for generating UUIDs in Java.
- ✔️ **[QueryDSL](https://github.com/querydsl/querydsl)**- A framework for building type-safe queries for JPA, SQL, and other data sources.
- ✔️ **[Reflections](https://github.com/ronmamo/reflections)** - A Java library for metadata analysis and scanning of classpath resources.
- ✔️ **[gRPC Spring](https://github.com/grpc-ecosystem/grpc-spring)** - A framework for building gRPC services with Spring Boot.
- ✔️ **[Testcontainers](https://github.com/testcontainers/testcontainers-java)** - A Java library for creating and managing lightweight, throwaway instances of Docker containers for testing.
- ✔️ **[Mockito](https://github.com/mockito/mockito)** - A mocking framework for unit tests in Java.
- ✔️ **[JUnit](https://github.com/junit-team)** - A popular framework for writing and running unit tests in Java.


## The Domain And Bounded Context - Service Boundary

- `Keycloak Service`: The Keycloak Service is a identity provider for the authentication and authorization of users using [Keycloak](https://github.com/keycloak/keycloak). This service is responsible for creating new users and their corresponding roles permissions and handeling authentication and authorization with OpenID-Connect and OAuth2.

- `Flight Service`: The Flight Service is a bounded context `CRUD` service to handle flight related operations.

- `Passenger Service`: The Passenger Service is a bounded context for managing passenger information, tracking activities and subscribing to get notification for out of stock products.

- `Booking Service`: The Booking Service is a bounded context for managing all operation related to booking ticket.

![](./assets/booking-microservices.png)

## Structure of Project

In this project I used a mix of [clean architecture](https://jasontaylor.dev/clean-architecture-getting-started/), [vertical slice architecture](https://jimmybogard.com/vertical-slice-architecture/) and I used [feature folder structure](http://www.kamilgrzybek.com/design/feature-folders/) to structure my files.

I used [yarp reverse proxy](https://microsoft.github.io/reverse-proxy/articles/index.html) to route synchronous and asynchronous requests to the corresponding microservice. Each microservice has its dependencies such as databases, files etc. Each microservice is decoupled from other microservices and developed and deployed separately. Microservices talk to each other with Rest or gRPC for synchronous calls and use RabbitMq or Kafka for asynchronous calls.

We have a separate microservice ([IdentityServer](https://github.com/DuendeSoftware/IdentityServer)) for authentication and authorization of each request. Once signed-in users are issued a JWT token. This token is used by other microservices to validate the user, read claims and allow access to authorized/role specific endpoints.

I used [RabbitMQ](https://github.com/rabbitmq) as my MessageBroker for async communication between microservices using the eventual consistency mechanism. Each microservice uses [MassTransit](https://github.com/MassTransit/MassTransit) to interface with [RabbitMQ](https://github.com/rabbitmq) providing, messaging, availability, reliability, etc.

Microservices are `event based` which means they can publish and/or subscribe to any events occurring in the setup. By using this approach for communicating between services, each microservice does not need to know about the other services or handle errors occurred in other microservices.

After saving data in write side, I save a [Internal Command](https://github.com/kgrzybek/modular-monolith-with-ddd#38-internal-processing) record in my Persist Messages storage (like something we do in outbox pattern) and after committing transaction in write side, trigger our command handler in read side  and this handler could save their read models in our MongoDB database.

I treat each request as a distinct use case or slice, encapsulating and grouping all concerns from front-end to back.
When adding or changing a feature in an application in n-tire architecture, we are typically touching many "layers" in an application. We are changing the user interface, adding fields to models, modifying validation, and so on. Instead of coupling across a layer, we couple vertically along a slice. We `minimize coupling` `between slices`, and `maximize coupling` `in a slice`.

With this approach, each of our vertical slices can decide for itself how to best fulfill the request. New features only add code, we're not changing shared code and worrying about side effects.

<div align="center">
  <img src="./assets/vertical-slice-architecture.png" />
</div>

Instead of grouping related action methods in one controller, as found in traditional controllers, I used the [REPR pattern](https://deviq.com/design-patterns/repr-design-pattern). Each action gets its own small endpoint, consisting of a route, the action, and an `IMediator` instance. The request is passed to the `IMediator` instance, routed through a [`Mediator pipeline`](https://lostechies.com/jimmybogard/2014/09/09/tackling-cross-cutting-concerns-with-a-mediator-pipeline/) where custom middleware can log, validate and intercept requests. The request is then handled by a request specific `IRequestHandler` which performs business logic before returning the result.

The use of the [mediator pattern](https://dotnetcoretutorials.com/2019/04/30/the-mediator-pattern-in-net-core-part-1-whats-a-mediator/) in my controllers creates clean and thin controllers. By separating action logic into individual handlers we support the [Single Responsibility Principle](https://en.wikipedia.org/wiki/Single_responsibility_principle) and [Don't Repeat Yourself principles](https://en.wikipedia.org/wiki/Don%27t_repeat_yourself), this is because traditional controllers tend to become bloated with large action methods and several injected `Services` only being used by a few methods.

I used CQRS to decompose my features into small parts that makes our application:

- Maximize performance, scalability and simplicity.
- Easy to maintain and add features to. Changes only affect one command or query, avoiding breaking changes or creating side effects.
- It gives us better separation of concerns and cross-cutting concern (with help of mediator behavior pipelines), instead of bloated service classes doing many things.

Using the CQRS pattern, we cut each business functionality into vertical slices, for each of these slices we group classes (see [technical folders structure](http://www.kamilgrzybek.com/design/feature-folders)) specific to that feature together (command, handlers, infrastructure, repository, controllers, etc). In our CQRS pattern each command/query handler is a separate slice. This is where you can reduce coupling between layers. Each handler can be a separated code unit, even copy/pasted. Thanks to that, we can tune down the specific method to not follow general conventions (e.g. use custom SQL query or even different storage). In a traditional layered architecture, when we change the core generic mechanism in one layer, it can impact all methods.


## How to Run

> ### Docker Compose

Use the command below to run our `infrastructure` with `docker` using the `docker-compose.infrastructure.yaml` file at the root of the app:
```
docker-compose -f ./deployments/docker-compose/docker-compose.infrastructure.yaml up -d
```

> ### Build
To `build` all microservices, run this command in the `root` of each microservice where the `pom.xml` file is located:
```bash
mvn clean install
```

> ### Run
To `run` each microservice, run this command in the `root` of each microservice where the `pom.xml` file is located:
```bash
mvn spring-boot:run
```

> ### Test

To `test` all microservices, run this command in the `root` of each microservice where the `pom.xml` file is located:
```bash
dotnet test
```

> ### Documentation Apis

Each microservice provides `API documentation` and navigate to `/swagger-ui/index.html` to visit list of endpoints.

As part of API testing, I created the [booking.rest](./booking.rest) file which can be run with the [REST Client](https://github.com/Huachao/vscode-restclient) `VSCode plugin`.

# Support

If you like my work, feel free to:

- ⭐ this repository. And we will be happy together :)

Thanks a bunch for supporting me!

## Contribution

Thanks to all [contributors](https://github.com/meysamhadeli/booking-microservices-java-spring-boot/graphs/contributors), you're awesome and this wouldn't be possible without you! The goal is to build a categorized, community-driven collection of very well-known resources.

Please follow this [contribution guideline](./CONTRIBUTION.md) to submit a pull request or create the issue.

## Project References & Credits

- [https://github.com/jbogard/ContosoUniversityDotNetCore-Pages](https://github.com/jbogard/ContosoUniversityDotNetCore-Pages)
- [https://github.com/kgrzybek/modular-monolith-with-ddd](https://github.com/kgrzybek/modular-monolith-with-ddd)
- [https://github.com/oskardudycz/cqrs-is-simpler-with-java](https://github.com/oskardudycz/cqrs-is-simpler-with-java)

## License
This project is made available under the MIT license. See [LICENSE](https://github.com/meysamhadeli/booking-microservices/blob/main/LICENSE) for details.
