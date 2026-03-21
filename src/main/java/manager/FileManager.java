package manager;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Класс FileManager отвечает за чтения из файла в формате CSV.
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class FileManager {

    private final String filename;
    public FileManager(String filename) {
        this.filename=filename;
    }

    /**
     * Читает данные из файла и парсит
     * @return массив StudyGroup
     * @throws IOException ошибка с файлом
     * @throws CsvException ошибка парсинга
     */
    public List<String[]> fileReader() throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(filename))){
            ArrayList<String[]> fileArgs = new ArrayList<>();
            String[] nextLine;
            while ((nextLine=reader.readNext())!=null) {
                if (nextLine.length == 13) {
                    fileArgs.add(nextLine);
                }
            }
            return fileArgs;
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода " + e.getMessage());
        }
        return List.of();
    }
}
