package commands;

import manager.CollectionManager;
/**
 * Класс Remove_by_id удалить элемент из коллекции по его id.
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class Remove_by_id implements Comand {

    private final String name="remove_by_id";
    private final CollectionManager collectionManager;
    /**
     * Конструктор команды Remove_by_id.
     *
     * @param collectionManager менеджер коллекции
     */
    public Remove_by_id(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду.
     *
     * @param args аргументы команды
     */
    public void execute(String... args) {
        try {
            long id = Long.parseLong(args[0]);
            collectionManager.remove_by_id(id);
        } catch (NumberFormatException e) {
            System.err.println("введен не корректный id");
        }
    }


        @Override
    public String getName() {
        return name;
    }

    @Override
    public String getInfo() {
        return "удалить элемент из коллекции по его id";
    }
}
