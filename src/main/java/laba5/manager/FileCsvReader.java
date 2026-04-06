package laba5.manager;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
/**
 * Класс FileManager отвечает за чтения из файла в формате CSV.
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class FileCsvReader {

    private final String filename;
    private String lastError = null;

    public FileCsvReader(String filename) {
        this.filename=filename;
    }

    /**
     * Читает данные из файла и парсит
     * @return массив StudyGroup
     * @throws IOException ошибка с файлом
     * @throws CsvException ошибка парсинга
     */
    private final static int ARGS_AMOUNT = 13;

    public List<String[]> readCSV() throws IOException {
        ArrayList<String[]> fileArgs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line=reader.readLine())!=null){
                try (CSVReader lineCsvReader = new CSVReader(new StringReader(line))) {
                    String[] parsed = lineCsvReader.readNext();
                    if (parsed!=null && parsed.length==ARGS_AMOUNT){
                        fileArgs.add(parsed);
                    }
                } catch (Exception e) {
                    System.err.println("Ошибка при парсинге строки: " + e.getMessage());;
                }
            }

        } catch (Exception e) {
            System.err.println("Ошибка при закрытии ридера: " + e.getMessage());
        }
        return fileArgs;
    }
}


