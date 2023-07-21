package com.simbirsoft.sales.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.simbirsoft.sales.domain.Offer} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OfferDTO implements Serializable {

    private Long id;

    private Integer curetRate;

    private Integer targetRate;

    private Float curentRatePesent;

    private Float targetRatePesent;

    private Integer unbilibliDay1;

    private String urlCV;

    private Instant activityBeforeDate;

    private EmployeeDTO employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCuretRate() {
        return curetRate;
    }

    public void setCuretRate(Integer curetRate) {
        this.curetRate = curetRate;
    }

    public Integer getTargetRate() {
        return targetRate;
    }

    public void setTargetRate(Integer targetRate) {
        this.targetRate = targetRate;
    }

    public Float getCurentRatePesent() {
        return curentRatePesent;
    }

    public void setCurentRatePesent(Float curentRatePesent) {
        this.curentRatePesent = curentRatePesent;
    }

    public Float getTargetRatePesent() {
        return targetRatePesent;
    }

    public void setTargetRatePesent(Float targetRatePesent) {
        this.targetRatePesent = targetRatePesent;
    }

    public Integer getUnbilibliDay1() {
        return unbilibliDay1;
    }

    public void setUnbilibliDay1(Integer unbilibliDay1) {
        this.unbilibliDay1 = unbilibliDay1;
    }

    public String getUrlCV() {
        return urlCV;
    }

    public void setUrlCV(String urlCV) {
        this.urlCV = urlCV;
    }

    public Instant getActivityBeforeDate() {
        return activityBeforeDate;
    }

    public void setActivityBeforeDate(Instant activityBeforeDate) {
        this.activityBeforeDate = activityBeforeDate;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OfferDTO)) {
            return false;
        }

        OfferDTO offerDTO = (OfferDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, offerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OfferDTO{" +
            "id=" + getId() +
            ", curetRate=" + getCuretRate() +
            ", targetRate=" + getTargetRate() +
            ", curentRatePesent=" + getCurentRatePesent() +
            ", targetRatePesent=" + getTargetRatePesent() +
            ", unbilibliDay1=" + getUnbilibliDay1() +
            ", urlCV='" + getUrlCV() + "'" +
            ", activityBeforeDate='" + getActivityBeforeDate() + "'" +
            ", employee=" + getEmployee() +
            "}";
    }
}
