package laba5.commands;

import laba5.manager.CollectionManager;
/**
 * Класс Remove_last удалить последний элемент из коллекции.
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class Remove_last implements Command {

    private final String name="remove_last";
    private final CollectionManager collectionManager;
    /**
     * Конструктор команды Remove_last.
     *
     * @param collectionManager менеджер коллекции
     */
    public Remove_last(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду.
     *
     * @param args аргументы команды
     */
    public void execute(String... args) {
        if(!collectionManager.showCollection().isEmpty()) {
            collectionManager.remove_last();
            System.out.println("последний элемент удален");
        }
        else System.out.println("коллекция пуста");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getInfo() {
        return "удалить последний элемент из коллекции";
    }
}
