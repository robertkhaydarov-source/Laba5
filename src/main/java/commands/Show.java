package commands;

import manager.CollectionManager;
import model.StudyGroup;

import java.util.List;

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
        List<StudyGroup> studyGroups = collectionManager.showCollection();
        if (studyGroups!=null){
            for (StudyGroup st: studyGroups){
                System.out.println(st);
            }
        }
        else System.out.println("коллекция пуста");
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
