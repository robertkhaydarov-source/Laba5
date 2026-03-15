package commands;

import manager.CollectionManager;

public class Remove_last implements Comand{
    private final String name="remove_last";
    private final CollectionManager collectionManager;

    public Remove_last(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String... args) {
        if(collectionManager.show()!=null) collectionManager.remove_last();
        else System.out.println("коллекция пуста");
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
