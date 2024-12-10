package com.adeindra6.catalog.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorQueryDTO implements Serializable {

    private static final long serialVersionUID = 271021854L;
    
    private Long bookId;

    private String authorName;
}
