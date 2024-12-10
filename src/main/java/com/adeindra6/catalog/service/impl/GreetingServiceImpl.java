package com.adeindra6.catalog.service.impl;

import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.adeindra6.catalog.config.ApplicationProperties;
import com.adeindra6.catalog.config.CloudProperties;
import com.adeindra6.catalog.service.GreetingService;

@Service
public class GreetingServiceImpl implements GreetingService {
    Logger log = LoggerFactory.getLogger(GreetingServiceImpl.class);

    private ApplicationProperties applicationProperties;
    private CloudProperties cloudProperties;

    public GreetingServiceImpl(ApplicationProperties applicationProperties, CloudProperties cloudProperties) {
        super();
        this.applicationProperties = applicationProperties;
        this.cloudProperties = cloudProperties;
    }

    @Override
    public String sayGreeting() {
        log.info("ini log info");
        System.out.println(cloudProperties.getApiKey());
        TimeZone timezone = TimeZone.getTimeZone(applicationProperties.getTimezone());
        return applicationProperties.getWelcomeText() + 
            ", our timezone is: " + timezone.getDisplayName() + 
            ", our currency is: " + applicationProperties.getCurrency();
    }
    
}
