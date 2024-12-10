package com.adeindra6.catalog.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BookQueryDTO implements Serializable {

    private static final long serialVersionUID = 683327743L;

    private Long id;

    private String bookId;
    
    private String bookTitle;

    private String publisherName;

    private String description;
}
