package commands;

import manager.CollectionManager;
import manager.InputManager;
import manager.StudyGroupFactory;
import model.*;

import java.time.ZonedDateTime;
/**
 * Класс Add добавляет элемент в коллекцию.
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class Add implements Comand{

    private final String name="add";
    private final CollectionManager collectionManager;
    private final InputManager inputManager;
    private final StudyGroupFactory studyGroupFactory;
    /**
     * Конструктор команды Add.
     *
     * @param collectionManager менеджер коллекции
     * @param inputManager менеджер ввода
     * @param studyGroupFactory фабрика объектов StudyGroup
     */
    public Add(CollectionManager collectionManager, InputManager inputManager, StudyGroupFactory studyGroupFactory) {
        this.collectionManager = collectionManager;
        this.inputManager=inputManager;
        this.studyGroupFactory = studyGroupFactory;
    }
    /**
     * Выполняет команду.
     *
     * @param args аргументы команды
     */
    public void execute(String... args) {
        if (args.length == 12) {
            StudyGroup studyGroup = studyGroupFactory.createFromConsole(ZonedDateTime.now(), args);
            if(studyGroup!=null) collectionManager.add(studyGroup);
        }

        else {
            StudyGroup consoleStudyGroup = studyGroupFactory.createFromConsole(ZonedDateTime.now(), inputManager.consoleArgs());
            if(consoleStudyGroup!=null)collectionManager.add(consoleStudyGroup);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getInfo() {
        return "добавить новый элемент в коллекцию";
    }
}
