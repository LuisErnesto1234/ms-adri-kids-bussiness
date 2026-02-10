package com.test.product.orders.domain.model;

import com.test.product.orders.domain.enums.OrderStatus;
import com.test.product.shared.domain.enums.GeneralStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

public record Order(
        UUID id,
        UUID customerId,
        AppliedCoupon appliedCoupon,
        OrderStatus status,
        PriceBreakdown priceBreakdown,
        List<OrderItem> items,
        ShippingDetails shippingDetails,
        GeneralStatus generalStatus,
        Instant createdAt,
        Instant updatedAt
) {

    public static Order createOrder(UUID customerId) {
        return new Order(
                UUID.randomUUID(),
                customerId,
                null,
                OrderStatus.PENDING_PAYMENT,
                // Precio inicial CERO
                PriceBreakdown.create(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO),
                List.of(),
                null, // Inicializamos en null (sin envío aún)
                GeneralStatus.ACTIVE,
                Instant.now(),
                Instant.now()
        );
    }

    public Order addItem(UUID variantId, Integer quantityToAdd, BigDecimal unitPrice) {
        if (this.status != OrderStatus.PENDING_PAYMENT) {
            throw new IllegalStateException("No se pueden editar órdenes cerradas");
        }

        // 1. Lógica de Items (Igual que tenías, está perfecta)
        List<OrderItem> newItems = new ArrayList<>(this.items);
        Optional<OrderItem> existingItem = newItems.stream()
                .filter(i -> i.variantId().equals(variantId))
                .findFirst();

        if (existingItem.isPresent()) {
            var item = existingItem.get();
            int totalQuantity = item.quantity() + quantityToAdd;
            newItems.set(newItems.indexOf(item), item.withQuantity(totalQuantity));
        } else {
            newItems.add(OrderItem.create(variantId, quantityToAdd, unitPrice));
        }

        // 2. Lógica de Precios (CORREGIDA)
        // Pasamos la nueva lista para calcular el nuevo desglose
        PriceBreakdown newPriceBreakdown = calculateNewPrice(newItems);

        return new Order(
                this.id,
                this.customerId,
                this.appliedCoupon,
                this.status,
                newPriceBreakdown, // Usamos el nuevo precio calculado
                Collections.unmodifiableList(newItems),
                this.shippingDetails,
                GeneralStatus.ACTIVE,
                this.createdAt,
                Instant.now()
        );
    }

    // Método para asignar envío (Te faltaba este para completar el flujo)
    public Order withShippingDetails(ShippingDetails details) {
        if (this.status != OrderStatus.PENDING_PAYMENT) {
            throw new IllegalStateException("No se pueden cambiar envíos de órdenes cerradas");
        }

        // Al cambiar el envío, cambia el costo total, hay que recalcular
        // Creamos una orden temporal con el nuevo envío para poder calcular
        Order tempOrder = new Order(
                this.id, this.customerId, this.appliedCoupon, this.status,
                this.priceBreakdown, this.items, details, // <--- Nuevo envío
                this.generalStatus, this.createdAt, this.updatedAt
        );

        // Recalculamos precios basados en los items actuales y el NUEVO envío
        PriceBreakdown newPrices = tempOrder.calculateNewPrice(this.items);

        return new Order(
                this.id, this.customerId, this.appliedCoupon, this.status,
                newPrices, this.items, details, this.generalStatus,  this.createdAt, Instant.now()
        );
    }

    public Order updateShippingAddress(Address address) {
        if (this.status == OrderStatus.SHIPPED) {
            throw new IllegalStateException("No se puede cambiar la dirección de envío de una orden que ya ha sido enviada.");
        }

        ShippingDetails updatedShippingDetails = new ShippingDetails(
                address,
                this.shippingDetails.carrier(),
                this.shippingDetails.trackingCode(),
                this.shippingDetails.cost(),
                this.shippingDetails.estimatedDeliveryDate()
        );

        // Recalculamos precios por si el cambio de dirección implica un cambio en el costo de envío (aunque no lo tenemos implementado, es buena práctica)
        PriceBreakdown newPrices = calculateNewPrice(this.items, updatedShippingDetails); // Esto no cambiará el costo de envío si no hay lógica para ello

        // Creamos una nueva orden con la dirección de envío actualizada y los precios recalculados
        return new Order(this.id, this.customerId, this.appliedCoupon, this.status,
                newPrices, this.items, updatedShippingDetails, this.generalStatus, this.createdAt, Instant.now()
        );
    }

    public Order markAsPaid() {
        if (this.status != OrderStatus.PENDING_PAYMENT) {
            throw new IllegalStateException("Estado incorrecto para pagar");
        }
        return new Order(
                this.id, this.customerId, this.appliedCoupon,
                OrderStatus.PAID,
                this.priceBreakdown, this.items, this.shippingDetails, this.generalStatus,
                this.createdAt, Instant.now()
        );
    }

    public Order applyCoupon(AppliedCoupon appliedCoupon) {
        if (this.status != OrderStatus.PENDING_PAYMENT) {
            throw new IllegalStateException("No se pueden aplicar cupones a órdenes cerradas");
        }

        // Al aplicar cupón, cambia el precio, así que recalculamos usando los items actuales
        // pero pasando el NUEVO cupón a la lógica de cálculo

        // Truco: Creamos una copia temporal con el cupón puesto para que el helper calcule
        Order tempOrder = new Order(
                this.id, this.customerId,
                appliedCoupon, // <--- Aquí va el nuevo cupón
                this.status, this.priceBreakdown, this.items,
                this.shippingDetails, this.generalStatus, this.createdAt, this.updatedAt
        );

        PriceBreakdown newPrices = tempOrder.calculateNewPrice(this.items);

        return new Order(
                this.id, this.customerId,
                appliedCoupon, // Guardamos el cupón
                this.status,
                newPrices, // Guardamos los precios actualizados
                this.items, this.shippingDetails, this.generalStatus, this.createdAt, Instant.now()
        );
    }

    public Order removeItem(Set<OrderItem> orderItems) {
        if (orderItems == null || orderItems.isEmpty()) {
            throw new IllegalStateException("Los items a remover no pueden estar vacias");
        }

        List<OrderItem> nowList = new ArrayList<>(this.items);

        nowList.removeAll(orderItems);

        PriceBreakdown breakdown = calculateNewPrice(nowList);

        return new Order(
                this.id,
                this.customerId,
                this.appliedCoupon,
                this.status,
                breakdown, // Usamos el nuevo precio calculado
                Collections.unmodifiableList(nowList),
                this.shippingDetails,
                this.generalStatus,
                this.createdAt,
                Instant.now()
        );
    }

    public Order confirmPayment() {
        if (this.status == OrderStatus.PENDING_PAYMENT) {
            return new Order(
                    this.id,
                    this.customerId,
                    this.appliedCoupon,
                    OrderStatus.PAID,
                    this.priceBreakdown,
                    this.items,
                    this.shippingDetails,
                    this.generalStatus,
                    this.createdAt,
                    Instant.now()
            );
        }

        throw new IllegalStateException("No se puede confirmar el pago en el estado actual: " + this.status);
    }

    public Order cancel() {
        if (this.status == OrderStatus.PAID || this.status == OrderStatus.SHIPPED ||
                this.status == OrderStatus.DELIVERED) {
            throw new IllegalStateException("No se puede cancelar una orden en estado: " + this.status);
        }
        return new Order(
                this.id, this.customerId, this.appliedCoupon,
                OrderStatus.CANCELLED,
                this.priceBreakdown, this.items, this.shippingDetails,
                this.generalStatus,
                this.createdAt, Instant.now()
        );
    }

    public Order markAsShipped(String carrier) {
        if (this.status != OrderStatus.PAID) {
            throw new IllegalStateException("No se puede marcar como enviado una orden que no está PAGADA. Estado actual: " + this.status);
        }


        ShippingDetails newShippingDetails = new ShippingDetails(
                this.shippingDetails.address(), carrier, generateTrackingCode(), this.shippingDetails.cost(),
                this.shippingDetails.estimatedDeliveryDate()
        );
        return new Order(
                this.id,
                this.customerId,
                this.appliedCoupon,
                OrderStatus.SHIPPED,
                this.priceBreakdown,
                this.items,
                newShippingDetails,
                this.generalStatus,
                this.createdAt,
                Instant.now()
        );
    }

    private String generateTrackingCode() {
        return "TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public Order markAsDelivered() {
        if (this.status != OrderStatus.SHIPPED) {
            throw new IllegalStateException("No se puede marcar como entregado una orden que no está ENVIADA. Estado actual: " + this.status);
        }
        return new Order(
                this.id,
                this.customerId,
                this.appliedCoupon,
                OrderStatus.DELIVERED,
                this.priceBreakdown,
                this.items,
                this.shippingDetails,
                this.generalStatus,
                this.createdAt,
                Instant.now()
        );
    }

    public Order markAsReturned() {
        if (this.status != OrderStatus.DELIVERED) {
            throw new IllegalStateException("No se puede marcar como devuelta una orden que no está ENTREGADA. Estado actual: " + this.status);
        }
        return new Order(
                this.id,
                this.customerId,
                this.appliedCoupon,
                OrderStatus.RETURNED,
                this.priceBreakdown,
                this.items,
                this.shippingDetails,
                this.generalStatus,
                this.createdAt,
                Instant.now()
        );
    }

    public Order markAsRefunded() {
        if (this.status != OrderStatus.RETURNED) {
            throw new IllegalStateException("No se puede reembolsar una orden que no está DEVUELTA. Estado actual: " + this.status);
        }
        return new Order(
                this.id,
                this.customerId,
                this.appliedCoupon,
                OrderStatus.REFUNDED,
                this.priceBreakdown,
                this.items,
                this.shippingDetails,
                this.generalStatus,
                this.createdAt,
                Instant.now()
        );
    }

    public Order markAsCompleted() {
        if (this.status != OrderStatus.DELIVERED) {
            throw new IllegalStateException("No se puede marcar como completada una orden que no está ENTREGADA. Estado actual: " + this.status);
        }
        return new Order(
                this.id,
                this.customerId,
                this.appliedCoupon,
                OrderStatus.COMPLETED,
                this.priceBreakdown,
                this.items,
                this.shippingDetails,
                this.generalStatus,
                this.createdAt,
                Instant.now()
        );
    }

    // --- HELPER PRIVADO OPTIMIZADO ---
    // Devuelve solo el PriceBreakdown, no toda la orden
    private PriceBreakdown calculateNewPrice(List<OrderItem> currentItems) {
        return calculateNewPrice(currentItems, this.shippingDetails);
    }

    // Sobrecarga para permitir pasar un ShippingDetails diferente (ej. al actualizar la dirección)
    private PriceBreakdown calculateNewPrice(List<OrderItem> currentItems, ShippingDetails currentShippingDetails) {
        BigDecimal subTotal = currentItems.stream()
                .map(OrderItem::subTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Usamos los detalles de envío pasados como argumento o los de la orden
        BigDecimal shippingCost = (currentShippingDetails != null)
                ? currentShippingDetails.cost()
                : BigDecimal.ZERO;

        // CAMBIO 2: Calcular descuento dinámicamente
        BigDecimal discount = BigDecimal.ZERO;

        if (this.appliedCoupon != null) {
            // Le pedimos al Value Object que haga la matemática
            discount = this.appliedCoupon.calculateDiscount(subTotal);
        }

        return PriceBreakdown.create(
                subTotal,
                discount, // Pasamos el descuento calculado
                shippingCost
        );
    }


}