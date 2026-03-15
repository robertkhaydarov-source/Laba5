package commands;

import manager.CollectionManager;

public class Remove_by_id implements Comand {
    private final String name="remove_by_id";
    private final CollectionManager collectionManager;

    public Remove_by_id(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String... args) {
        long id = Long.parseLong(args[0]);
        collectionManager.remove_by_id(id);
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
