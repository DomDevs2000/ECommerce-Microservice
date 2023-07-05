View this project in [GitHub](https://github.com/DomDevs2000/ECommerce-Microservice)

## Project Description

For this project, I developed a fully integrated E-commerce service that allows users to create orders, check products
and inventory and successfully place orders if products are in stock.
Functionality includes product and inventory queries, order placements, and event-driven notifications using Kafka once orders are successfully placed. Ensured security with Spring WebFlux Security and OAuth 2.0 JWT authentication. Implemeneted inter-service communication using Spring Cloud Netflix Eureka with inter-service
fault tolerance using Spring Cloud Circuit Breaker via the Resilience4j library.
Ensured website functionality with comprehensive unit testing using JUnit5 and MockMvc and integration testing using
TestContainers.
This entire project is Dockerized using, Docker, Docker Hub and Google Cloud Jib.

## Technologies Used

-   Java 17,
-   Spring Boot 3.0.5,
-   Spring Security,
-   Spring Cloud Netflix Eureka(Service Discovery),
-   Apache Kafka (Event-Driven Architecture),
-   KeyCloak (OAuth 2.0 JWT)
-   Docker,
-   Resilience4J (Circuit Breaker),
-   MySQL,
-   MongoDB,
-   Zipkin (Distributed Tracing),
-   JUnit5 (Unit Testing)
-   MockMVC (Unit Testing)
-   TestContainers(Integration Testing)

## Microservice Architecture

![Architecture](https://github.com/DomDevs2000/ECommerce-Microservice/assets/109763238/c731940f-ad27-433e-9d89-2c182403294b)

## Why Did i Decide To Create This Project
I decided to build a microservice architecture due to the fact I've not built one before and wanted to dive deeper and learn how to create a microservice project, find out its best uses and its drawbacks and how it compares to other types of architectures like monolith projects. The idea to create e E-commerce related services was due to the fact I wanted to have hands on experience on how real world services would work and interact under this specific architecture.


## What Did I Learn
Creating this project I learnt a lot about microservices architecture, connecting each service using API gateways and inter-service communication using service discovery and registry as well as implementing inter-service fault tolerance.


## Services In Action

### Eureka
Once the docker containers have spun up via docker compose, we need to check that all services are registered to the Eureka discovery server. To do this we need to visit port ```8761``` and will be greeted with this screen:
![Eureka](https://github.com/DomDevs2000/ECommerce-Microservice/assets/109763238/de52d47f-269a-411a-8f13-fb04ca56e297)
Here we can see that all services are registered to the Eureka server.

### KeyCloak (OAuth2)
Before we can use our services, we need to ensure we have an OAuth 2 JWT token, otherwise each service will return a 401 error for being unauthorised. To do this, we need to create a client inside of Keycloak, retrieve the realm token URI and the client secret, so that we can generate a token inside of a HTTP REST Client such as Postman or Insomnia.



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
queried product, it's quantity count and will state if its in stock or not.
![Inventory Service](https://github.com/DomDevs2000/microservice-images/assets/109763238/7e1ba039-1008-489d-bd9c-17257dc85579)
### Order Service

To create an order, make a HTTP POST request to `/api/order` with the body in JSON,
here is an example:

```
{
"orderLineItemsDto": {
   "name": "iphone_12_black",
   "quantity": "1",
   "price": 1000
   }
}
```
![Order Placed](https://github.com/DomDevs2000/microservice-images/assets/109763238/86c97f2d-1ae0-4133-a19e-28ca676c061b)
The order service will query the inventory service, if the requested item is in stock, the order will be placed
successfully, otherwise it will send a response stating that the product is not in stock.



### Notification
Once the order is successfully placed, the notification service (powered by kafka) will return in the logs including the order id.

![Notification](https://github.com/DomDevs2000/microservice-images/assets/109763238/82802670-f245-4ed7-a729-249e9d190434)


### Zipkin - Distributed Tracing
Zipkin allows us to see the traces of each HTTP request.

![example](https://github.com/DomDevs2000/microservice-images/assets/109763238/370a7008-2812-4606-b40a-c2cc4fcd4823)
# Dockerizing The Project

The entire project is dockerized, using Google Cloud Jib to create a docker image of each microservice, which
automatically pushes each build to a remote repository (Docker Hub), where the docker compose file will pull and run
these containers. 3rd-party services, such as Kafka, Key Cloak and ZipKin are also run via docker compose by pulling and spinning up
their respective containers. I am currently in the process of deploying this project using Kubernetes via K8s.


