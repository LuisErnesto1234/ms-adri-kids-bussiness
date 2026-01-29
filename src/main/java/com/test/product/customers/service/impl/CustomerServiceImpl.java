package com.test.product.customers.service.impl;

import com.test.product.customers.dto.excepcion.NotFoundException;
import com.test.product.customers.dto.reponse.CustomerResponse;
import com.test.product.customers.dto.request.CustomerRequest;
import com.test.product.customers.entity.CustomerEntity;
import com.test.product.customers.mapper.CustomerRestMapper;
import com.test.product.customers.repository.CustomerJpaRepository;
import com.test.product.customers.service.CustomerService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerRestMapper customerRestMapper;

    @Transactional(rollbackFor = RuntimeException.class, timeout = 1000, propagation = Propagation.REQUIRES_NEW)
    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        CustomerEntity customerEntity = customerRestMapper.toEntity(request);

        return customerRestMapper.toResponse(customerJpaRepository.save(customerEntity));
    }

    @Transactional(rollbackFor = RuntimeException.class, timeout = 1000, propagation = Propagation.REQUIRES_NEW)
    @Override
    public CustomerResponse updateCustomer(UUID id, CustomerRequest request) {
        CustomerEntity customerEntity = customerJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El customer no existe registrado en la base de datos"));

        customerRestMapper.updateCustomerEntity(request, customerEntity);

        return customerRestMapper.toResponse(customerJpaRepository.save(customerEntity));
    }

    @Transactional(rollbackFor = RuntimeException.class, timeout = 1000, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void deleteCustomerById(UUID id) {
        CustomerEntity customerEntity = customerJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El customer no existe registrado en la base de datos"));

        customerJpaRepository.delete(customerEntity);
    }
}
