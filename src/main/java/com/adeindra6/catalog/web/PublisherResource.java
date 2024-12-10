package com.adeindra6.catalog.web;

import java.net.URI;

import com.adeindra6.catalog.exception.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adeindra6.catalog.annotation.LogThisMethod;
import com.adeindra6.catalog.dto.PublisherCreateRequestDTO;
import com.adeindra6.catalog.dto.PublisherListResponseDTO;
import com.adeindra6.catalog.dto.PublisherUpdateRequestDTO;
import com.adeindra6.catalog.dto.ResultPageResponseDTO;
import com.adeindra6.catalog.service.PublisherService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

@Validated
@AllArgsConstructor
@RestController
public class PublisherResource {
    private final PublisherService publisherService;

    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/v1/publisher")
    public ResponseEntity<Void> createNewPublisher(@RequestBody @Valid PublisherCreateRequestDTO dto) {
        publisherService.createPublisher(dto);
        return ResponseEntity.created(URI.create("/v1/publisher")).build();
    }

    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/v1/publisher/{publisherId}")
    public ResponseEntity<Void> updatePublisher(@PathVariable @Size(max = 36, min = 36, message = "publisher.id.is.not.uuid") String publisherId, @RequestBody @Valid PublisherUpdateRequestDTO dto) {
        publisherService.updatePublisher(publisherId, dto);
        return ResponseEntity.ok().build();
    }

    // @PreAuthorize("isAuthenticated()")
    @LogThisMethod
    @GetMapping("/v1/publisher")
    public ResponseEntity<ResultPageResponseDTO<PublisherListResponseDTO>> findPublisherList(
        @RequestParam(name = "pages", required = true, defaultValue = "0") Integer pages,
        @RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit,
        @RequestParam(name = "sortBy", required = true, defaultValue = "name") String sortBy,
        @RequestParam(name = "direction", required = true, defaultValue = "asc") String direction,
        @RequestParam(name = "publisherName", required = false) String publisherName) {
        if(pages < 0) {
            throw new BadRequestException("Invalid page");
        }

        return ResponseEntity.ok().body(publisherService.findPublisherList(pages, limit, sortBy, direction, publisherName));
    }
}
