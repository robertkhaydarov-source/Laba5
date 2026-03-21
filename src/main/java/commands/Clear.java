package commands;

import manager.CollectionManager;
import model.StudyGroup;
/**
 * Класс Clear очистить коллекцию.
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class Clear implements Comand{

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
    public void execute(String... args) {
        collectionManager.clear();
        System.out.println("коллекция очищена");
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
