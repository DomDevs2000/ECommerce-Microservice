package com.DomDevs.orderservice.service;

import com.DomDevs.orderservice.dto.OrderLineItemsDto;
import com.DomDevs.orderservice.dto.OrderRequest;
import com.DomDevs.orderservice.model.Order;
import com.DomDevs.orderservice.model.OrderLineItems;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsList().stream().map(this::mapToDto).toList();

        order.setOrderLineItemsList(orderLineItems);

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;

    }
}
