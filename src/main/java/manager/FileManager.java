package manager;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private final String filename;
    public FileManager(String filename) {
        this.filename=filename;
    }

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
            throw new RuntimeException(e);
        }
    }
    public String getFilename() {
        return filename;
    }
}
