package com.adeindra6.catalog.dto;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CategoryCreateUpdateRequestDTO implements Serializable {

    private static final long serialVersionUID = 968311783L;
    
    @NotBlank
    private String code;

    @NotBlank
    private String name;

    private String description;
}
