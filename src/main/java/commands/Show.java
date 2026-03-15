package commands;

import manager.CollectionManager;

public class Show implements Comand {
    private final String name ="show";
    private final CollectionManager collectionManager;

    public Show(CollectionManager collectionManager){
        this.collectionManager=collectionManager;
    }
    @Override
    public void execute(String... args) {
        System.out.println(collectionManager.show());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getInfo() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
