package laba5.commands;

import laba5.manager.CollectionManager;
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
    public void execute(String... args) {
        if(!collectionManager.showCollection().isEmpty()){
            collectionManager.print_field_ascending_should_be_expelled();
            System.out.println("имена отсортированы");
        }
        else System.out.println("коллекция пуста");
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
