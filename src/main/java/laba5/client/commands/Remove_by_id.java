package laba5.client.commands;

import laba5.server.manager.CollectionManager;
import laba5.shared.model.StudyGroup;

/**
 * Класс Remove_by_id удалить элемент из коллекции по его id.
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class Remove_by_id implements Command {

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
    public String execute(String... args) {
        try {
            long id = Long.parseLong(args[0]);
            if(collectionManager.remove_by_id(id)){
                return "элемент с введенным id удален";
            }
            else {
                return "элемента с введенным id нет";
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return  "введен не корректный id" + e.getMessage();
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
        return "удалить элемент из коллекции по его id";
    }
}
