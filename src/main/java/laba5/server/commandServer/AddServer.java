package laba5.server.commandServer;

import laba5.client.commands.Command;
import laba5.server.manager.CollectionManager;
import laba5.shared.model.StudyGroup;

public class AddServer implements Command {
    private CollectionManager collectionManager;
    public AddServer(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(String... args) {
        return "";
    }

    @Override
    public String execute(String args, StudyGroup studyGroup) {
        try {
            return collectionManager.add(studyGroup);
        } catch (Exception e) {
            return "ошибка" + e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getInfo() {
        return "добавить новый элемент в коллекцию";
    }
}
