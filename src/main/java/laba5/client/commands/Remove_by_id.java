package laba5.client.commands;

import laba5.server.manager.CollectionDao;
import laba5.server.manager.CollectionManager;
import laba5.shared.actions.Request;
import laba5.shared.model.StudyGroup;

/**
 * Класс Remove_by_id удалить элемент из коллекции по его id.
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class Remove_by_id implements Command {

    private final String name="remove_by_id";
    private final CollectionManager collectionManager;
    private final CollectionDao collectionDao;
    /**
     * Конструктор команды Remove_by_id.
     *
     * @param collectionManager менеджер коллекции
     */
    public Remove_by_id(CollectionManager collectionManager, CollectionDao collectionDao){
        this.collectionManager = collectionManager;
        this.collectionDao = collectionDao;
    }

    /**
     * Выполняет команду.
     *
     * @param args аргументы команды
     */
    public String execute(String... args) {
        try {
            long id = Long.parseLong(args[0]);
            if(collectionManager.remove_by_id(id)){
                return "элемент с введенным id удален";
            }
            else {
                return "элемента с введенным id нет";
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return  "введен не корректный id" + e.getMessage();
        }
    }
    public String execute(Request request) {
        synchronized (collectionManager){
            if (request.getArgs()==null) {
                return "не введен id";
            }
            long id_update = Long.parseLong(request.getArgs().toString().trim());
            if(collectionDao.deleteStudy(id_update, request.getUserName())){
                collectionManager.remove_by_id(id_update);
                return "удаление из базы данных прошло успешно";
            }
            return "ошибка удаления";

        }
    }

    @Override
    public String execute(String args, StudyGroup studyGroup) {
        return "";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getInfo() {
        return "удалить элемент из коллекции по его id";
    }
}
