package com.DomDevs.inventoryservice;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@RequiredArgsConstructor
@AutoConfigureMockMvc

class InventoryServiceApplicationTests {
    @Container
    private static final MySQLContainer mySQLContainer = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("testcontainer")
            .withUsername("test")
            .withPassword("test")
            .withInitScript("test-init.sql");
    @Autowired
    private MockMvc mockMvc;


    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @Test
    void testMySQLContainerIsRunning() {
        assert (mySQLContainer.isRunning());
    }

    @Test
    void shouldCheckInventory() throws Exception {
        String paramName = "skuCode";
        String skuCode = "iphone_12_red";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/inventory").
                        param(paramName, skuCode)).
                andExpect(status().isOk());
    }

}



