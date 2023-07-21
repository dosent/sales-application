package com.simbirsoft.sales.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simbirsoft.sales.IntegrationTest;
import com.simbirsoft.sales.domain.Offer;
import com.simbirsoft.sales.repository.OfferRepository;
import com.simbirsoft.sales.service.dto.OfferDTO;
import com.simbirsoft.sales.service.mapper.OfferMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OfferResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OfferResourceIT {

    private static final Integer DEFAULT_CURET_RATE = 1;
    private static final Integer UPDATED_CURET_RATE = 2;

    private static final Integer DEFAULT_TARGET_RATE = 1;
    private static final Integer UPDATED_TARGET_RATE = 2;

    private static final Float DEFAULT_CURENT_RATE_PESENT = 1F;
    private static final Float UPDATED_CURENT_RATE_PESENT = 2F;

    private static final Float DEFAULT_TARGET_RATE_PESENT = 1F;
    private static final Float UPDATED_TARGET_RATE_PESENT = 2F;

    private static final Integer DEFAULT_UNBILIBLI_DAY_1 = 1;
    private static final Integer UPDATED_UNBILIBLI_DAY_1 = 2;

    private static final String DEFAULT_URL_CV = "AAAAAAAAAA";
    private static final String UPDATED_URL_CV = "BBBBBBBBBB";

    private static final Instant DEFAULT_ACTIVITY_BEFORE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTIVITY_BEFORE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/offers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private OfferMapper offerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOfferMockMvc;

    private Offer offer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offer createEntity(EntityManager em) {
        Offer offer = new Offer()
            .curetRate(DEFAULT_CURET_RATE)
            .targetRate(DEFAULT_TARGET_RATE)
            .curentRatePesent(DEFAULT_CURENT_RATE_PESENT)
            .targetRatePesent(DEFAULT_TARGET_RATE_PESENT)
            .unbilibliDay1(DEFAULT_UNBILIBLI_DAY_1)
            .urlCV(DEFAULT_URL_CV)
            .activityBeforeDate(DEFAULT_ACTIVITY_BEFORE_DATE);
        return offer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offer createUpdatedEntity(EntityManager em) {
        Offer offer = new Offer()
            .curetRate(UPDATED_CURET_RATE)
            .targetRate(UPDATED_TARGET_RATE)
            .curentRatePesent(UPDATED_CURENT_RATE_PESENT)
            .targetRatePesent(UPDATED_TARGET_RATE_PESENT)
            .unbilibliDay1(UPDATED_UNBILIBLI_DAY_1)
            .urlCV(UPDATED_URL_CV)
            .activityBeforeDate(UPDATED_ACTIVITY_BEFORE_DATE);
        return offer;
    }

    @BeforeEach
    public void initTest() {
        offer = createEntity(em);
    }

    @Test
    @Transactional
    void createOffer() throws Exception {
        int databaseSizeBeforeCreate = offerRepository.findAll().size();
        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);
        restOfferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isCreated());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeCreate + 1);
        Offer testOffer = offerList.get(offerList.size() - 1);
        assertThat(testOffer.getCuretRate()).isEqualTo(DEFAULT_CURET_RATE);
        assertThat(testOffer.getTargetRate()).isEqualTo(DEFAULT_TARGET_RATE);
        assertThat(testOffer.getCurentRatePesent()).isEqualTo(DEFAULT_CURENT_RATE_PESENT);
        assertThat(testOffer.getTargetRatePesent()).isEqualTo(DEFAULT_TARGET_RATE_PESENT);
        assertThat(testOffer.getUnbilibliDay1()).isEqualTo(DEFAULT_UNBILIBLI_DAY_1);
        assertThat(testOffer.getUrlCV()).isEqualTo(DEFAULT_URL_CV);
        assertThat(testOffer.getActivityBeforeDate()).isEqualTo(DEFAULT_ACTIVITY_BEFORE_DATE);
    }

    @Test
    @Transactional
    void createOfferWithExistingId() throws Exception {
        // Create the Offer with an existing ID
        offer.setId(1L);
        OfferDTO offerDTO = offerMapper.toDto(offer);

        int databaseSizeBeforeCreate = offerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOffers() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList
        restOfferMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offer.getId().intValue())))
            .andExpect(jsonPath("$.[*].curetRate").value(hasItem(DEFAULT_CURET_RATE)))
            .andExpect(jsonPath("$.[*].targetRate").value(hasItem(DEFAULT_TARGET_RATE)))
            .andExpect(jsonPath("$.[*].curentRatePesent").value(hasItem(DEFAULT_CURENT_RATE_PESENT.doubleValue())))
            .andExpect(jsonPath("$.[*].targetRatePesent").value(hasItem(DEFAULT_TARGET_RATE_PESENT.doubleValue())))
            .andExpect(jsonPath("$.[*].unbilibliDay1").value(hasItem(DEFAULT_UNBILIBLI_DAY_1)))
            .andExpect(jsonPath("$.[*].urlCV").value(hasItem(DEFAULT_URL_CV)))
            .andExpect(jsonPath("$.[*].activityBeforeDate").value(hasItem(DEFAULT_ACTIVITY_BEFORE_DATE.toString())));
    }

    @Test
    @Transactional
    void getOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get the offer
        restOfferMockMvc
            .perform(get(ENTITY_API_URL_ID, offer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(offer.getId().intValue()))
            .andExpect(jsonPath("$.curetRate").value(DEFAULT_CURET_RATE))
            .andExpect(jsonPath("$.targetRate").value(DEFAULT_TARGET_RATE))
            .andExpect(jsonPath("$.curentRatePesent").value(DEFAULT_CURENT_RATE_PESENT.doubleValue()))
            .andExpect(jsonPath("$.targetRatePesent").value(DEFAULT_TARGET_RATE_PESENT.doubleValue()))
            .andExpect(jsonPath("$.unbilibliDay1").value(DEFAULT_UNBILIBLI_DAY_1))
            .andExpect(jsonPath("$.urlCV").value(DEFAULT_URL_CV))
            .andExpect(jsonPath("$.activityBeforeDate").value(DEFAULT_ACTIVITY_BEFORE_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingOffer() throws Exception {
        // Get the offer
        restOfferMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        int databaseSizeBeforeUpdate = offerRepository.findAll().size();

        // Update the offer
        Offer updatedOffer = offerRepository.findById(offer.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOffer are not directly saved in db
        em.detach(updatedOffer);
        updatedOffer
            .curetRate(UPDATED_CURET_RATE)
            .targetRate(UPDATED_TARGET_RATE)
            .curentRatePesent(UPDATED_CURENT_RATE_PESENT)
            .targetRatePesent(UPDATED_TARGET_RATE_PESENT)
            .unbilibliDay1(UPDATED_UNBILIBLI_DAY_1)
            .urlCV(UPDATED_URL_CV)
            .activityBeforeDate(UPDATED_ACTIVITY_BEFORE_DATE);
        OfferDTO offerDTO = offerMapper.toDto(updatedOffer);

        restOfferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, offerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(offerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
        Offer testOffer = offerList.get(offerList.size() - 1);
        assertThat(testOffer.getCuretRate()).isEqualTo(UPDATED_CURET_RATE);
        assertThat(testOffer.getTargetRate()).isEqualTo(UPDATED_TARGET_RATE);
        assertThat(testOffer.getCurentRatePesent()).isEqualTo(UPDATED_CURENT_RATE_PESENT);
        assertThat(testOffer.getTargetRatePesent()).isEqualTo(UPDATED_TARGET_RATE_PESENT);
        assertThat(testOffer.getUnbilibliDay1()).isEqualTo(UPDATED_UNBILIBLI_DAY_1);
        assertThat(testOffer.getUrlCV()).isEqualTo(UPDATED_URL_CV);
        assertThat(testOffer.getActivityBeforeDate()).isEqualTo(UPDATED_ACTIVITY_BEFORE_DATE);
    }

    @Test
    @Transactional
    void putNonExistingOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();
        offer.setId(count.incrementAndGet());

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, offerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(offerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();
        offer.setId(count.incrementAndGet());

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(offerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();
        offer.setId(count.incrementAndGet());

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOfferWithPatch() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        int databaseSizeBeforeUpdate = offerRepository.findAll().size();

        // Update the offer using partial update
        Offer partialUpdatedOffer = new Offer();
        partialUpdatedOffer.setId(offer.getId());

        partialUpdatedOffer.curentRatePesent(UPDATED_CURENT_RATE_PESENT);

        restOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOffer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOffer))
            )
            .andExpect(status().isOk());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
        Offer testOffer = offerList.get(offerList.size() - 1);
        assertThat(testOffer.getCuretRate()).isEqualTo(DEFAULT_CURET_RATE);
        assertThat(testOffer.getTargetRate()).isEqualTo(DEFAULT_TARGET_RATE);
        assertThat(testOffer.getCurentRatePesent()).isEqualTo(UPDATED_CURENT_RATE_PESENT);
        assertThat(testOffer.getTargetRatePesent()).isEqualTo(DEFAULT_TARGET_RATE_PESENT);
        assertThat(testOffer.getUnbilibliDay1()).isEqualTo(DEFAULT_UNBILIBLI_DAY_1);
        assertThat(testOffer.getUrlCV()).isEqualTo(DEFAULT_URL_CV);
        assertThat(testOffer.getActivityBeforeDate()).isEqualTo(DEFAULT_ACTIVITY_BEFORE_DATE);
    }

    @Test
    @Transactional
    void fullUpdateOfferWithPatch() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        int databaseSizeBeforeUpdate = offerRepository.findAll().size();

        // Update the offer using partial update
        Offer partialUpdatedOffer = new Offer();
        partialUpdatedOffer.setId(offer.getId());

        partialUpdatedOffer
            .curetRate(UPDATED_CURET_RATE)
            .targetRate(UPDATED_TARGET_RATE)
            .curentRatePesent(UPDATED_CURENT_RATE_PESENT)
            .targetRatePesent(UPDATED_TARGET_RATE_PESENT)
            .unbilibliDay1(UPDATED_UNBILIBLI_DAY_1)
            .urlCV(UPDATED_URL_CV)
            .activityBeforeDate(UPDATED_ACTIVITY_BEFORE_DATE);

        restOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOffer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOffer))
            )
            .andExpect(status().isOk());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
        Offer testOffer = offerList.get(offerList.size() - 1);
        assertThat(testOffer.getCuretRate()).isEqualTo(UPDATED_CURET_RATE);
        assertThat(testOffer.getTargetRate()).isEqualTo(UPDATED_TARGET_RATE);
        assertThat(testOffer.getCurentRatePesent()).isEqualTo(UPDATED_CURENT_RATE_PESENT);
        assertThat(testOffer.getTargetRatePesent()).isEqualTo(UPDATED_TARGET_RATE_PESENT);
        assertThat(testOffer.getUnbilibliDay1()).isEqualTo(UPDATED_UNBILIBLI_DAY_1);
        assertThat(testOffer.getUrlCV()).isEqualTo(UPDATED_URL_CV);
        assertThat(testOffer.getActivityBeforeDate()).isEqualTo(UPDATED_ACTIVITY_BEFORE_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();
        offer.setId(count.incrementAndGet());

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, offerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(offerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();
        offer.setId(count.incrementAndGet());

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(offerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();
        offer.setId(count.incrementAndGet());

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        int databaseSizeBeforeDelete = offerRepository.findAll().size();

        // Delete the offer
        restOfferMockMvc
            .perform(delete(ENTITY_API_URL_ID, offer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
