package com.adeindra6.catalog.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.adeindra6.catalog.dto.UserDetailResponseDTO;

public interface AppUserService extends UserDetailsService {
    
    public UserDetailResponseDTO findUserDetail();

}
