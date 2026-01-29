package com.test.product.customers.security;

import com.test.product.customers.entity.CustomerEntity;
import com.test.product.customers.entity.RoleEntity;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final CustomerEntity customer;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (customer.getRoles() == null) {
            return List.of();
        }

        return customer.getRoles().stream()
                .map(RoleEntity::getPermissions)
                .flatMap(r -> {
                    if (r == null) {
                        return Stream.of();
                    }
                    return r.stream();
                })
                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public @Nullable String getPassword() {
        // Suponiendo que tu entidad Customer tiene un método getPassword().
        return customer.getPassword();
    }

    @Override
    public @Nullable String getUsername() {
        // Suponiendo que usas el email como username.
        return customer.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
