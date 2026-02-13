package com.adri.kids.customers.entity;

import com.adri.kids.customers.dto.enums.AddressType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "addresses")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false, length = 50)
    private String city;

    @Column(nullable = false, length = 50)
    private String state;

    @Column(name = "postal_code", nullable = false, length = 10)
    private String postalCode;

    @Column(nullable = false, length = 50)
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(name = "address_type", length = 10)
    private AddressType type; // Para saber si es de envío (SHIPPING) o facturación (BILLING)

    // Relación: Muchas direcciones pertenecen a un cliente.
    // FetchType.LAZY es una buena práctica para no cargar el cliente a menos que se necesite.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;
}