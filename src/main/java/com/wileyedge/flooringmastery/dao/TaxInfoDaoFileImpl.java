package com.wileyedge.flooringmastery.dao;

import com.wileyedge.flooringmastery.model.TaxInfo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

public class TaxInfoDaoFileImpl implements TaxInfoDao {
    private final String TAX_INFO_FILE = "data/Data/Taxes.txt";
    private final HashMap<String, TaxInfo> taxInfoMap = new HashMap<>();

    public TaxInfoDaoFileImpl() throws PersistenceException {
        readData();
    }

    private void readData() throws PersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(new FileReader(TAX_INFO_FILE)));
        } catch (FileNotFoundException ex) {
            throw new PersistenceException(
                    "Could not load taxInfo data...");
        }

        String currentLine;
        TaxInfo currentTaxInfo;
        scanner.nextLine(); // Skip the headers
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTaxInfo = unmarshallData(currentLine);
            taxInfoMap.put(currentTaxInfo.getStateCode(), currentTaxInfo);
        }
        scanner.close();
    }

    private TaxInfo unmarshallData(String line) {
        String[] data = line.split(",");
        TaxInfo taxInfo = new TaxInfo(data[0]);
        taxInfo.setStateName(data[1]);
        taxInfo.setTaxRate(new BigDecimal(data[2]));
        return taxInfo;
    }
    @Override
    public TaxInfo getTaxInfo(String taxState) throws PersistenceException {
        return taxInfoMap.get(taxState);
    }

    @Override
    public Collection<TaxInfo> getAllTaxInfo() throws PersistenceException {
        return taxInfoMap.values();
    }
}
