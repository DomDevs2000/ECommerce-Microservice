package com.DomDevs.orderservice;

import com.DomDevs.orderservice.dto.OrderLineItemsDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class OrderServiceApplicationTests {
    @Container
    private static final MySQLContainer mySQLContainer = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("testcontainer")
            .withUsername("test")
            .withPassword("test");
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @Test
    void testMySQLContainerIsRunning() {
        assert(mySQLContainer.isRunning());
    }

    @Test
    void shouldCreateOrder() throws Exception {
        OrderLineItemsDto orderRequest = getOrderRequest();
        String orderRequestAsString = objectMapper.writeValueAsString(orderRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/order").contentType(MediaType.APPLICATION_JSON).content(orderRequestAsString)).andExpect(status().isCreated());

    }

    private OrderLineItemsDto getOrderRequest() {
        return OrderLineItemsDto.builder().skuCode("iphone_13").price(BigDecimal.valueOf(1250)).quantity(1).build();
    }


}
