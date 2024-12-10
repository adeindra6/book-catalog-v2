package com.adeindra6.catalog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adeindra6.catalog.domain.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    
    public Optional<AppUser> findByUsername(String username);
}
