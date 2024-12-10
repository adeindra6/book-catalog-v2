package com.adeindra6.catalog.service;

import com.adeindra6.catalog.dto.AuthorResponseDTO;

import java.util.List;
import java.util.Map;

import com.adeindra6.catalog.domain.Author;
import com.adeindra6.catalog.dto.AuthorCreateRequestDTO;
import com.adeindra6.catalog.dto.AuthorUpdateRequestDTO;

public interface AuthorService {
    public AuthorResponseDTO findAuthorById(String id);

    public void createNewAuthor(List<AuthorCreateRequestDTO> dto);

    public void updateAuthor(String authorId, AuthorUpdateRequestDTO dto);

    public void deleteAuthor(String authorId);

    public List<Author> findAuthors(List<String> authorIdList);

    public List<AuthorResponseDTO> constructDTO(List<Author> authors);

    public Map<Long, List<String>> findAuthorMaps(List<Long> bookIdList);
}
