package bg.sofia.uni.fmi.mjt.csvprocessor;

import bg.sofia.uni.fmi.mjt.csvprocessor.exceptions.CsvDataNotCorrectException;
import bg.sofia.uni.fmi.mjt.csvprocessor.table.BaseTable;
import bg.sofia.uni.fmi.mjt.csvprocessor.table.Table;
import bg.sofia.uni.fmi.mjt.csvprocessor.table.printer.ColumnAlignment;
import bg.sofia.uni.fmi.mjt.csvprocessor.table.printer.MarkdownTablePrinter;
import bg.sofia.uni.fmi.mjt.csvprocessor.table.printer.TablePrinter;
import bg.sofia.uni.fmi.mjt.csvprocessor.validator.Validator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;

public class CsvProcessor implements CsvProcessorAPI {
    private final Table table;

    public CsvProcessor() {
        this(new BaseTable());
    }

    public CsvProcessor(Table table) {
        this.table = table;
    }

    @Override
    public void readCsv(Reader reader, String delimiter) throws CsvDataNotCorrectException {
        Validator.validateNotNull(delimiter, "delimiter");
        Validator.validateNotBlank(delimiter, "delimiter");

        delimiter = "\\Q" + delimiter + "\\E";

        try (var bufferedReader = new BufferedReader(reader)) {
            String headerLine = bufferedReader.readLine();

            if (headerLine == null || headerLine.isBlank()) {
                throw new CsvDataNotCorrectException("CSV file is empty.");
            }

            String[] data = headerLine.split(delimiter);
            table.addData(data);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] colData = line.split(delimiter);
                table.addData(colData);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("A problem occurred while reading from a file", e);
        }
    }

    @Override
    public void writeTable(Writer writer, ColumnAlignment... alignments) {
        Validator.validateNotNull(alignments, "alignments");

        try (var bufferedWriter = new BufferedWriter(writer)) {
            TablePrinter tablePrinter = new MarkdownTablePrinter();
            Collection<String> tableRows = tablePrinter.printTable(table, alignments);

            Iterator<String> it = tableRows.iterator();
            while (it.hasNext()) {
                bufferedWriter.write(it.next());

                if (it.hasNext()) {
                    bufferedWriter.write(System.lineSeparator());
                }
            }

            bufferedWriter.flush();
        } catch (IOException e) {
            throw new UncheckedIOException("A problem occurred while writing to a file", e);
        }
    }
}
