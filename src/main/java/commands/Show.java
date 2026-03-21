package commands;

import manager.CollectionManager;
/**
 * Класс Show вывести в стандартный поток вывода все элементы коллекции в строковом представлении.
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class Show implements Comand {

    private final String name ="show";
    private final CollectionManager collectionManager;
    /**
     * Конструктор команды Show.
     *
     * @param collectionManager менеджер коллекции
     */
    public Show(CollectionManager collectionManager){
        this.collectionManager=collectionManager;
    }
    /**
     * Выполняет команду.
     *
     * @param args аргументы команды
     */
    public void execute(String... args) {
        System.out.println(collectionManager.show());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getInfo() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
