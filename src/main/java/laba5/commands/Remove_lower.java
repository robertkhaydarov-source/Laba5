package laba5.commands;

import laba5.manager.CollectionManager;
import laba5.manager.InputManager;
import laba5.manager.StudyGroupFactory;
import laba5.model.StudyGroup;

import java.time.ZonedDateTime;
/**
 * Класс Remove_lower удалить из коллекции все элементы, меньшие, чем заданный.
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class Remove_lower implements Command {

    private final String name="remove_lower";
    private final CollectionManager collectionManager;
    private final InputManager inputManager;
    private final StudyGroupFactory studyGroupFactory;
    /**
     * Конструктор команды Remove_by_id.
     * @param inputManager менеджер ввода
     * @param collectionManager менеджер коллекции
     * @param studyGroupFactory фабрика объектов StudyGroup
     */
    public Remove_lower(CollectionManager collectionManager, InputManager inputManager, StudyGroupFactory studyGroupFactory) {
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
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
            if(studyGroup!=null){
                collectionManager.remove_lower(studyGroup);
            }
        }

        else {
            StudyGroup consoleStudyGroup = studyGroupFactory.createFromConsole(ZonedDateTime.now(), inputManager.consoleArgs());
            if(consoleStudyGroup!=null){
                collectionManager.remove_lower(consoleStudyGroup);
            }
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getInfo() {
        return "удалить из коллекции все элементы, меньшие, чем заданный";
    }
}
