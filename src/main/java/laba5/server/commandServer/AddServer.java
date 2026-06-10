package laba5.server.commandServer;

import laba5.client.commands.Command;
import laba5.server.manager.CollectionDao;
import laba5.server.manager.CollectionManager;
import laba5.shared.actions.Request;
import laba5.shared.model.StudyGroup;

public class AddServer implements Command {
    private final CollectionManager collectionManager;
    private final CollectionDao collectionDao;

    public AddServer(CollectionManager collectionManager, CollectionDao collectionDao) {
        this.collectionManager = collectionManager;
        this.collectionDao = collectionDao;
    }

    @Override
    public String execute(Request request) {
        StudyGroup studyGroup = request.getStudyGroup();

        long newId = collectionDao.saveGroup(studyGroup, request.getUserName(), request.getPassword());

        if (newId != -1) {
            studyGroup.setId(newId);

            studyGroup.setOwnerLogin(request.getUserName());

            collectionManager.add(studyGroup);
            return "Элемент успешно добавлен в базу данных и оперативную память с ID: " + newId;
        }

        return "Ошибка: не удалось сохранить группу в базу данных (возможно, неверный логин или пароль).";
    }

    @Override
    public String execute(String... args) { return ""; }

    @Override
    public String execute(String args, StudyGroup studyGroup) { return ""; }

    @Override
    public String getName() { return "add"; }

    @Override
    public String getInfo() { return "добавить новый элемент в коллекцию"; }
}