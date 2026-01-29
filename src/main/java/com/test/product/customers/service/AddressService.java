package com.test.product.customers.service;

import com.test.product.customers.dto.reponse.AddressResponse;
import com.test.product.customers.dto.request.AddressRequest;

public interface AddressService {
    AddressResponse createAddress(AddressRequest request);
}
