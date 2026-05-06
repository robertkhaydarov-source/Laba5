package laba5.client.commands;

import laba5.manager.CollectionManager;
import laba5.shared.model.StudyGroup;

/**
 * Класс Print_field_ascending_should_be_expelled вывести значения поля shouldBeExpelled всех элементов в порядке возрастания.
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class Print_field_ascending_should_be_expelled implements Command {

    private final String name="print_field_ascending_should_be_expelled";
    private final CollectionManager collectionManager;
    /**
     * Конструктор команды Print_field_ascending_should_be_expelled.
     *
     * @param collectionManager менеджер коллекции
     */
    public Print_field_ascending_should_be_expelled(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(String... args) {
        if(!collectionManager.showCollection().isEmpty()){
            collectionManager.print_field_ascending_should_be_expelled();
            return "имена отсортированы";
        }
        else return "коллекция пуста";
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
        return "вывести значения поля shouldBeExpelled всех элементов в порядке возрастания";
    }
}
