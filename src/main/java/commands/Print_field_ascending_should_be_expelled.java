package commands;

import manager.CollectionManager;

public class Print_field_ascending_should_be_expelled implements Comand {
    private final String name="print_field_ascending_should_be_expelled";
    private final CollectionManager collectionManager;

    public Print_field_ascending_should_be_expelled(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String... args) {
        collectionManager.print_field_ascending_should_be_expelled();
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
