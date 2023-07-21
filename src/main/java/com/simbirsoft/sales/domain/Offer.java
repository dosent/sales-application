package com.simbirsoft.sales.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Offer.
 */
@Entity
@Table(name = "offer")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Offer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "curet_rate")
    private Integer curetRate;

    @Column(name = "target_rate")
    private Integer targetRate;

    @Column(name = "curent_rate_pesent")
    private Float curentRatePesent;

    @Column(name = "target_rate_pesent")
    private Float targetRatePesent;

    @Column(name = "unbilibli_day_1")
    private Integer unbilibliDay1;

    @Column(name = "url_cv")
    private String urlCV;

    @Column(name = "activity_before_date")
    private Instant activityBeforeDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "offers" }, allowSetters = true)
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Offer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCuretRate() {
        return this.curetRate;
    }

    public Offer curetRate(Integer curetRate) {
        this.setCuretRate(curetRate);
        return this;
    }

    public void setCuretRate(Integer curetRate) {
        this.curetRate = curetRate;
    }

    public Integer getTargetRate() {
        return this.targetRate;
    }

    public Offer targetRate(Integer targetRate) {
        this.setTargetRate(targetRate);
        return this;
    }

    public void setTargetRate(Integer targetRate) {
        this.targetRate = targetRate;
    }

    public Float getCurentRatePesent() {
        return this.curentRatePesent;
    }

    public Offer curentRatePesent(Float curentRatePesent) {
        this.setCurentRatePesent(curentRatePesent);
        return this;
    }

    public void setCurentRatePesent(Float curentRatePesent) {
        this.curentRatePesent = curentRatePesent;
    }

    public Float getTargetRatePesent() {
        return this.targetRatePesent;
    }

    public Offer targetRatePesent(Float targetRatePesent) {
        this.setTargetRatePesent(targetRatePesent);
        return this;
    }

    public void setTargetRatePesent(Float targetRatePesent) {
        this.targetRatePesent = targetRatePesent;
    }

    public Integer getUnbilibliDay1() {
        return this.unbilibliDay1;
    }

    public Offer unbilibliDay1(Integer unbilibliDay1) {
        this.setUnbilibliDay1(unbilibliDay1);
        return this;
    }

    public void setUnbilibliDay1(Integer unbilibliDay1) {
        this.unbilibliDay1 = unbilibliDay1;
    }

    public String getUrlCV() {
        return this.urlCV;
    }

    public Offer urlCV(String urlCV) {
        this.setUrlCV(urlCV);
        return this;
    }

    public void setUrlCV(String urlCV) {
        this.urlCV = urlCV;
    }

    public Instant getActivityBeforeDate() {
        return this.activityBeforeDate;
    }

    public Offer activityBeforeDate(Instant activityBeforeDate) {
        this.setActivityBeforeDate(activityBeforeDate);
        return this;
    }

    public void setActivityBeforeDate(Instant activityBeforeDate) {
        this.activityBeforeDate = activityBeforeDate;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Offer employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Offer)) {
            return false;
        }
        return id != null && id.equals(((Offer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Offer{" +
            "id=" + getId() +
            ", curetRate=" + getCuretRate() +
            ", targetRate=" + getTargetRate() +
            ", curentRatePesent=" + getCurentRatePesent() +
            ", targetRatePesent=" + getTargetRatePesent() +
            ", unbilibliDay1=" + getUnbilibliDay1() +
            ", urlCV='" + getUrlCV() + "'" +
            ", activityBeforeDate='" + getActivityBeforeDate() + "'" +
            "}";
    }
}
