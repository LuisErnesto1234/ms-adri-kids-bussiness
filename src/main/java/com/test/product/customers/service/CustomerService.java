package com.test.product.customers.service;

import com.test.product.customers.dto.reponse.CustomerResponse;
import com.test.product.customers.dto.request.CustomerRequest;

import java.util.UUID;

public interface CustomerService {
    CustomerResponse createCustomer(CustomerRequest request);

    CustomerResponse updateCustomer(UUID id, CustomerRequest request);

    void deleteCustomerById(UUID id);
}
