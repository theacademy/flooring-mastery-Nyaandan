package com.wileyedge.flooringmastery.service;

import com.wileyedge.flooringmastery.dao.PersistenceException;
import com.wileyedge.flooringmastery.dao.TaxInfoDao;
import com.wileyedge.flooringmastery.model.TaxInfo;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public class TaxInfoDaoStub implements TaxInfoDao {
    private final TaxInfo stubbedTaxInfo = new TaxInfo("SY");

    public TaxInfoDaoStub() {
        stubbedTaxInfo.setStateName("Sharlayan");
        stubbedTaxInfo.setTaxRate(new BigDecimal("2.10"));
    }

    @Override
    public TaxInfo getTaxInfo(String taxState) throws PersistenceException {
        return stubbedTaxInfo;
    }

    @Override
    public Collection<TaxInfo> getAllTaxInfo() throws PersistenceException {
        return List.of(stubbedTaxInfo);
    }
}
