package commands;

import manager.CollectionManager;
import manager.InputManager;
import manager.StudyGroupFactory;

public class Count_by_form_of_education implements Comand{
    private final String name="count_by_form_of_education";
    private final CollectionManager collectionManager;

    public Count_by_form_of_education(CollectionManager collectionManager, StudyGroupFactory studyGroupFactory, InputManager inputManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String... args) {
        if (args.length == 1) {
            collectionManager.count_by_form_of_education(args[0]);
        }

        else {
            System.out.println(",e,ee,");
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getInfo() {
        return "вывести количество элементов, значение поля formOfEducation которых равно заданному";
    }
}
