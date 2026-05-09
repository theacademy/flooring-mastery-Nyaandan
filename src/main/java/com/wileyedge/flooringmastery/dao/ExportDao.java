package com.wileyedge.flooringmastery.dao;

public interface ExportDao {
    public void exportActiveData() throws PersistenceException;
    public void exportAllData() throws PersistenceException;
}
