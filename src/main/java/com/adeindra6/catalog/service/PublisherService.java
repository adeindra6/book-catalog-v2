package com.adeindra6.catalog.service;

import com.adeindra6.catalog.domain.Publisher;
import com.adeindra6.catalog.dto.PublisherCreateRequestDTO;
import com.adeindra6.catalog.dto.PublisherListResponseDTO;
import com.adeindra6.catalog.dto.PublisherResponseDTO;
import com.adeindra6.catalog.dto.PublisherUpdateRequestDTO;
import com.adeindra6.catalog.dto.ResultPageResponseDTO;

public interface PublisherService {
    public void createPublisher(PublisherCreateRequestDTO dto);

    public Publisher findPublisher(String publisherId);

    public void updatePublisher(String publisherId, PublisherUpdateRequestDTO dto);

    public ResultPageResponseDTO<PublisherListResponseDTO> findPublisherList(Integer pages, Integer limit, String sortBy, String direction, String publisherName);

    public PublisherResponseDTO constructDTO(Publisher publisher);
}
