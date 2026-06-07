package laba5.server.commandServer;

import laba5.client.commands.Command;
import laba5.server.manager.CollectionDao;
import laba5.server.manager.CollectionManager;
import laba5.shared.actions.Request;
import laba5.shared.model.StudyGroup;

import java.util.List;
import java.util.stream.Collectors;

public class RemoveLowerServer implements Command {
    private final CollectionManager collectionManager;
    private final CollectionDao collectionDao;

    public RemoveLowerServer(CollectionManager collectionManager, CollectionDao collectionDao) {
        this.collectionManager = collectionManager;
        this.collectionDao = collectionDao;
    }

    @Override
    public String execute(String... args) {
        return "";
    }

    @Override
    public String execute(String args, StudyGroup studyGroup) {
        return "";
    }
    public String execute(Request request) {
        synchronized (collectionManager) {
            String userName = request.getUserName();
            StudyGroup studyGroup = request.getStudyGroup();
            List<StudyGroup> studyGroupList = collectionManager.showCollection().stream().filter(group->group.getOwnerLogin().equals(userName))
                    .filter(group -> group.compareTo(studyGroup) < 0)
                    .collect(Collectors.toList());
            int deleteCount=0;
            for(StudyGroup group:studyGroupList){
                if(collectionDao.deleteStudy(group.getId(), userName)){
                    collectionManager.remove_by_id(group.getId());
                    deleteCount++;
                }
            }
            return "Успешно удалено ваших элементов: " + deleteCount;
        }

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
