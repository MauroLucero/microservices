package com.zenith.orders_service.services;


import com.zenith.orders_service.events.OrderEvent;
import com.zenith.orders_service.models.enums.OrderStatus;
import com.zenith.orders_service.models.dtos.*;
import com.zenith.orders_service.models.entities.Order;
import com.zenith.orders_service.models.entities.OrderItems;
import com.zenith.orders_service.repositories.OrderRepository;
import com.zenith.orders_service.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String,String> kafkaTemplate;

    public OrderResponse placeOrder(OrderRequest orderRequest){

        //Check for inventory
        BaseResponse result = this.webClientBuilder.build()
                .post()
                .uri("lb://inventory-service/api/inventory/in-stock")
                .bodyValue(orderRequest.getOrderItemsList())
                .retrieve()
                .bodyToMono(BaseResponse.class)
                .block();

        if(result != null && !result.hasErrors()){

            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setOrderItemsList(orderRequest.getOrderItemsList().stream().map(orderItemsRequest -> mapOrderItemRequestToOrderItem(orderItemsRequest,order)).toList());

           var savedOrder = this.orderRepository.save(order);

           this.kafkaTemplate.send("orders-topic", JsonUtils.toJson(
                   new OrderEvent(savedOrder.getOrderNumber(),savedOrder.getOrderItemsList().size(), OrderStatus.PLACED)
           ));

           return mapToOrderResponse(savedOrder);

        }else{
            throw new IllegalArgumentException("Some of the products are not in stock");
        }

    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orders = this.orderRepository.findAll();

        return orders.stream().map(this::mapToOrderResponse).toList();

    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(order.getId(), order.getOrderNumber()
                , order.getOrderItemsList().stream().map(this::mapToOrderItemRequest).toList());
    }

    private OrderItemsResponse mapToOrderItemRequest(OrderItems orderItems) {
        return new OrderItemsResponse(orderItems.getId(), orderItems.getSku(), orderItems.getPrice(), orderItems.getQuantity());
    }

    private OrderItems mapOrderItemRequestToOrderItem(OrderItemsRequest orderItemsRequest, Order order) {
        return OrderItems.builder()
                .id(orderItemsRequest.getId())
                .sku(orderItemsRequest.getSku())
                .price(orderItemsRequest.getPrice())
                .quantity(orderItemsRequest.getQuantity())
                .order(order)
                .build();
    }
}
