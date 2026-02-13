package com.adri.kids.customers.service;

import com.adri.kids.customers.dto.reponse.CustomerResponse;
import com.adri.kids.customers.dto.request.CustomerRequest;

import java.util.UUID;

public interface CustomerService {
    CustomerResponse createCustomer(CustomerRequest request);

    CustomerResponse updateCustomer(UUID id, CustomerRequest request);

    void deleteCustomerById(UUID id);
}
