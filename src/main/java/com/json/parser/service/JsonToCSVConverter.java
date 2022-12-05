package com.json.parser.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class JsonToCSVConverter {

    public static String convert(Map<String, List<String>> data, int records) {
        String csv = String.join(",", data.keySet()) +
                System.lineSeparator();
        StringBuffer csvData = new StringBuffer();
        csvData.append(csv);
        createRecords(data.values(), records).forEach(line -> csvData.append(String.join(",", line)).append(System.lineSeparator()));
        System.out.println(csvData);
        return csvData.toString();
    }

    private static List<List<String >> createRecords(Collection<List<String>> records, int rows) {
        List<List<String >> lines = new ArrayList<>();
        for(int i=0;i<rows;i++) {
            List<String> line = new ArrayList<>();
            for(List<String> record : records) {
                line.add(record.get(i));
            }
            lines.add(line);
        }
        return lines;
    }
}
