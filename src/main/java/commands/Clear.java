package commands;

import manager.CollectionManager;
import model.StudyGroup;

public class Clear implements Comand{
    private final CollectionManager collectionManager;
    private final String name="clear";
    private StudyGroup studyGroup;
    public Clear(CollectionManager collectionManager){
        this.collectionManager=collectionManager;
    }

    @Override
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
