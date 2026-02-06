package com.test.product.orders.domain.port.out.gateway;

import java.util.UUID;

public interface CustomerGateway {
    UUID getCustomerIdByUsername(String username);
}
