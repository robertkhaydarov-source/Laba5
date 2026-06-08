package laba5.client.commands;

import laba5.server.manager.CollectionDao;
import laba5.server.manager.CollectionManager;
import laba5.shared.actions.Request;
import laba5.shared.model.StudyGroup;

import java.util.Comparator;
import java.util.Optional;

/**
 * Класс Remove_last удалить последний элемент из коллекции.
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class Remove_last implements Command {

    private final String name="remove_last";
    private final CollectionManager collectionManager;
    private final CollectionDao collectionDao;
    /**
     * Конструктор команды Remove_last.
     *
     * @param collectionManager менеджер коллекции
     */
    public Remove_last(CollectionManager collectionManager, CollectionDao collectionDao) {
        this.collectionManager = collectionManager;
        this.collectionDao = collectionDao;
    }

    /**
     * Выполняет команду.
     *
     * @param args аргументы команды
     */
    public String execute(String... args) {
        if(!collectionManager.showCollection().isEmpty()) {
            if(collectionManager.remove_last()){
                return "последний элемент удален";
            }
            return "последний элемент не удален";
        }
        else return "коллекция пуста";
    }

    @Override
    public String execute(String args, StudyGroup studyGroup) {
        return "";
    }
    @Override
    public String execute(Request request){
        synchronized (collectionManager) {
            String username = request.getUserName();
            Optional<StudyGroup> lastOwnedGroup = collectionManager.showCollection().stream()
                    .filter(group -> group.getOwnerLogin().equals(username))
                    .max(Comparator.comparingLong(StudyGroup::getId));

            if (lastOwnedGroup.isEmpty()) {
                return "Ошибка: У вас нет созданных элементов в этой коллекции.";
            }
            long idToDelete = lastOwnedGroup.get().getId();
            if (collectionDao.deleteStudy(idToDelete, username, request.getPassword())) {
                collectionManager.remove_by_id(idToDelete);
                return "Ваш последний элемент успешно удален.";
            } else {
                return "Ошибка удаления из базы данных.";
            }
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getInfo() {
        return "удалить последний элемент из коллекции";
    }
}
