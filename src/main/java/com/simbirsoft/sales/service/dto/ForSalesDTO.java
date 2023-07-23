package com.simbirsoft.sales.service.dto;

import com.simbirsoft.sales.domain.enumeration.Stack;
import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class ForSalesDTO implements Serializable {

    private String firstName;
    private String lastName;
    private Stack stack;
    private Integer targetRate;
    private Integer targetRate3Mounts;
    private String urlCV;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Stack getStack() {
        return stack;
    }

    public void setStack(Stack stack) {
        this.stack = stack;
    }

    public Integer getTargetRate() {
        return targetRate;
    }

    public void setTargetRate(Integer targetRate) {
        this.targetRate = targetRate;
    }

    public Integer getTargetRate3Mounts() {
        return targetRate3Mounts;
    }

    public void setTargetRate3Mounts(Integer targetRate3Mounts) {
        this.targetRate3Mounts = targetRate3Mounts;
    }

    public String getUrlCV() {
        return urlCV;
    }

    public void setUrlCV(String urlCV) {
        this.urlCV = urlCV;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForSalesDTO that = (ForSalesDTO) o;
        return (
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            stack == that.stack &&
            Objects.equals(targetRate, that.targetRate) &&
            Objects.equals(targetRate3Mounts, that.targetRate3Mounts) &&
            Objects.equals(urlCV, that.urlCV)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, stack, targetRate, targetRate3Mounts, urlCV);
    }

    @Override
    public String toString() {
        return (
            "ForSalesDTO{" +
            "firstName='" +
            firstName +
            '\'' +
            ", lastName='" +
            lastName +
            '\'' +
            ", stack=" +
            stack +
            ", targetRate=" +
            targetRate +
            ", targetRate3Mounts=" +
            targetRate3Mounts +
            ", urlCV='" +
            urlCV +
            '\'' +
            '}'
        );
    }
}
