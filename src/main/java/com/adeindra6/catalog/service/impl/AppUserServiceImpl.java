package com.adeindra6.catalog.service.impl;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.adeindra6.catalog.dto.UserDetailResponseDTO;
import com.adeindra6.catalog.exception.ResourceNotFoundException;
import com.adeindra6.catalog.repository.AppUserRepository;
import com.adeindra6.catalog.service.AppUserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AppUserServiceImpl implements AppUserService {
    
    private final AppUserRepository appUserRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsername(username).
            orElseThrow(() -> new ResourceNotFoundException("Invalid Username"));
    }

    @Override
    public UserDetailResponseDTO findUserDetail() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        UserDetailResponseDTO dto = new UserDetailResponseDTO();
        String username = ctx.getAuthentication().getName();
        dto.setUsername(username);

        return dto;
    }
    
}
