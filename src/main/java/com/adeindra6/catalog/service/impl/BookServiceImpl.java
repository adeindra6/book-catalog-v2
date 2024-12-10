package com.adeindra6.catalog.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.adeindra6.catalog.domain.Author;
import com.adeindra6.catalog.domain.Book;
import com.adeindra6.catalog.domain.Category;
import com.adeindra6.catalog.domain.Publisher;
import com.adeindra6.catalog.dto.BookCreateRequestDTO;
import com.adeindra6.catalog.dto.BookDetailResponseDTO;
import com.adeindra6.catalog.dto.BookListResponseDTO;
import com.adeindra6.catalog.dto.BookQueryDTO;
import com.adeindra6.catalog.dto.BookUpdateRequestDTO;
import com.adeindra6.catalog.dto.ResultPageResponseDTO;
import com.adeindra6.catalog.exception.BadRequestException;
import com.adeindra6.catalog.repository.BookRepository;
import com.adeindra6.catalog.service.AuthorService;
import com.adeindra6.catalog.service.BookService;
import com.adeindra6.catalog.service.CategoryService;
import com.adeindra6.catalog.service.PublisherService;
import com.adeindra6.catalog.util.PaginationUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service("bookService")
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    private final AuthorService authorService;

    private final CategoryService categoryService;

    private final PublisherService publisherService;

    @Override
    public BookDetailResponseDTO findBookDetailById(String bookId) {
        log.info("=== start get data book ===");

        Book book = bookRepository.findBySecureId(bookId)
            .orElseThrow(() -> new BadRequestException("book_id invalid"));
        BookDetailResponseDTO dto = new BookDetailResponseDTO();

        log.info("=== finish get data book ===");

        dto.setBookId(book.getSecureId());

        log.info("=== start get data category ===");
        dto.setCategories(categoryService.constructDTO(book.getCategories()));
        log.info("=== finish get data category ===");

        log.info("=== start get data author ===");
        dto.setAuthors(authorService.constructDTO(book.getAuthors()));
        log.info("=== finish get data author ===");

        log.info("=== start get data publisher ===");
        dto.setPublisher(publisherService.constructDTO(book.getPublisher()));
        log.info("=== finish get data publisher ===");

        dto.setBookTitle(book.getTitle());
        dto.setBookDescription(book.getDescription());

        return dto;
    }

    @Override
    public List<BookDetailResponseDTO> findBookListDetail() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map((b) -> {
            BookDetailResponseDTO dto = new BookDetailResponseDTO();
            // dto.setAuthorName(b.getAuthor().getName());
            dto.setBookDescription(b.getDescription());
            // dto.setBookId(b.getId());
            dto.setBookTitle(b.getTitle());

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void createNewBook(BookCreateRequestDTO dto) {
        List<Author> authors = authorService.findAuthors(dto.getAuthorIdList());
        List<Category> categories = categoryService.findCategories(dto.getCategoryList());
        Publisher publisher = publisherService.findPublisher(dto.getPublisherId());

        Book book = new Book();
        book.setTitle(dto.getBookTitle());
        book.setAuthors(authors);
        book.setCategories(categories);
        book.setPublisher(publisher);
        book.setDescription(dto.getDescription());

        bookRepository.save(book);
    }

    @Override
    public void updateBook(Long bookId, BookUpdateRequestDTO dto) {
        // Get Book from Repository
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new BadRequestException("book_id invalid"));

        // Update
        book.setTitle(dto.getBookTitle());
        book.setDescription(dto.getDescription());

        // Save
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public ResultPageResponseDTO<BookListResponseDTO> findBookList(Integer page, Integer limit, String sortBy,
            String direction, String publisherName, String bookTitle, String authorName) {
        Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(direction), sortBy));
        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<BookQueryDTO> pageResult = bookRepository.findBookList(bookTitle, publisherName, authorName, pageable);
        List<Long> idList = pageResult.stream().map(b -> b.getId()).collect(Collectors.toList());

        Map<Long, List<String>> categoryMap = categoryService.findCategoriesMap(idList);
        Map<Long, List<String>> authorMap = authorService.findAuthorMaps(idList);

        List<BookListResponseDTO> dtos = pageResult.stream().map((b) -> {
            BookListResponseDTO dto = new BookListResponseDTO();
            dto.setId(b.getBookId());
            dto.setAuthorNames(authorMap.get(b.getId()));
            dto.setCategoryCodes(categoryMap.get(b.getId()));
            dto.setTitle(b.getBookTitle());
            dto.setPublisherName(b.getPublisherName());
            dto.setDescription(b.getDescription());

            return dto;
        }).collect(Collectors.toList());

        return PaginationUtil.createResultPageDTO(dtos, pageResult.getTotalElements(), pageResult.getTotalPages());
    }
}
