package com.adri.kids.customers.service;

import com.adri.kids.customers.dto.reponse.AddressResponse;
import com.adri.kids.customers.dto.request.AddressRequest;

public interface AddressService {
    AddressResponse createAddress(AddressRequest request);
}
