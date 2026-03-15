package commands;

import manager.CollectionManager;

public class Info implements Comand {
    private String name = "info";
    private CollectionManager collectionManager;
    public Info(CollectionManager collectionManager){
        this.collectionManager=collectionManager;
    }
    public void execute(String... args) {

        System.out.println(collectionManager.getType());
        System.out.println(collectionManager.getInitilizationDate());
        System.out.println(collectionManager.getSize());

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
