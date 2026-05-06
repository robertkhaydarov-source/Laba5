package laba5.client.commands;

import laba5.server.manager.CollectionManager;
import laba5.shared.model.StudyGroup;

import java.util.List;

/**
 * Класс Show вывести в стандартный поток вывода все элементы коллекции в строковом представлении.
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class Show implements Command {

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
    public String execute(String... args) {
        List<StudyGroup> studyGroups = collectionManager.showCollection();
        String line = "";
        if (!studyGroups.isEmpty()){
            for (StudyGroup st: studyGroups){
                 line+= st + "\n";
            }
            return line;
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
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
