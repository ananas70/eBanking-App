package org.poo.cb;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public class StockValuesParser implements CsvParser<ZonedDateTime> {
    //Parser for the StockValues; the result will be atributed to the static stockValues of the BankDatabase instance

    @Override
    public Map<String, Map<ZonedDateTime, Double>> parseCsv(String filePath) throws IOException, CsvException {
        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))){
            Map<String, Map<ZonedDateTime, Double>> stockValues = new HashMap<>();
            String[] stockDates = csvReader.readNext();
            String[] row;
            while ((row = csvReader.readNext()) != null) {
                //AAPL,193.5800018310547,192.52999877929688,185.63999938964844, ...
                String stockName = row[0];  //AAPL

                Map<ZonedDateTime,Double> values = new HashMap<>();
                for (int i = 1; i < row.length; i++) {
                    ZonedDateTime time = DateParser.parseDate(stockDates[i]);
                    values.put(time, Double.parseDouble(row[i]));
                }
                stockValues.put(stockName,values);
            }
            return stockValues;
        }
    }
}
