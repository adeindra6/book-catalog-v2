package com.adeindra6.catalog.dto;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class UserDetailResponseDTO implements Serializable {
    
    private static final long serialVersionUID = 916449189L;

    private String username;
}
