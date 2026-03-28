package laba5.commands;

import laba5.manager.CollectionManager;
import laba5.manager.InputManager;
import laba5.manager.StudyGroupFactory;
import laba5.model.StudyGroup;

import java.time.ZonedDateTime;
/**
 * Класс Add_if_max добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции.
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class Add_if_max implements Command {

    private final String name="add_if_max";
    private final CollectionManager collectionManager;
    private final StudyGroupFactory studyGroupFactory;
    private final InputManager inputManager;
    /**
     * Конструктор команды Add_if_max.
     *
     * @param collectionManager менеджер коллекции
     * @param inputManager менеджер ввода
     * @param studyGroupFactory фабрика объектов StudyGroup
     */
    public Add_if_max(CollectionManager collectionManager, StudyGroupFactory studyGroupFactory, InputManager inputManager) {
        this.collectionManager = collectionManager;
        this.studyGroupFactory = studyGroupFactory;
        this.inputManager = inputManager;
    }

    /**
     * Выполняет команду.
     *
     * @param args аргументы команды
     */
    public void execute(String... args) {
        if (args.length == 12) {
            StudyGroup studyGroup = studyGroupFactory.createFromConsole(ZonedDateTime.now(), args);
            if(studyGroup!=null && (!collectionManager.showCollection().isEmpty())) collectionManager.add_if_max(studyGroup);
        }

        else {
            StudyGroup consoleStudyGroup = studyGroupFactory.createFromConsole(ZonedDateTime.now(), inputManager.consoleArgs());
            if(consoleStudyGroup!=null && (!collectionManager.showCollection().isEmpty()))collectionManager.add_if_max(consoleStudyGroup);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getInfo() {
        return "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }
}
