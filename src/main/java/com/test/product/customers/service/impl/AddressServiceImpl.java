package com.test.product.customers.service.impl;

import com.test.product.customers.dto.reponse.AddressResponse;
import com.test.product.customers.dto.request.AddressRequest;
import com.test.product.customers.mapper.AddressRestMapper;
import com.test.product.customers.repository.AddressJpaRepository;
import com.test.product.customers.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressJpaRepository addressJpaRepository;
    private final AddressRestMapper addressRestMapper;

    @Transactional(rollbackFor = RuntimeException.class, timeout = 1000, propagation = Propagation.REQUIRES_NEW)
    @Override
    public AddressResponse createAddress(AddressRequest request) {
        return addressRestMapper.toResponse(addressJpaRepository.save(addressRestMapper.toEntity(request)));
    }
}
