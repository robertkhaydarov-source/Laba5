package laba5.server.commandServer;

import laba5.client.commands.Command;
import laba5.server.manager.CollectionDao;
import laba5.server.manager.CollectionManager;
import laba5.shared.actions.Request;
import laba5.shared.model.StudyGroup;

import java.util.Collection;
import java.util.Comparator;

public class AddIfMaxServer implements Command {
    private final CollectionManager collectionManager;
    private final CollectionDao collectionDao;
    public AddIfMaxServer(CollectionManager collectionManager,  CollectionDao collectionDao) {
        this.collectionManager = collectionManager;
        this.collectionDao = collectionDao;
    }

    @Override
    public String execute(String... args) {
        return "Ошибка на сервере должен вызываться другой метод";
    }

    @Override
    public String execute(String args, StudyGroup studyGroup) {return "444";}
    @Override
    public String execute(Request request) {
        synchronized (collectionManager){
            StudyGroup studyGroup = request.getStudyGroup();
            if(!collectionManager.showCollection().isEmpty()){
                StudyGroup max = collectionManager.showCollection().stream().max(Comparator.naturalOrder()).get();
                if(studyGroup.compareTo(max)>0){
                    return "Элемент не является максимальным. Добавление отклонено.";
                }

            }
            long newId = collectionDao.saveGroup(studyGroup, request.getUserName());
            if(newId == -1){
                studyGroup.setId(newId);
                collectionManager.add(studyGroup);
            }
            return "Ошибка: не удалось сохранить группу в базу данных.";
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
