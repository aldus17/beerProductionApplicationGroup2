package com.mycompany.data.dataAccess.Connect;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SimpleSet {

    private final ArrayList<LinkedHashMap<String, Object>> rows;

    public SimpleSet() {
        rows = new ArrayList<>();
    }

    public void add(String[] labels, Object[] values) {

        if (labels.length != values.length) {
            throw new IllegalArgumentException("Label and Value arrays were of different lengths.");
        }

        rows.add(new LinkedHashMap<>());
        for (int i = 0; i < values.length; i++) {
            rows.get(rows.size() - 1).put(labels[i].toLowerCase(), values[i]);
        }
    }

    public Object get(int row, String label) {
        return rows.get(row).get(label.toLowerCase());
    }

    public Object get(int row, int column) {
        Object[] values = rows.get(row).values().toArray();
        return values[column];
    }

    public int getRows() {
        return rows.size();
    }

    public int getColumns() {
        return rows.get(0).size();
    }

    public boolean isEmpty() {
        return rows.isEmpty();
    }
}
