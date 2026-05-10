package com.wileyedge.flooringmastery.dao;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.stream.Stream;

public class ExportDaoFileImpl implements ExportDao {
    private final String ORDERS_LOCATION = "data/Orders";
    private final String BACKUP_LOCATION = "data/Backup/";

    @Override
    public String exportActiveData() throws PersistenceException {
        processOrders(false);
        return BACKUP_LOCATION + "DataExport.txt";
    }

    @Override
    public String exportAllData() throws PersistenceException {
        processOrders(true);
        return BACKUP_LOCATION + "DataSnapshot_"
                + LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))
                + ".txt";
    }

    private LocalDate getFileDate(Path path, DateTimeFormatter formatter) {
        String filename = path.getFileName().toString();
        String datePart = filename.substring(7, 15);
        return LocalDate.parse(datePart, formatter);
    }

    public void processOrders(boolean fullExport) throws PersistenceException {
        Path dir = Paths.get(ORDERS_LOCATION);

        DateTimeFormatter filenameFormatter = DateTimeFormatter.ofPattern("MMddyyyy");
        DateTimeFormatter ioFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        String filename = fullExport ?
                BACKUP_LOCATION + "DataSnapshot_" + LocalDate.now().format(filenameFormatter) + ".txt"
                : BACKUP_LOCATION + "DataExport.txt";

        String headers = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,"
                + "CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total,Date";

        try (Stream<Path> stream = Files.list(dir);
             PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            out.println(headers);

            stream.filter(Files::isRegularFile)
                    .filter(path -> fullExport
                            || !getFileDate(path, filenameFormatter).isBefore(LocalDate.now()))
                    .sorted(Comparator.comparing(path -> getFileDate(path, filenameFormatter)))
                    .forEachOrdered(path -> {
                        try (BufferedReader br = new BufferedReader(new FileReader(path.toString()))) {
                            String date = getFileDate(path, filenameFormatter).format(ioFormatter);
                            br.lines().skip(1).forEach(
                                    line -> out.println(line + "," + date));
                        } catch (IOException ignored) {
                        }
                    });

        } catch (IOException e) {
            throw new PersistenceException("Error processing data.");
        }
    }

}
