package commands;

import manager.CollectionManager;
import manager.InputManager;
import manager.StudyGroupFactory;
/**
 * Класс Count_by_form_of_education выводит количество элементов, значение поля formOfEducation которых равно заданному.
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class Count_by_form_of_education implements Comand{

    private final String name="count_by_form_of_education";
    private final CollectionManager collectionManager;
    /**
     * Конструктор команды Add.
     *
     * @param collectionManager менеджер коллекции
     */
    public Count_by_form_of_education(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду.
     *
     * @param args аргументы команды
     */
    public void execute(String... args) {
        if (args.length == 1) {
            collectionManager.count_by_form_of_education(args[0]);
        }
        else {
            System.out.println("введите значение формы обучения");
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getInfo() {
        return "вывести количество элементов, значение поля formOfEducation которых равно заданному";
    }
}
