package laba5.server.commandServer;

import laba5.client.commands.Command;
import laba5.server.manager.CollectionDao;
import laba5.server.manager.CollectionManager;
import laba5.shared.actions.Request;
import laba5.shared.model.StudyGroup;

import java.util.Collection;
import java.util.Optional;

public class UpdateServer implements Command {
    private final CollectionManager collectionManager;
    private final CollectionDao collectionDao;

    public UpdateServer(CollectionManager collectionManager, CollectionDao collectionDao) {
        this.collectionManager = collectionManager;
        this.collectionDao = collectionDao;
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
    public String execute(Request request){
        synchronized (collectionManager){
            StudyGroup studyGroup = request.getStudyGroup();
            if (request.getArgs()==null) {
                return "не введен id";
            }
            long id_update = Long.parseLong(request.getArgs().toString());
            if(collectionDao.updateStudy(id_update, studyGroup, request.getUserName())){
                collectionManager.remove_by_id(id_update);
                studyGroup.setId(id_update);
                collectionManager.add(studyGroup);
                return "элемент обновлен в бд";
            }
            return "ошибка обновления";
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
