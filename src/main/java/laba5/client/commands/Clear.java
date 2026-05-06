package laba5.client.commands;

import laba5.manager.CollectionManager;
import laba5.shared.model.StudyGroup;
/**
 * Класс Clear очистить коллекцию.
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class Clear implements Command {

    private final CollectionManager collectionManager;
    private final String name="clear";
    private StudyGroup studyGroup;
    /**
     * Конструктор команды Clear.
     *
     * @param collectionManager менеджер коллекции
     */
    public Clear(CollectionManager collectionManager){
        this.collectionManager=collectionManager;
    }

    /**
     * Выполняет команду.
     *
     * @param args аргументы команды
     */
    public String execute(String... args) {
        collectionManager.clear();
        return "коллекция очищена";
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
        return "очистить коллекцию";
    }
}
