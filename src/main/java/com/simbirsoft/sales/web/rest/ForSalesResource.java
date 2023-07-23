package com.simbirsoft.sales.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ForSalesResource {

    private final Logger log = LoggerFactory.getLogger(ForSalesResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
}
