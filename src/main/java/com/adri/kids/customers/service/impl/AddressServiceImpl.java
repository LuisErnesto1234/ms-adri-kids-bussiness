package com.adri.kids.customers.service.impl;

import com.adri.kids.customers.dto.reponse.AddressResponse;
import com.adri.kids.customers.dto.request.AddressRequest;
import com.adri.kids.customers.mapper.AddressRestMapper;
import com.adri.kids.customers.repository.AddressJpaRepository;
import com.adri.kids.customers.service.AddressService;
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
