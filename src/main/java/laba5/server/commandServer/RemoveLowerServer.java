package laba5.server.commandServer;

import laba5.client.commands.Command;
import laba5.server.manager.CollectionManager;
import laba5.shared.model.StudyGroup;

public class RemoveLowerServer implements Command {
    private final CollectionManager collectionManager;

    public RemoveLowerServer(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(String... args) {
        return "";
    }

    @Override
    public String execute(String args, StudyGroup studyGroup) {
        if(studyGroup!=null){
        if(collectionManager.remove_lower(studyGroup)){
            return "элемент успешно удален";
        }
        return "не удален";
        }
        return "коллекция пустая";
    }

    @Override
    public String getName() {
        return "remove_lower";
    }

    @Override
    public String getInfo() {
        return "удалить из коллекции все элементы, меньшие, чем заданный";
    }
}
