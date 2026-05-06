package laba5.client.commands;

import laba5.manager.CollectionManager;
import laba5.shared.model.StudyGroup;

/**
 * Класс Filter_contains_name вывести элементы, значение поля name которых содержит заданную подстроку.
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class Filter_contains_name implements Command {

    private final String name="filter_contains_name";
    private final CollectionManager collectionManager;
    /**
     * Конструктор команды Filter_contains_name.
     *
     * @param collectionManager менеджер коллекции
     */
    public Filter_contains_name(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду.
     *
     * @param args аргументы команды
     */
    public String execute(String... args) {
        if (collectionManager.showCollection().isEmpty()){
            return "коллекция пуста";
        }
        if (args.length==1){
            if(collectionManager.filter_contains_name(args[0]));
            else return "таких элементов нет";
        }
        else return "Введите имя без пробелов";
        return "";
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
        return "вывести элементы, значение поля name которых содержит заданную подстроку";
    }
}
