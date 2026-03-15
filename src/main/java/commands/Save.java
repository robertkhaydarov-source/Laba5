package commands;

import com.opencsv.CSVWriter;
import manager.CollectionManager;
import model.StudyGroup;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Save implements Comand{
    private final String name="save";
    private final CollectionManager collectionManager;
    private final String filename;

    public Save(CollectionManager collectionManager, String filename) {
        this.collectionManager = collectionManager;
        this.filename = filename;
    }
    public void execute(String... args) {
        List<StudyGroup> show = collectionManager.show();
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
            System.out.println("Данные успешно записаны.");
        }
        catch (IOException e) {
            System.err.println("Ошибка при записи: " + e.getMessage());
        }

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
