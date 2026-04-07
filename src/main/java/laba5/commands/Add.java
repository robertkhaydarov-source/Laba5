package laba5.commands;

import laba5.manager.CollectionManager;
import laba5.manager.InputManager;
import laba5.manager.StudyGroupFactory;
import laba5.model.StudyGroup;

import java.time.ZonedDateTime;
/**
 * Класс Add добавляет элемент в коллекцию.
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class Add implements Command {

    private final String name="add";
    private final CollectionManager collectionManager;
    private final InputManager inputManager;
    private final StudyGroupFactory studyGroupFactory;
    /**
     * Конструктор команды Add.
     *
     * @param collectionManager менеджер коллекции
     * @param inputManager менеджер ввода
     * @param studyGroupFactory фабрика объектов StudyGroup
     */
    public Add(CollectionManager collectionManager, InputManager inputManager, StudyGroupFactory studyGroupFactory) {
        this.collectionManager = collectionManager;
        this.inputManager=inputManager;
        this.studyGroupFactory = studyGroupFactory;
    }
    /**
     * Выполняет команду.
     *
     * @param args аргументы команды
     */
    public void execute(String... args) {
            if (args.length == 12) {
                try {
                    StudyGroup studyGroup = studyGroupFactory.createFromConsole(ZonedDateTime.now(), args);
                    if (studyGroup != null) {
                        if (collectionManager.add(studyGroup)) {
                            System.out.println("элемент добавлен в коллекцию");
                        }
                    }
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }
            }
            else {
                try {
                    StudyGroup consoleStudyGroup = studyGroupFactory.createFromConsole(ZonedDateTime.now(), inputManager.consoleArgs());
                    if(consoleStudyGroup!=null){
                        if(collectionManager.add(consoleStudyGroup)){
                            System.out.println("элемент добавлен в коллекцию");
                        }
                    }
                }catch (IllegalArgumentException e){
                    System.err.println(e.getMessage());
                }

            }
        }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getInfo() {
        return "добавить новый элемент в коллекцию";
    }
}
