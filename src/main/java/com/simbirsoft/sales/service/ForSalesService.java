package com.simbirsoft.sales.service;

import com.simbirsoft.sales.domain.Offer;
import com.simbirsoft.sales.repository.OfferRepository;
import com.simbirsoft.sales.service.dto.ForSalesDTO;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ForSalesService {

    public static final String URL_JIRA_SIMBIRSOFT_COM_BROWSE_BACK = "https://jira.simbirsoft.com/browse/BACK-";
    private final Logger log = LoggerFactory.getLogger(ForSalesService.class);

    private final OfferRepository offerRepository;

    public ForSalesService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Transactional(readOnly = true)
    public List<ForSalesDTO> findReadyOffers() {
        log.debug("Request to get all findReadyOffers");
        List<ForSalesDTO> result = new ArrayList<ForSalesDTO>();
        List<Offer> offerList = offerRepository.findAll();
        for (Offer offer : offerList) {
            if (null != offer.getActivityBeforeDate() && Instant.now().isBefore(offer.getActivityBeforeDate())) {
                ForSalesDTO forSalesDTO = new ForSalesDTO();
                forSalesDTO.setFirstName(offer.getEmployee().getFirstName());
                forSalesDTO.setLastName(offer.getEmployee().getLastName());
                forSalesDTO.setStack(offer.getEmployee().getStack());
                forSalesDTO.setUrlCV(offer.getUrlCV().toString());
                forSalesDTO.setTargetRate(offer.getTargetRate());
                forSalesDTO.setTargetRate3Mounts(offer.getCuretRate());
                forSalesDTO.setUrlJira(URL_JIRA_SIMBIRSOFT_COM_BROWSE_BACK.concat(offer.getEmployee().getExternalId().toString()));
                forSalesDTO.setActivityBeforeDate(offer.getActivityBeforeDate());
                result.add(forSalesDTO);
            }
        }
        return result;
    }
}
