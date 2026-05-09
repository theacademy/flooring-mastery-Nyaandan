package com.wileyedge.flooringmastery.model;

import java.math.BigDecimal;
import java.util.Objects;

public class TaxInfo {
    private final String stateCode;
    private String stateName;
    private BigDecimal taxRate;


    public TaxInfo(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !getClass().equals(obj.getClass())) {
            return false;
        }
        TaxInfo info = (TaxInfo) obj;
        return stateCode.equals(info.stateCode)
                && taxRate.equals(info.taxRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stateCode, taxRate);
    }
}
