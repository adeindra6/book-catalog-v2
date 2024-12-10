package com.adeindra6.catalog.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.adeindra6.catalog.domain.Address;
import com.adeindra6.catalog.domain.Author;
import com.adeindra6.catalog.dto.AuthorResponseDTO;
import com.adeindra6.catalog.dto.AuthorUpdateRequestDTO;
import com.adeindra6.catalog.repository.AuthorRepository;
import com.adeindra6.catalog.dto.AuthorCreateRequestDTO;
import com.adeindra6.catalog.dto.AuthorQueryDTO;
import com.adeindra6.catalog.service.AuthorService;
import com.adeindra6.catalog.exception.BadRequestException;
import com.adeindra6.catalog.exception.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public AuthorResponseDTO findAuthorById(String id) {
        Author author = authorRepository.findBySecureId(id).orElseThrow(() -> new ResourceNotFoundException("Invalid AuthorID"));

        AuthorResponseDTO dto = new AuthorResponseDTO();
        dto.setAuthorName(author.getName());
        dto.setBirthDate(author.getBirthDate().toEpochDay());

        return dto;
    }

    @Override
    public void createNewAuthor(List<AuthorCreateRequestDTO> dtos) {
        List<Author> authors = dtos.stream().map((dto) -> {
            Author author = new Author();
            author.setName(dto.getAuthorName());
            author.setBirthDate(LocalDate.ofEpochDay(dto.getBirthDate()));
            List<Address> addresses = dto.getAddresses().stream().map((a) -> {
                Address address = new Address();
                address.setCityName(a.getCityName());
                address.setStreetName(a.getStreetName());
                address.setZipCode(a.getZipCode());
                address.setAuthor(author);

                return address;
            }).collect(Collectors.toList());
            author.setAddresses(addresses);

            return author;
        }).collect(Collectors.toList());

        authorRepository.saveAll(authors);
    }

    @Override
    public void updateAuthor(String authorId, AuthorUpdateRequestDTO dto) {
        Author author = authorRepository.findBySecureId(authorId).orElseThrow(() -> new BadRequestException("Invalid AuthorID"));

        Map<Long, Address> addressMap = author.getAddresses().stream().map(a -> a).collect(Collectors.toMap(Address::getId, Function.identity()));

        List<Address> addresses = dto.getAddresses().stream().map((a) -> {
            Address address = addressMap.get(a.getAddressId());
            address.setCityName(a.getCityName());
            address.setStreetName(a.getStreetName());
            address.setZipCode(a.getZipCode());

            return address;
        }).collect(Collectors.toList());
        
        author.setAddresses(addresses);
        author.setName(dto.getAuthorName() == null ? author.getName() : dto.getAuthorName());
        author.setBirthDate(dto.getBirthDate() == null ? author.getBirthDate() : LocalDate.ofEpochDay(dto.getBirthDate()));

        authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(String authorId) {
        Author author = authorRepository.findBySecureId(authorId).orElseThrow(() -> new BadRequestException("Invalid AuthorID"));

        authorRepository.delete(author);
        /*
        Author author = authorRepository.findByIdAndDeletedFalse(authorId).orElseThrow(() -> new BadRequestException("Invalid Author ID"));

        author.setDeleted(Boolean.TRUE);
        authorRepository.save(author);
        */
    }

    @Override
    public List<Author> findAuthors(List<String> authorIdList) {
        List<Author> authors = authorRepository.findBySecureIdIn(authorIdList);
        if(authors.isEmpty()) {
            throw new BadRequestException("author can't be empty");
        }

        return authors;
    }

    @Override
    public List<AuthorResponseDTO> constructDTO(List<Author> authors) {
        return authors.stream().map((a) -> {
            AuthorResponseDTO dto = new AuthorResponseDTO();
            dto.setAuthorName(a.getName());
            dto.setBirthDate(a.getBirthDate().toEpochDay());

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public Map<Long, List<String>> findAuthorMaps(List<Long> bookIdList) {
        List<AuthorQueryDTO> queryList = authorRepository.findAuthorsByBookIdList(bookIdList);
        Map<Long, List<String>> authorMap = new HashMap<>();
        List<String> authorList = null;

        for(AuthorQueryDTO q:queryList) {
            if(!authorMap.containsKey(q.getBookId())) {
                authorList = new ArrayList<>();
            }
            else {
                authorList = authorMap.get(q.getBookId());
            }

            authorList.add(q.getAuthorName());
            authorMap.put(q.getBookId(), authorList);
        }
        
        return authorMap;
    }
}
