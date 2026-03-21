package commands;

import manager.CollectionManager;
/**
 * Класс Filter_contains_name вывести элементы, значение поля name которых содержит заданную подстроку.
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class Filter_contains_name implements Comand{

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
    public void execute(String... args) {
        collectionManager.filter_contains_name(args[0]);
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
