package com.emmett.demo_flux.containers;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class BaseContainer {

    @Container
    @ServiceConnection
    static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0");

    static {
        // This guarantees the container is fully healthy before Spring Context initializes
        mongoDBContainer.start();
    }
//    @DynamicPropertySource
//    static void setMongoProperties(DynamicPropertyRegistry registry) {
//        // Sets the reactive connection string dynamically
//        registry.add("spring.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
//    }
}
