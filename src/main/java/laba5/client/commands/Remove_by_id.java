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
    @Override
    public String execute(Request request) {
        synchronized (collectionManager) {
            if (request.getArgs() == null || request.getArgs().toString().isEmpty()) {
                return "Ошибка: Не указан ID для удаления.";
            }
            try {
                long id = Long.parseLong(request.getArgs().toString().trim());
                StudyGroup group = collectionManager.getById(id);

                if (group == null) {
                    return "Ошибка: Элемент с ID " + id + " не существует.";
                }
                if (!group.getOwnerLogin().equals(request.getUserName())) {
                    return "Ошибка: Отказано в доступе. Вы не являетесь владельцем элемента с ID " + id;
                }

                if (collectionDao.deleteStudy(id, request.getUserName(), request.getPassword())) {
                    collectionManager.remove_by_id(id);
                    return "Элемент с ID " + id + " успешно удалён из базы данных и памяти.";
                }
                return "Ошибка: База данных отклонила удаление.";
            } catch (NumberFormatException e) {
                return "Ошибка: ID должен быть числом.";
            }
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
