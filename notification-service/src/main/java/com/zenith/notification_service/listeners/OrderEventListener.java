package com.zenith.notification_service.listeners;

import com.zenith.notification_service.events.OrderEvent;
import com.zenith.notification_service.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderEventListener {
    @KafkaListener(topics="orders-topic")
    public void handleOrdersNotifications(String message){
        var orderEvent = JsonUtils.fromJson(message, OrderEvent.class);

        //send email to costumers, send SMS to costumers, etc.
        //notify another service...

        log.info("Order {} event received for order : {} with {} items" , orderEvent.orderStatus(),orderEvent.orderNumber(), orderEvent.itemsCount());
    }
}
