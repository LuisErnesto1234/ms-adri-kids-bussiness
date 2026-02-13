package com.adri.kids.orders.app.usecases.checkout;

import java.util.UUID;

public record CheckoutCommand(
        UUID orderId
) {}
