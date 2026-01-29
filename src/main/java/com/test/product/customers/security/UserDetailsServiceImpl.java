package com.test.product.customers.security;

import com.test.product.customers.dto.enums.Status;
import com.test.product.customers.entity.CustomerEntity;
import com.test.product.customers.entity.RoleEntity;
import com.test.product.customers.repository.CustomerJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CustomerJpaRepository customerJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomerEntity customer = customerJpaRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Unificamos Roles y Permisos en una sola lista de autoridades
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        // 1. Agregar Roles (prefijo ROLE_ es buena práctica en Spring)
        customer.getRoles().forEach(role ->
                authorityList.add(new SimpleGrantedAuthority("ROLE_" + role.getName()))
        );

        // 2. Agregar Permisos
        customer.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .forEach(permission ->
                        authorityList.add(new SimpleGrantedAuthority(permission.getName()))
                );

        return User.builder()
                .username(customer.getEmail())
                .password(customer.getPassword())
                .authorities(authorityList) // Pasamos los autoridades junto aquí
                .disabled(customer.getStatus() == Status.INACTIVE || customer.getStatus() == Status.BANNED)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .build();
    }
}