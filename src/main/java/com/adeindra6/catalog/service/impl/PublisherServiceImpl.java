package com.adeindra6.catalog.service.impl;

import com.adeindra6.catalog.exception.BadRequestException;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.adeindra6.catalog.domain.Publisher;
import com.adeindra6.catalog.dto.PublisherCreateRequestDTO;
import com.adeindra6.catalog.dto.PublisherListResponseDTO;
import com.adeindra6.catalog.dto.PublisherResponseDTO;
import com.adeindra6.catalog.dto.PublisherUpdateRequestDTO;
import com.adeindra6.catalog.dto.ResultPageResponseDTO;
import com.adeindra6.catalog.repository.PublisherRepository;
import com.adeindra6.catalog.service.PublisherService;
import com.adeindra6.catalog.util.PaginationUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    @Override
    public void createPublisher(PublisherCreateRequestDTO dto) {
        Publisher publisher = new Publisher();
        publisher.setName(dto.getPublisherName());
        publisher.setCompanyName(dto.getCompanyName());
        publisher.setAddress(dto.getAddress());

        publisherRepository.save(publisher);
    }

    @Override
    public void updatePublisher(String publisherId, PublisherUpdateRequestDTO dto) {
        Publisher publisher = publisherRepository.findBySecureId(publisherId)
            .orElseThrow(() -> new BadRequestException("Invalid Publisher ID"));

        publisher.setName(dto.getPublisherName() == null || dto.getPublisherName().isBlank() ? publisher.getName() : dto.getPublisherName());
        publisher.setCompanyName(dto.getCompanyName() == null || dto.getCompanyName().isBlank() ? publisher.getCompanyName() : dto.getCompanyName());
        publisher.setAddress(dto.getAddress());

        publisherRepository.save(publisher);
    }

    @Override
    public ResultPageResponseDTO<PublisherListResponseDTO> findPublisherList(Integer pages, Integer limit,
            String sortBy, String direction, String publisherName) {
        
        publisherName = StringUtils.isBlank(publisherName) ? "%" : publisherName + "%";
        Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(direction), sortBy));
        Pageable pageable = PageRequest.of(pages, limit, sort);
        Page<Publisher> pageResult = publisherRepository.findByNameLikeIgnoreCase(publisherName, pageable);
        List<PublisherListResponseDTO> dtos = pageResult.stream().map((p) -> {
            PublisherListResponseDTO dto = new PublisherListResponseDTO();
            dto.setPublisherId(p.getSecureId());
            dto.setPublisherName(p.getName());
            dto.setCompanyName(p.getCompanyName());

            return dto;
        }).collect(Collectors.toList());
        
        return PaginationUtil.createResultPageDTO(dtos, pageResult.getTotalElements(), pageResult.getTotalPages());
    }

    @Override
    public Publisher findPublisher(String publisherId) {
        Publisher publisher = publisherRepository.findBySecureId(publisherId)
            .orElseThrow(() -> new BadRequestException("Invalid Publisher ID"));

        return publisher;
    }

    @Override
    public PublisherResponseDTO constructDTO(Publisher publisher) {
        PublisherResponseDTO dto = new PublisherResponseDTO();
        dto.setPublisherId(publisher.getSecureId());
        dto.setPublisherName(publisher.getName());

        return dto;
    }
}
