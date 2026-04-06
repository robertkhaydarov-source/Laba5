package laba5.commands;

import laba5.manager.CollectionManager;
import laba5.manager.InputManager;
import laba5.manager.StudyGroupFactory;
import laba5.model.StudyGroup;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Класс Update обновить значение элемента коллекции, id которого равен заданному.
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class Update implements Command {

    private final InputManager inputManager;
    private final StudyGroupFactory studyGroupFactory;

    /**
     * Ищет элемент по id удаляет его и создает новый
     * @param args аргументы введенные после имени команды
     */
    public void execute(String... args) {
        try {
            if (args.length == 13){
                long id_update=Long.parseLong(args[0]);
                if(!collectionManager.showCollection().isEmpty()){
                    if(collectionManager.remove_by_id(id_update));
                    else {
                        System.out.println("элемент не найден");
                        return;
                    }
                }
                else {
                    System.out.println("коллекция пуста");
                    return;
                }

                List<String> list = new ArrayList<>(Arrays.asList(args));
                list.remove(0);
                String[] newArgs = list.toArray(new String[0]);
                StudyGroup studyGroup = studyGroupFactory.createFromConsole(ZonedDateTime.now(), newArgs);
                if(studyGroup!=null) collectionManager.add(studyGroup);
                System.out.println("элемент обновлен");

            }
            else if(args.length == 1){
                long id_update=Long.parseLong(args[0]);
                if(collectionManager.remove_by_id(id_update));
                else {
                    System.out.println("элемент не найден");
                    return;
                }
                StudyGroup addstudyGroup = studyGroupFactory.createFromConsole(ZonedDateTime.now(), inputManager.consoleArgs());
                if(addstudyGroup!=null){
                    collectionManager.add(addstudyGroup);
                }
                System.out.println("элемент обновлен");
            }
            else System.out.println("не введен id");
        } catch (NumberFormatException e) {
            System.err.println("Ошибка при вводе id: " + e.getMessage());;
        }

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getInfo() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }

    private final CollectionManager collectionManager;
    private final String name = "update";
    /**
     * Конструктор команды Update.
     *
     * @param collectionManager менеджер коллекции
     * @param inputManager менеджер ввода
     * @param studyGroupFactory фабрика объектов StudyGroup
     */
    public Update(InputManager inputManager, StudyGroupFactory studyGroupFactory, CollectionManager collectionManager) {
        this.inputManager = inputManager;
        this.studyGroupFactory = studyGroupFactory;
        this.collectionManager = collectionManager;


    }
}
