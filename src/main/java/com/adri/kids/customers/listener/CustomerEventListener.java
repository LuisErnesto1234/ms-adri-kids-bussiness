package com.adri.kids.customers.listener;

import com.adri.kids.customers.repository.CustomerJpaRepository;
import com.adri.kids.shared.events.customer.CustomerFindByUsernameEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomerEventListener {

    private final CustomerJpaRepository customerJpaRepository;

    @TransactionalEventListener
    public void handle(CustomerFindByUsernameEvent event){
        try {
            UUID customerId = customerJpaRepository.findByEmail(event.getUsername())
                    .orElseThrow().getId();

            event.getCustomerId().complete(customerId);
        } catch (Exception e) {
            event.getCustomerId().completeExceptionally(e);
        }
    }

}
