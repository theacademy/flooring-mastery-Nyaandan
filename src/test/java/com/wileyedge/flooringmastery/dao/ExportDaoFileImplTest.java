package com.wileyedge.flooringmastery.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ExportDaoFileImplTest {
    private ExportDaoFileImpl  testExportDao;
    private final String BACKUP = "testdata/Backup/";

    @BeforeEach
    void setUp() {
        final String ORDERS = "testdata/Orders";
        testExportDao = new ExportDaoFileImpl(ORDERS, BACKUP);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void exportActiveData() throws PersistenceException {
        Path backupPath = Paths.get(BACKUP, "DataExport.txt");
        try {
            Files.deleteIfExists(backupPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        testExportDao.exportActiveData();
        Assertions.assertTrue(Files.exists(backupPath));
    }

    @Test
    void exportAllData() throws PersistenceException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        Path backupPath = Paths.get(BACKUP,
                "DataSnapshot_" + LocalDate.now().format(formatter) + ".txt");

        try {
            Files.deleteIfExists(backupPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        testExportDao.exportAllData();
        Assertions.assertTrue(Files.exists(backupPath));
    }
}
