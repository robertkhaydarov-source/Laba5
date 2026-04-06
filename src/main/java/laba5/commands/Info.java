package laba5.commands;

import laba5.manager.CollectionManager;
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
    public void execute(String... args) {

        System.out.println(collectionManager.getType());
        System.out.println(collectionManager.getInitilizationDate());
        System.out.println(collectionManager.getSize());
        if (collectionManager.showCollection().isEmpty()){
            System.out.println("коллекция пуста");
        }

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
