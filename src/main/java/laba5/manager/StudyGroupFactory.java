package laba5.manager;

import laba5.model.*;

import java.time.ZonedDateTime;
import java.util.Arrays;

/**
 * Класс StudyGroupFactory отвечает за создание элемента коллекции, принимает массив аргументов.
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class StudyGroupFactory {

    private final CollectionManager collectionManager;

    public StudyGroupFactory(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    private StudyGroup createStudyGroup(ZonedDateTime creationDate, long id, String... args){
        String name = args[0];
        Long x = Long.parseLong(args[1]);
        Long y = Long.parseLong(args[2]);
        Coordinates coordinates = new Coordinates(x, y);
        int studentCount = Integer.parseInt(args[3]);
        Long shouldBeExpelled = Long.parseLong(args[4]);
        FormOfEducation formOfEducation = FormOfEducation.valueOf(args[5].toUpperCase());
        Semester semester = Semester.valueOf(args[6].toUpperCase());
        String namePerson = args[7];
        String passportId = args[8];
        Color colorEye= Color.valueOf(args[9].toUpperCase());
        Color colorHair= Color.valueOf(args[10].toUpperCase());
        Country nationality=Country.valueOf(args[11].toUpperCase());
        Person person = new Person(namePerson, passportId, colorEye, colorHair, nationality);
        StudyGroup studyGroup = new StudyGroup(id, name, coordinates, creationDate, studentCount, shouldBeExpelled, formOfEducation, semester, person);
        if (studyGroup.validate()) return studyGroup;
        else {
            System.out.println("объект не создан не валидные данные");
            return null;
        }

    }

    /**
     *
     * @param dateTime время
     * @param args аргументы
     * @return создает StudyGroup из консоли.
     */
    public StudyGroup createFromConsole(ZonedDateTime dateTime, String[] args){
        if (args!=null){
            long id =collectionManager.getCurrentId();
            return createStudyGroup(dateTime, id, args);
        }
        return null;
    }

    /**
     *
     * @param dateTime время
     * @param args аргументы
     * @return создает StudyGroup из файла.
     */
    public StudyGroup createFromFile(ZonedDateTime dateTime, String[] args){
        if(args!=null){
            long id = Long.parseLong(args[0]);
            String[] withoutId = Arrays.copyOfRange(args, 1, args.length);
            return createStudyGroup(dateTime, id, withoutId);
        }
        return null;
    }
}


