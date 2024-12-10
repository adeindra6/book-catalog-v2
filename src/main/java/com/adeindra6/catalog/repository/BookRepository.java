package com.adeindra6.catalog.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.adeindra6.catalog.domain.Book;
import com.adeindra6.catalog.dto.BookQueryDTO;

public interface BookRepository extends JpaRepository<Book, Long> {
    public Optional<Book> findById(Long id);

    public Optional<Book> findBySecureId(String id);

    @Query("SELECT DISTINCT new com.adeindra6.catalog.dto.BookQueryDTO(b.id, b.secureId, b.title, p.name, b.description) " +
           "FROM Book b " + 
           "INNER JOIN Publisher p ON p.id = b.publisher.id " +
           "JOIN b.authors ba " +
           "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%',:publisherName,'%')) " + 
           "AND LOWER(b.title) LIKE LOWER(CONCAT('%',:bookTitle,'%')) " +
           "AND LOWER(ba.name) LIKE LOWER(CONCAT('%',:authorName,'%'))")
    public Page<BookQueryDTO> findBookList(String bookTitle, String publisherName, String authorName, Pageable pageable);
    
    /*
    public List<Book> findAll();

    public void save(Book book);

    public void update(Book book);

    public void delete(Long bookId);
    */
}
