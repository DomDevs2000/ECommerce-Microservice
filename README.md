View this project in [GitHub](https://github.com/DomDevs2000/ECommerce-Microservice)

## Project Description

For this project, I developed a fully integrated E-commerce service that allows users to create orders, check products
and inventory and successfully place orders if products are in stock.
Functionality includes product and inventory queries, order placements, and event-driven notifications using Kafka once orders are successfully placed. Ensured security with Spring WebFlux Security and OAuth 2.0 JWT authentication. Implemented inter-service communication using Spring Cloud Netflix Eureka with inter-service
fault tolerance using Spring Cloud Circuit Breaker via the Resilience4j library.
Ensured website functionality with comprehensive unit testing using JUnit5 and MockMvc and integration testing using
TestContainers.
This entire project is Dockerized using, Docker, Docker Hub and Google Cloud Jib.

## Technologies Used

-   Java 17
-   Spring Boot 3.0.5
-   Spring Security
-   Spring Cloud Netflix Eureka(Service Discovery)
-   Apache Kafka (Event-Driven Architecture)
-   KeyCloak (OAuth 2.0 JWT)
-   Docker
-   Resilience4J (Circuit Breaker)
-   MySQL
-   MongoDB
-   Zipkin (Distributed Tracing)
-   JUnit5 (Unit Testing)
-   MockMVC (Unit Testing)
-   TestContainers(Integration Testing)

## Microservice Architecture

![Architecture](https://github.com/DomDevs2000/microservice-images/assets/109763238/0c55febe-3c8d-4514-bf4e-03d9453c5ecc)

## Why Did I Decide To Create This Project
I decided to build a microservice architecture due to the fact I've not built one before and wanted to dive deeper and learn how to create a microservice project, find out its best uses and its drawbacks and how it compares to other types of architectures like monolith projects. The idea to create e E-commerce related services was due to the fact I wanted to have hands-on experience on how real world services would work and interact under this specific architecture.


## What Did I Learn
I learnt a lot about microservices architecture, connecting each service using API gateways and inter-service communication using service discovery and registry as well as implementing inter-service fault tolerance. In addition to this, I have learnt a lot about how Kafka and event-driven architecture works. I have also deepened my object-oriented programming skills by implementing consistent DTO and MVC design patterns. 
I believe microservices are great, however, should be created once a project is already built as a monolithic and when needed to scale further, slowly migrate to microservices, instead of creating the entire project as a microservice from the start.

# Services In Action

### Eureka
Once the docker containers have spun up via docker compose, we need to check that all services are registered to the Eureka discovery server. To do this we need to visit port ```8761``` and will be greeted with this screen:
![Eureka](https://github.com/DomDevs2000/microservice-images/assets/109763238/0f740a35-6a4f-48ea-9721-ddd8c27f6e9f)
Here we can see that all services are registered to the Eureka server.

### KeyCloak 
Before we can use our services, we need to ensure we have an OAuth 2 JWT token, otherwise each service will return a 401 error for being unauthorised. To do this, we need to create a client inside Keycloak, retrieve the realm token URI and the client secret, so that we can generate a token inside a HTTP REST Client such as Postman or Insomnia.

### Product Service

To view the product catalog, make a HTTP GET request to `/api/product`
To create a product for the product catalog, please make a HTTP POST request to `/api/product/create` with the body in
JSON, here is an example:

```
{
   "name": "iphone_12_black",
   "description": "Black iPhone 12",
   "price": 1000
}
```
### Inventory Service

To view the inventory, make a HTTP GET request to `/api/inventory?skuCode={skuCode}`. This will return the
queried product, and will state if its in stock or not.

![Is In Stock](https://github.com/DomDevs2000/microservice-images/assets/109763238/7e1ba039-1008-489d-bd9c-17257dc85579)

Here is an example where the product is not in stock, we can see that it returns false.

![Not in stock ](https://github.com/DomDevs2000/microservice-images/assets/109763238/bbf5b34f-3a91-4ad3-b937-ab2d8475d6de)


### Order Service

To create an order, make a HTTP POST request to `/api/order` with the body in JSON,
here is an example:

```
{
"orderLineItemsDto": [
    {
   "name": "iphone_12_black",
   "quantity": "1",
   "price": 1000
    }
]
}
```

The order service will query the inventory service, if the requested item is in stock, the order will be placed
successfully, otherwise it will send a response stating that the product is not in stock.

![Order Placed](https://github.com/DomDevs2000/microservice-images/assets/109763238/86c97f2d-1ae0-4133-a19e-28ca676c061b)


### Notification Service

Once the order is successfully placed, the notification service (powered by kafka) will return in the logs including the auto generated order id.

![Notification](https://github.com/DomDevs2000/microservice-images/assets/109763238/0d9f58cc-130b-4453-849c-caf111cd33fb)


### Zipkin - Distributed Tracing

Zipkin allows us to see the traces of each HTTP request. ZipKin helps gather timing data needed to troubleshoot latency problems in service architectures

![Zipkin](https://github.com/DomDevs2000/microservice-images/assets/109763238/370a7008-2812-4606-b40a-c2cc4fcd4823)

# Testing
I implemented integration testing in this project, utilising the TestContainers plugin. This test allows me to spin up a container with a database, build and save or search that object in the database. 3 services were tested (Product, Inventory and Order) ensuring that the services' main function was carried out.
#### Inventory Service
For example, this tests the inventory service and checks that the inventory was queried. The first test checks the container is running.
![Inventory Test](https://github.com/DomDevs2000/microservice-images/assets/109763238/742b9b61-38f6-4c2e-b89d-a5d7e6fc8d21)

Here we can see in the logs that the inventory was in fact checked, as the Inventory Service logs "Checking inventory" when a request is made.
![Logs](https://github.com/DomDevs2000/microservice-images/assets/109763238/bb764d4f-9bb0-4924-b189-25533e0358d7)

The other service tests are very similar, either creating a new product in a mongodb database or creating an order for the order service. 
#### Product Service
Here we can see that a product is saved.
![Product Service](https://github.com/DomDevs2000/microservice-images/assets/109763238/8487e751-9de5-441d-b309-028c90e5a3ee)
#### Order Service
Test result for order service:
![Order Service](https://github.com/DomDevs2000/microservice-images/assets/109763238/e67768eb-17c9-4d62-a4a0-fe73fd3ed6bc)

### Test Coverage 
As we are integration testing each service's full function and not specific units, class coverage is the best indicator that we are in fact testing the correct things. Each test is achieving 75-100% class coverage.
##### Order Service Coverage
![Order Service Coverage](https://github.com/DomDevs2000/microservice-images/assets/109763238/4ffe1842-0c3e-4cc8-a91f-9416cb513c10)
##### Inventory Service Coverage
![Inventory Service Coverage](https://github.com/DomDevs2000/microservice-images/assets/109763238/40d1f66f-0076-4545-88c2-570ebc6dd45b)
##### Product Service Coverage
![Product Service Coverage](https://github.com/DomDevs2000/microservice-images/assets/109763238/3acd80e9-840d-4aec-9a20-d61828c0237c)


# Dockerizing The Project

The entire project is dockerized, using Google Cloud Jib to create a docker image of each microservice, which
automatically pushes each build to a remote repository (Docker Hub), where the docker compose file will pull and run
these containers. 3rd-party services, such as Kafka, Key Cloak and ZipKin are also run via docker compose by pulling and spinning up
their respective containers. I am currently in the process of deploying this project using Kubernetes via K8s.


