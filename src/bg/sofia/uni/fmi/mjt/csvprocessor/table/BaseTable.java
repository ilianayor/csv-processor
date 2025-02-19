package bg.sofia.uni.fmi.mjt.csvprocessor.table;

import bg.sofia.uni.fmi.mjt.csvprocessor.exceptions.CsvDataNotCorrectException;
import bg.sofia.uni.fmi.mjt.csvprocessor.table.column.BaseColumn;
import bg.sofia.uni.fmi.mjt.csvprocessor.table.column.Column;
import bg.sofia.uni.fmi.mjt.csvprocessor.validator.Validator;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class BaseTable implements Table {
    private final Map<String, Column> tableData;

    public BaseTable() {
        this.tableData = new LinkedHashMap<>();
    }

    @Override
    public void addData(String[] data) throws CsvDataNotCorrectException {
        Validator.validateNotNull(data, "data");

        if (data.length == 0) {
            throw new IllegalArgumentException("Data cannot be empty.");
        }

        if (tableData.isEmpty()) {
            addTableHeader(data);
            return;
        }

        if (data.length != tableData.size()) {
            throw new CsvDataNotCorrectException(
                "The count of the provided data parts is not equal to the number of columns in the table.");
        }

        addTableRow(data);
    }

    @Override
    public Collection<String> getColumnNames() {
        return Collections.unmodifiableSet(tableData.keySet());
    }

    @Override
    public Collection<String> getColumnData(String column) {
        Validator.validateNotNull(column, "column");
        Validator.validateNotBlank(column, "column");

        if (!tableData.containsKey(column)) {
            throw new IllegalArgumentException("There is no column with such name in the table.");
        }

        return Collections.unmodifiableCollection(tableData.get(column).getData());
    }

    @Override
    public int getRowsCount() {
        return tableData.isEmpty() ? 0 : tableData.values().iterator().next().getData().size();
    }

    private void addTableHeader(String[] data) {
        for (String header : data) {
            tableData.putIfAbsent(header, new BaseColumn());
        }
    }

    private void addTableRow(String[] data) {
        int index = 0;

        for (String col : this.tableData.keySet()) {
            if (index < data.length) {
                Column column = tableData.get(col);
                column.addData(data[index]);
            }

            index++;
        }
    }

    @Override
    public String toString() {
        return "BaseTable{" +
            "tableData=" + tableData +
            '}';
    }
}
