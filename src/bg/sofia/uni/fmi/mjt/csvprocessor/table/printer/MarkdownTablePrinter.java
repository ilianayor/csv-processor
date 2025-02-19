package bg.sofia.uni.fmi.mjt.csvprocessor.table.printer;

import bg.sofia.uni.fmi.mjt.csvprocessor.table.Table;
import bg.sofia.uni.fmi.mjt.csvprocessor.validator.Validator;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MarkdownTablePrinter implements TablePrinter {
    private static final String VERTICAL_BAR = "|";
    private static final String WHITESPACE = " ";

    private static final String DASH = "-";
    private static final String COLON = ":";
    private static final int MIN_LENGTH_COLUMN = 3;

    @Override
    public Collection<String> printTable(Table table, ColumnAlignment... alignments) {
        Validator.validateNotNull(table, "table");
        Validator.validateNotNull(alignments, "alignments");

        Collection<String> header = table.getColumnNames();
        List<String> colNames = new LinkedList<>(header);
        int numColumns = header.size();

        Map<String, Integer> maxLengthOfColumns = getMaxLengthColumn(table);

        ColumnAlignment[] correctColumnAlignment = ensureCorrectAlignmentSize(numColumns, alignments);

        Collection<String> result = new LinkedList<>();
        result.add(formatHeader(colNames, maxLengthOfColumns));
        result.add(formatDelimiterRow(colNames, numColumns, maxLengthOfColumns, correctColumnAlignment));

        List<List<String>> rows = getRows(table);

        for (List<String> row : rows) {
            result.add(format(row, colNames, maxLengthOfColumns, correctColumnAlignment));
        }

        return Collections.unmodifiableCollection(result);
    }

    private ColumnAlignment[] ensureCorrectAlignmentSize(int numColumns, ColumnAlignment... alignments) {
        if (alignments.length >= numColumns) {
            return alignments;
        }

        ColumnAlignment[] realColumnAlignment = Arrays.copyOf(alignments, numColumns);
        Arrays.fill(realColumnAlignment, alignments.length, numColumns, ColumnAlignment.NOALIGNMENT);

        return realColumnAlignment;
    }

    private String format(List<String> row, List<String> headers, Map<String, Integer> maxLengthOfColumns,
                          ColumnAlignment... alignments) {
        int numColumns = headers.size();
        StringBuilder formattedRow = new StringBuilder(VERTICAL_BAR);

        for (int i = 0; i < numColumns; i++) {
            String value = row.get(i);
            int maxLength = maxLengthOfColumns.get(headers.get(i));
            formattedRow.append(WHITESPACE)
                .append(formatValue(value, maxLength, alignments[i]))
                .append(WHITESPACE)
                .append(VERTICAL_BAR);
        }

        return formattedRow.toString();
    }

    private String formatHeader(List<String> colNames, Map<String, Integer> maxLengthOfColumns) {
        int numColumns = colNames.size();
        StringBuilder formattedRow = new StringBuilder(VERTICAL_BAR);

        for (int i = 0; i < numColumns; i++) {
            String colName = colNames.get(i);
            int maxLength = maxLengthOfColumns.get(colName);
            int padding = (maxLength - colName.length());

            formattedRow.append(WHITESPACE)
                .append(colName)
                .append(WHITESPACE.repeat(padding))
                .append(WHITESPACE)
                .append(VERTICAL_BAR);
        }

        return formattedRow.toString();
    }

    private String formatDelimiterRow(List<String> headers, int numColumns,
                                      Map<String, Integer> maxLengthOfColumns,
                                      ColumnAlignment... alignments) {
        StringBuilder row = new StringBuilder(VERTICAL_BAR);

        for (int i = 0; i < numColumns; i++) {
            String value = headers.get(i);
            int maxLength = maxLengthOfColumns.get(value);
            row.append(WHITESPACE)
                .append(formatDelimiter(maxLength, alignments[i]))
                .append(WHITESPACE)
                .append(VERTICAL_BAR);
        }
        return row.toString();
    }

    private String centerAlign(String value, int maxLength) {
        int padding = Math.max(0, maxLength - value.length());
        int leftPadding = padding / 2;
        int rightPadding = padding - leftPadding;
        return WHITESPACE.repeat(leftPadding) + value + WHITESPACE.repeat(rightPadding);
    }

    private String formatValue(String value, int maxLength, ColumnAlignment alignment) {
        int padding = maxLength - value.length();

        return switch (alignment) {
            case LEFT -> value + WHITESPACE.repeat(padding);
            case RIGHT -> WHITESPACE.repeat(padding) + value;
            case CENTER, NOALIGNMENT -> centerAlign(value, maxLength);
        };
    }

    private String formatDelimiter(int maxLength, ColumnAlignment alignment) {
        int alignmentCharactersCount = alignment.getAlignmentCharactersCount();
        int numberOfDashes = maxLength - alignmentCharactersCount;

        return switch (alignment) {
            case LEFT -> COLON + DASH.repeat(numberOfDashes);
            case RIGHT -> DASH.repeat(numberOfDashes) + COLON;
            case CENTER -> COLON + DASH.repeat(numberOfDashes) + COLON;
            case NOALIGNMENT -> DASH.repeat(numberOfDashes);
        };
    }

    private Map<String, Integer> getMaxLengthColumn(Table table) {
        Map<String, Integer> maxLengthColumn = new HashMap<>();
        Collection<String> headers = table.getColumnNames();

        for (String col : headers) {
            int maxLen = col.length();
            Collection<String> columnData = table.getColumnData(col);

            for (String data : columnData) {
                maxLen = Math.max(maxLen, data.length());
            }

            maxLengthColumn.put(col, Math.max(maxLen, MIN_LENGTH_COLUMN));
        }

        return maxLengthColumn;
    }

    private List<List<String>> getRows(Table table) {
        int numRows = table.getRowsCount();
        Collection<String> headers = table.getColumnNames();
        List<String> columnNames = new LinkedList<>(headers);
        List<List<String>> rows = new LinkedList<>();

        for (int i = 0; i < numRows; i++) {
            List<String> currentRow = new LinkedList<>();
            for (String column : columnNames) {
                List<String> columnDataList = new ArrayList<>(table.getColumnData(column));
                currentRow.add(columnDataList.get(i));
            }
            rows.add(currentRow);
        }
        return rows;
    }
}


