package com.woxsen.studentinitiatives.config;

import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@Configuration
public class JacksonConfiguration {
    public JacksonConfiguration(ObjectMapper objectMapper) { 
        objectMapper.setFilterProvider(new SimpleFilterProvider().setFailOnUnknownId(false)); 
    } 
}
