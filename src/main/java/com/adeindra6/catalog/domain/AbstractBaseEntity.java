package com.adeindra6.catalog.domain;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import lombok.Data;
import jakarta.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class AbstractBaseEntity implements Serializable {
    @Column(name = "secure_id", nullable = false, unique = true)
    private String secureId=UUID.randomUUID().toString();

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private Boolean deleted;
}
