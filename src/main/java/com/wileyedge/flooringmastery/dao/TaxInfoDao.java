package com.wileyedge.flooringmastery.dao;

import com.wileyedge.flooringmastery.model.TaxInfo;

import java.util.Collection;

public interface TaxInfoDao {
    public TaxInfo getTaxInfo(String taxState) throws PersistenceException;
    public Collection<TaxInfo> getAllTaxInfo() throws PersistenceException;
}
