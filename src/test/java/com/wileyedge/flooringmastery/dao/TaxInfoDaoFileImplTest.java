package com.wileyedge.flooringmastery.dao;

import com.wileyedge.flooringmastery.model.TaxInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.util.Collection;

public class TaxInfoDaoFileImplTest {
    TaxInfoDaoFileImpl testTaxDao;

    @BeforeEach
    void setUp() throws PersistenceException {
        String testfile = "testdata/Data/Taxes.txt";
        testTaxDao = new TaxInfoDaoFileImpl(testfile);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getTaxInfo() {
        TaxInfo wa = testTaxDao.getTaxInfo("WA");
        TaxInfo ky = testTaxDao.getTaxInfo("KY");
        Assertions.assertEquals(new BigDecimal("9.25"), wa.getTaxRate());
        Assertions.assertEquals(new BigDecimal("6.00"), ky.getTaxRate());
    }

    @Test
    void getAllTaxInfo() {
        Collection<TaxInfo> taxInfo = testTaxDao.getAllTaxInfo();
        TaxInfo cali = new TaxInfo("CA");
        cali.setStateName("California");
        cali.setTaxRate(new BigDecimal("25.00"));
        Assertions.assertEquals(4, taxInfo.size());
        Assertions.assertTrue(taxInfo.contains(cali));
    }
}
