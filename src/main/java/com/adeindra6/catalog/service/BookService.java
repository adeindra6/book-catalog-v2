package com.adeindra6.catalog.service;

import java.util.List;

import com.adeindra6.catalog.dto.BookCreateRequestDTO;
import com.adeindra6.catalog.dto.BookDetailResponseDTO;
import com.adeindra6.catalog.dto.BookUpdateRequestDTO;
import com.adeindra6.catalog.dto.ResultPageResponseDTO;
import com.adeindra6.catalog.dto.BookListResponseDTO;

public interface BookService {
    public BookDetailResponseDTO findBookDetailById(String bookId);

    public List<BookDetailResponseDTO> findBookListDetail();

    public void createNewBook(BookCreateRequestDTO dto);

    public void updateBook(Long bookId, BookUpdateRequestDTO dto);

    public void deleteBook(Long bookId);

    public ResultPageResponseDTO<BookListResponseDTO> findBookList(Integer page, Integer limit, String sortBy, String direction, String publisherName, String bookTitle, String authorName);
}
