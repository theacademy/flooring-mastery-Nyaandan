package com.wileyedge.flooringmastery.service;

import com.wileyedge.flooringmastery.dao.ExportDao;

public class ExportDaoStub implements ExportDao {
    @Override
    public String exportActiveData() {
        return "";
    }

    @Override
    public String exportAllData() {
        return "";
    }
}
