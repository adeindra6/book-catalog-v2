package com.adeindra6.catalog.dto;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class PublisherResponseDTO implements Serializable {
    private static final long serialVersionUID = 44061988L;

    private String publisherId;

    private String publisherName;
}
