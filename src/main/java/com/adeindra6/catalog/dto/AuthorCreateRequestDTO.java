package com.adeindra6.catalog.dto;

import java.util.List;

import com.adeindra6.catalog.validator.annotation.ValidAuthorName;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class AuthorCreateRequestDTO {

    @ValidAuthorName
    @NotBlank
    private String authorName;

    @NotNull
    private Long birthDate;

    @NotEmpty
    private List<AddressCreateRequestDTO> addresses;
}
