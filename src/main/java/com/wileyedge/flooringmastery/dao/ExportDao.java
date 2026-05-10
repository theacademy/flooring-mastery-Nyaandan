package com.wileyedge.flooringmastery.dao;

public interface ExportDao {
    String exportActiveData() throws PersistenceException;
    String exportAllData() throws PersistenceException;
}
