package com.adeindra6.catalog.dto;

import java.io.Serializable;

import com.adeindra6.catalog.annotation.LogThisArg;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@LogThisArg
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PublisherCreateRequestDTO implements Serializable {
    private static final long serialVersionUID = 663791717L;

    @NotBlank(message = "publisher_name.must.not.be.blank")
    private String publisherName;

    @NotBlank(message = "company_name.must.not.be.blank")
    private String companyName;

    private String address;
}
