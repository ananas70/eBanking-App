package org.poo.cb;

import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.Map;

public interface CsvParser<T> {
    Map<String, Map<T, Double>> parseCsv(String filePath) throws IOException, CsvException;
}
