package com.simbirsoft.sales.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simbirsoft.sales.domain.enumeration.Stack;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "location")
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "stack")
    private Stack stack;

    @Column(name = "external_id")
    private Integer externalId;

    @Column(name = "salary")
    private Integer salary;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    @JsonIgnoreProperties(value = { "employee" }, allowSetters = true)
    private Set<Offer> offers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Employee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Employee firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Employee lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLocation() {
        return this.location;
    }

    public Employee location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Stack getStack() {
        return this.stack;
    }

    public Employee stack(Stack stack) {
        this.setStack(stack);
        return this;
    }

    public void setStack(Stack stack) {
        this.stack = stack;
    }

    public Integer getExternalId() {
        return this.externalId;
    }

    public Employee externalId(Integer externalId) {
        this.setExternalId(externalId);
        return this;
    }

    public void setExternalId(Integer externalId) {
        this.externalId = externalId;
    }

    public Integer getSalary() {
        return this.salary;
    }

    public Employee salary(Integer salary) {
        this.setSalary(salary);
        return this;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Set<Offer> getOffers() {
        return this.offers;
    }

    public void setOffers(Set<Offer> offers) {
        if (this.offers != null) {
            this.offers.forEach(i -> i.setEmployee(null));
        }
        if (offers != null) {
            offers.forEach(i -> i.setEmployee(this));
        }
        this.offers = offers;
    }

    public Employee offers(Set<Offer> offers) {
        this.setOffers(offers);
        return this;
    }

    public Employee addOffer(Offer offer) {
        this.offers.add(offer);
        offer.setEmployee(this);
        return this;
    }

    public Employee removeOffer(Offer offer) {
        this.offers.remove(offer);
        offer.setEmployee(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", location='" + getLocation() + "'" +
            ", stack='" + getStack() + "'" +
            ", externalId=" + getExternalId() +
            ", salary=" + getSalary() +
            "}";
    }
}
