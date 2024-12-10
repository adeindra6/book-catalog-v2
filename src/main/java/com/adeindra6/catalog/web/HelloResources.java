package com.adeindra6.catalog.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adeindra6.catalog.dto.HelloMessageResponseDTO;
import com.adeindra6.catalog.service.GreetingService;

@RestController
public class HelloResources {
    Logger log = LoggerFactory.getLogger(HelloResources.class);

    private GreetingService greetingService;

    public HelloResources(GreetingService greetingService) {
        super();
        this.greetingService = greetingService;
    }

    @GetMapping("/hello")
    public ResponseEntity<HelloMessageResponseDTO> helloWorld() {
        log.info("this is log info");
        log.warn("this is log warn");
        log.debug("this is log debug");
        log.error("this is log error");
        log.trace("this is log trace");

        HelloMessageResponseDTO dto = new HelloMessageResponseDTO();
        dto.setMessage(greetingService.sayGreeting());

        return ResponseEntity.accepted().body(dto);
    }
}
