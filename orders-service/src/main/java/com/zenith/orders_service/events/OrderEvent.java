package com.zenith.orders_service.events;

import com.zenith.orders_service.models.enums.OrderStatus;

public record OrderEvent(String orderNumber, int itemsCount, OrderStatus orderStatus) {
}
