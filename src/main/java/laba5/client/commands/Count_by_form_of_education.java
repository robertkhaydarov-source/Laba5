package laba5.client.commands;

import laba5.server.manager.CollectionManager;
import laba5.shared.model.StudyGroup;

/**
 * Класс Count_by_form_of_education выводит количество элементов, значение поля formOfEducation которых равно заданному.
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class Count_by_form_of_education implements Command {

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
    public String execute(String... args) {
        if (args.length == 1) {
            return collectionManager.count_by_form_of_education(args[0]);
        }
        else {
            return "введите значение формы обучения";
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
        return "вывести количество элементов, значение поля formOfEducation которых равно заданному";
    }
}
