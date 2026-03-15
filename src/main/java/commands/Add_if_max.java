package commands;

import manager.CollectionManager;
import manager.InputManager;
import manager.StudyGroupFactory;
import model.StudyGroup;

import java.time.ZonedDateTime;

public class Add_if_max implements Comand{
    private final String name="add_if_max";
    private final CollectionManager collectionManager;
    private final StudyGroupFactory studyGroupFactory;
    private final InputManager inputManager;

    public Add_if_max(CollectionManager collectionManager, StudyGroupFactory studyGroupFactory, InputManager inputManager) {
        this.collectionManager = collectionManager;
        this.studyGroupFactory = studyGroupFactory;
        this.inputManager = inputManager;
    }

    @Override
    public void execute(String... args) {
        if (args.length == 12) {
            StudyGroup studyGroup = studyGroupFactory.createFromConsole(ZonedDateTime.now(), args);
            if(studyGroup!=null && (collectionManager.show()!=null)) collectionManager.add_if_max(studyGroup);
        }

        else {
            StudyGroup consoleStudyGroup = studyGroupFactory.createFromConsole(ZonedDateTime.now(), inputManager.consoleArgs());
            if(consoleStudyGroup!=null && (collectionManager.show()!=null))collectionManager.add_if_max(consoleStudyGroup);
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
