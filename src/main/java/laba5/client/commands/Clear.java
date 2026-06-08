package laba5.client.commands;

import laba5.server.manager.CollectionDao;
import laba5.server.manager.CollectionManager;
import laba5.shared.actions.Request;
import laba5.shared.model.StudyGroup;
/**
 * Класс Clear очистить коллекцию.
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class Clear implements Command {

    private final CollectionManager collectionManager;
    private final String name="clear";
    private StudyGroup studyGroup;
    private final CollectionDao collectionDao;
    /**
     * Конструктор команды Clear.
     *
     * @param collectionManager менеджер коллекции
     */
    public Clear(CollectionManager collectionManager, CollectionDao collectionDao){
        this.collectionManager=collectionManager;
        this.collectionDao = collectionDao;
    }

    /**
     * Выполняет команду.
     *
     * @param args аргументы команды
     */
    public String execute(String... args) {
        collectionManager.clear();
        return "коллекция очищена";
    }
    @Override
    public String execute(Request request) {
        synchronized (collectionManager) {
            // Вызываем метод и получаем количество удалённых строк
            int deletedRows = collectionDao.clearAllGroups(request.getUserName(), request.getPassword());

            if (deletedRows == -1) {
                return "Ошибка: Не удалось выполнить очистку в базе данных. Проверьте соединение или учётные данные.";
            }

            // Даже если удалено 0 строк, мы обязаны вызвать очистку памяти (на всякий случай)
            collectionManager.clearAllOwnedBy(request.getUserName());

            if (deletedRows == 0) {
                return "Очистка завершена. У вас не было созданных элементов в коллекции.";
            }

            return "Очистка успешно завершена! Из базы данных и оперативной памяти удалено ваших элементов: " + deletedRows;
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
        return "очистить коллекцию";
    }
}
