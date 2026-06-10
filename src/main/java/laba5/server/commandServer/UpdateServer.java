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
    public String execute(Request request) {
        synchronized (collectionManager) {
            long idUpdate = Long.parseLong(request.getArgs().toString().trim());
            StudyGroup existingGroup = collectionManager.getById(idUpdate);
            if (existingGroup == null) {
                return "Ошибка: Элемент с ID " + idUpdate + " не найден в коллекции.";
            }
            if (!existingGroup.getOwnerLogin().equals(request.getUserName())) {
                return "Ошибка: Элемент с ID " + idUpdate + " принадлежит другому пользователю. Вы не имеете прав на его модификацию.";
            }
            if (request.getStudyGroup() == null) {
                return "Права подтверждены";
            }
            StudyGroup newStudyGroup = request.getStudyGroup();
            if (!collectionDao.updateStudy(idUpdate, newStudyGroup, request.getUserName(), request.getPassword())) {
                return "Ошибка: Сбой при обновлении в базе данных. Проверьте пароль.";
            }
            collectionManager.remove_by_id(idUpdate);
            newStudyGroup.setId(idUpdate);
            newStudyGroup.setOwnerLogin(request.getUserName());
            collectionManager.add(newStudyGroup);

            return "Элемент с ID " + idUpdate + " успешно обновлён.";
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
