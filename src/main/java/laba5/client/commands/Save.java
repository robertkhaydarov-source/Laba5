package laba5.client.commands;

import com.opencsv.CSVWriter;
import laba5.manager.CollectionManager;
import laba5.shared.model.StudyGroup;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
/**
 * Класс Save сохранить коллекцию в файл.
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class Save implements Command {

    private final String name="save";
    private final CollectionManager collectionManager;
    private final String filename;
    /**
     * Конструктор команды Save.
     *
     * @param collectionManager менеджер коллекции
     * @param filename имя файла
     */
    public Save(CollectionManager collectionManager, String filename) {
        this.collectionManager = collectionManager;
        this.filename = filename;
    }

    /**
     * С помощью библиотеки собирает значения и парсит
     * после этого записывает в файл
     * @param args
     */
    public String execute(String... args) {
        List<StudyGroup> show = collectionManager.showCollection();
        try(CSVWriter csvWriter = new CSVWriter(new FileWriter(filename))){
            for(StudyGroup s:show) {
                String[] data = {
                        String.valueOf(s.getId()),
                        s.getName(),
                        s.getCoordinatesX(),
                        s.getCoordinatesY(),
                        String.valueOf(s.getStudentsCount()),
                        String.valueOf(s.getShouldBeExpelled()),
                        String.valueOf(s.getFormOfEducation()),
                        String.valueOf(s.getSemesterEnum()),
                        s.getGroupAdminN(),
                        s.getGroupAdminP(),
                        String.valueOf(s.getGroupAdminE()),
                        String.valueOf(s.getGroupAdminH()),
                        String.valueOf(s.getGroupAdminC()),
                };
                csvWriter.writeNext(data);
            }
            return "Данные успешно записаны.";
        }
        catch (IOException e) {
            return "Ошибка при записи: " + e.getMessage();
        }

    }

    @Override
    public String execute(String args, StudyGroup studyGroup) {
        return "";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getInfo() {
        return "сохранить коллекцию в файл";
    }
}
