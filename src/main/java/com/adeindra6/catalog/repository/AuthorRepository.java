package com.adeindra6.catalog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.adeindra6.catalog.domain.Author;
import com.adeindra6.catalog.dto.AuthorQueryDTO;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    public Optional<Author> findById(Long id);

    public List<Author> findBySecureIdIn(List<String> authorIdList);

    public Optional<Author> findBySecureId(String id);

    public Optional<Author> findByIdAndDeletedFalse(Long id);

    public List<Author> findByNameLike(String authorName);

    @Query("SELECT new com.adeindra6.catalog.dto.AuthorQueryDTO(b.id, ba.name) " +
           "FROM Book b " +
           "JOIN b.authors ba " +
           "WHERE b.id IN :bookIdList")
    public List<AuthorQueryDTO> findAuthorsByBookIdList(List<Long> bookIdList);
}
