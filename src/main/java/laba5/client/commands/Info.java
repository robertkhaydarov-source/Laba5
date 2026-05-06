package laba5.client.commands;

import laba5.server.manager.CollectionManager;
import laba5.shared.model.StudyGroup;

/**
 * Класс Info вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.).
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class Info implements Command {

    private String name = "info";
    private CollectionManager collectionManager;
    /**
     * Конструктор команды Info.
     *
     * @param collectionManager менеджер коллекции
     */
    public Info(CollectionManager collectionManager){
        this.collectionManager=collectionManager;
    }
    /**
     * Выполняет команду.
     *
     * @param args аргументы команды
     */
    public String execute(String... args) {
        String line = collectionManager.getType() + '\n' + collectionManager.getInitilizationDate() + '\n' + collectionManager.getSize();
        if (collectionManager.showCollection().isEmpty()){
            return "коллекция пуста";
        }
        return line;

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
    public String getInfo(){
        return "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }
}
