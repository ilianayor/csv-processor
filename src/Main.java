import bg.sofia.uni.fmi.mjt.csvprocessor.CsvProcessor;
import bg.sofia.uni.fmi.mjt.csvprocessor.CsvProcessorAPI;
import bg.sofia.uni.fmi.mjt.csvprocessor.exceptions.CsvDataNotCorrectException;
import bg.sofia.uni.fmi.mjt.csvprocessor.table.BaseTable;
import bg.sofia.uni.fmi.mjt.csvprocessor.table.Table;
import bg.sofia.uni.fmi.mjt.csvprocessor.table.printer.ColumnAlignment;
import bg.sofia.uni.fmi.mjt.csvprocessor.table.printer.MarkdownTablePrinter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

public class Main {

    public static void main(String[] args) {
        /*
        Table table = new BaseTable();

        String inputFilePath = "data.csv";  // Path to your CSV file
        String outPutFilePath = "data2.md";
        String delimiter = "/";        // Define delimiter (e.g., comma, tab, etc.)

        try {
            FileReader fileReader = new FileReader(inputFilePath);
            FileWriter fileWriter = new FileWriter(outPutFilePath);

            CsvProcessorAPI csvReader = new CsvProcessor(table);

            csvReader.readCsv(fileReader, delimiter);

            Collection<String> headers = table.getColumnNames();
            ColumnAlignment[] alignments = new ColumnAlignment[3];
            alignments[0] = ColumnAlignment.RIGHT;
            alignments[1] = ColumnAlignment.LEFT;
            alignments[2] = ColumnAlignment.CENTER;

            csvReader.writeTable(fileWriter, alignments);

            MarkdownTablePrinter printer = new MarkdownTablePrinter();
            Collection<String> result = printer.printTable(table, alignments);
            for (String row : result) {
                System.out.println(row);
            }

            System.out.println(table.getColumnData("col2"));

        } catch (IOException | CsvDataNotCorrectException e) {
            e.printStackTrace();
        }

        */
        //System.err.println("yes");
    }
}
