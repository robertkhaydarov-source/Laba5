package commands;

import manager.CollectionManager;
import manager.InputManager;
import manager.StudyGroupFactory;
import model.StudyGroup;

import java.time.ZonedDateTime;

public class Remove_lower implements Comand{
    private final String name="remove_lower";
    private final CollectionManager collectionManager;
    private final InputManager inputManager;
    private final StudyGroupFactory studyGroupFactory;
    public Remove_lower(CollectionManager collectionManager, InputManager inputManager, StudyGroupFactory studyGroupFactory) {
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
        this.studyGroupFactory = studyGroupFactory;
    }

    public void execute(String... args) {
        if (args.length == 12) {
            StudyGroup studyGroup = studyGroupFactory.createFromConsole(ZonedDateTime.now(), args);
            if(studyGroup!=null)collectionManager.remove_lower(studyGroup);
        }

        else {
            StudyGroup consoleStudyGroup = studyGroupFactory.createFromConsole(ZonedDateTime.now(), inputManager.consoleArgs());
            if(consoleStudyGroup!=null)collectionManager.remove_lower(consoleStudyGroup);
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
