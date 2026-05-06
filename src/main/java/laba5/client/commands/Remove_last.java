package laba5.client.commands;

import laba5.server.manager.CollectionManager;
import laba5.shared.model.StudyGroup;

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
    public String execute(String... args) {
        if(!collectionManager.showCollection().isEmpty()) {
            collectionManager.remove_last();
            if(!collectionManager.remove_last()){
                return "последний элемент удален";
            }
            return "последний элемент не удален";
        }
        else return "коллекция пуста";
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
        return "удалить последний элемент из коллекции";
    }
}
