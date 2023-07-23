package com.simbirsoft.sales.web.rest;

import com.simbirsoft.sales.service.ForSalesService;
import com.simbirsoft.sales.service.dto.ForSalesDTO;
import java.util.List;
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

    private final ForSalesService forSalesService;

    public ForSalesResource(ForSalesService forSalesService) {
        this.forSalesService = forSalesService;
    }

    @GetMapping("/forsales")
    public List<ForSalesDTO> getForSales() {
        log.debug("REST request to get all ForSales");
        return forSalesService.findReadyOffers();
    }
}
