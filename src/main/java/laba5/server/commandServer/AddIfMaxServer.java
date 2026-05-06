package laba5.server.commandServer;

import laba5.client.commands.Command;
import laba5.server.manager.CollectionManager;
import laba5.shared.model.StudyGroup;

public class AddIfMaxServer implements Command {
    private final CollectionManager collectionManager;

    public AddIfMaxServer(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(String... args) {
        return "";
    }

    @Override
    public String execute(String args, StudyGroup studyGroup) {
        boolean added = collectionManager.add_if_max(studyGroup);
        if (studyGroup != null) {
            if (!added) {
                return "элемент не добавлен (не максимальный)";
            } else {
                return "элемент добавлен, условие выполнено";
            }
        } else {
            return "неверное количество аргументов";
        }
    }
    @Override
    public String getName() {
        return "add_if_max";
    }

    @Override
    public String getInfo() {
        return "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }
}
