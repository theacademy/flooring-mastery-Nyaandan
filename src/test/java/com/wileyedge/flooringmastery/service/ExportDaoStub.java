package com.wileyedge.flooringmastery.service;

import com.wileyedge.flooringmastery.dao.ExportDao;

public class ExportDaoStub implements ExportDao {
    @Override
    public String exportActiveData() {
        return "Exported active data.";
    }

    @Override
    public String exportAllData() {
        return "Exported all data.";
    }
}
