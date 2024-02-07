package org.poo.cb;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExchangeRatesParser implements CsvParser<String>{

    //Parser for the Exchange Rates; the result will be atributed to the static exchangeRates of the BankDatabase instance
    @Override
    public Map<String, Map<String, Double>> parseCsv(String filePath) throws IOException, CsvException {
        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            Map<String, Map<String, Double>> exchangeRates = new HashMap<>();
            String[] currencies = csvReader.readNext();
            String[] row;

            while ((row = csvReader.readNext()) != null) {
                //Base,EUR,GBP,JPY,CAD,USD
                String baseCurrency = row[0];
                //baseCurrency is EUR from the line below
                //EUR,1.0,0.88,129.7,1.47,1.59
                Map<String,Double> rates = new HashMap<>();
                //rates are 1.0,0.88,129.7,1.47,1.59
                for (int i = 1; i < row.length; i++)
                    rates.put(currencies[i],Double.parseDouble(row[i]));
                exchangeRates.put(baseCurrency,rates);
            }
            return exchangeRates;
        }
    }
}
