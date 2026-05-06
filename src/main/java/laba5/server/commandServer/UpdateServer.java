package laba5.server.commandServer;

import laba5.client.commands.Command;
import laba5.manager.CollectionManager;
import laba5.shared.model.StudyGroup;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateServer implements Command {
    private final CollectionManager collectionManager;

    public UpdateServer(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(String... args) {
        return "";
    }

    @Override
    public String execute(String args, StudyGroup studyGroup) {
        try {
            if (args==null) {
                return "не введен id";
            }
            long id_update = Long.parseLong(args);
            if (!collectionManager.remove_by_id(id_update)) {
                return "элемент не найден";
            }
            if (studyGroup != null) {
                collectionManager.add(studyGroup);
                return "элемент обновлен";
            }
            return "неверное количество аргументов";
        } catch (NumberFormatException e) {
            return "Ошибка при вводе id: " + e.getMessage();
        }catch (IllegalArgumentException e){
            return e.getMessage();
        }

    }
    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getInfo() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }
}
