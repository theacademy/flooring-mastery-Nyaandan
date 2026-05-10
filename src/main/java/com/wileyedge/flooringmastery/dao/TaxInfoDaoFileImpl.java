package com.wileyedge.flooringmastery.dao;

import com.wileyedge.flooringmastery.model.TaxInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;

public class TaxInfoDaoFileImpl implements TaxInfoDao {
    private String TAX_INFO_FILE = "data/Data/Taxes.txt";
    private final HashMap<String, TaxInfo> taxInfoMap = new HashMap<>();

    @SuppressWarnings({"unused"})
    public TaxInfoDaoFileImpl() throws PersistenceException {
        readData();
    }

    public TaxInfoDaoFileImpl(String TAX_INFO_FILE) throws PersistenceException {
        this.TAX_INFO_FILE = TAX_INFO_FILE;
        readData();
    }

    private void readData() throws PersistenceException {
        try (BufferedReader br = new BufferedReader(new FileReader(TAX_INFO_FILE))) {
            br.lines().skip(1).forEach(line -> {
                TaxInfo taxInfo = unmarshallData(line);
                taxInfoMap.put(taxInfo.getStateCode(), taxInfo);
            });
        } catch (IOException ex) {
            throw new PersistenceException("Could not load taxInfo data...");
        }
    }

    private TaxInfo unmarshallData(String line) {
        String[] data = line.split(",");
        TaxInfo taxInfo = new TaxInfo(data[0]);
        taxInfo.setStateName(data[1]);
        taxInfo.setTaxRate(new BigDecimal(data[2]));
        return taxInfo;
    }
    @Override
    public TaxInfo getTaxInfo(String taxState) {
        return taxInfoMap.get(taxState);
    }

    @Override
    public Collection<TaxInfo> getAllTaxInfo() {
        return taxInfoMap.values();
    }
}
