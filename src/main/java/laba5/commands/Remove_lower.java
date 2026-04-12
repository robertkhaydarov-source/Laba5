package laba5.commands;

import laba5.manager.CollectionManager;
import laba5.manager.FileCsvReader;
import laba5.manager.InputManager;
import laba5.manager.StudyGroupFactory;
import laba5.model.StudyGroup;

import java.time.ZonedDateTime;
/**
 * Класс Remove_lower удалить из коллекции все элементы, меньшие, чем заданный.
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class Remove_lower implements Command {

    private final String name="remove_lower";
    private final CollectionManager collectionManager;
    private final InputManager inputManager;
    private final StudyGroupFactory studyGroupFactory;
    private final FileCsvReader fileCsvReader;
    /**
     * Конструктор команды Remove_by_id.
     * @param inputManager менеджер ввода
     * @param collectionManager менеджер коллекции
     * @param studyGroupFactory фабрика объектов StudyGroup
     */
    public Remove_lower(CollectionManager collectionManager, InputManager inputManager, StudyGroupFactory studyGroupFactory, FileCsvReader fileCsvReader) {
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
        this.studyGroupFactory = studyGroupFactory;
        this.fileCsvReader = fileCsvReader;
    }
    /**
     * Выполняет команду.
     *
     * @param args аргументы команды
     */
    public void execute(String... args) {
        StudyGroup studyGroup;
        if (args.length == 12) {
             studyGroup = studyGroupFactory.createFromConsole(ZonedDateTime.now(), args);
        }
        else if (inputManager.isInScript()) {
            if(args.length!=2){
                System.out.println("неверное количество аргументов для скрипта");
                return;
            }
            String[] parsedArgs = fileCsvReader.parsingCSV(args[0]);
            if (parsedArgs == null) {
                System.out.println("Ошибка аргументов");
                return;
            }
            studyGroup = studyGroupFactory.createFromConsole(ZonedDateTime.now(), parsedArgs);
        }
        else if (args.length==0){
             studyGroup = studyGroupFactory.createFromConsole(ZonedDateTime.now(), inputManager.consoleArgs());
        }
        else {
            System.out.println("неверное количество аргументов");
            studyGroup=null;
        }
        if(studyGroup!=null){
            if(collectionManager.remove_lower(studyGroup)){
                System.out.println("элементы удалены");
            }
            else System.out.println("элементы не удалены");
        }
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getInfo() {
        return "удалить из коллекции все элементы, меньшие, чем заданный";
    }
}
