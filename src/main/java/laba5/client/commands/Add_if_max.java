package laba5.client.commands;

import laba5.server.manager.CollectionManager;
import laba5.server.manager.FileCsvReader;
import laba5.server.manager.InputManager;
import laba5.server.manager.StudyGroupFactory;
import laba5.shared.model.StudyGroup;

import java.time.ZonedDateTime;
/**
 * Класс Add_if_max добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции.
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class Add_if_max implements Command {

    private final String name="add_if_max";
    private final CollectionManager collectionManager;
    private final StudyGroupFactory studyGroupFactory;
    private final InputManager inputManager;
    private final FileCsvReader fileCsvReader;
    /**
     * Конструктор команды Add_if_max.
     *
     * @param collectionManager менеджер коллекции
     * @param inputManager менеджер ввода
     * @param studyGroupFactory фабрика объектов StudyGroup
     */
    public Add_if_max(CollectionManager collectionManager, StudyGroupFactory studyGroupFactory, InputManager inputManager, FileCsvReader fileCsvReader) {
        this.collectionManager = collectionManager;
        this.studyGroupFactory = studyGroupFactory;
        this.inputManager = inputManager;
        this.fileCsvReader = fileCsvReader;
    }

    /**
     * Выполняет команду.
     *
     * @param args аргументы команды
     */
    public String execute(String... args) {
        StudyGroup studyGroup;
        try {
            if (args.length == 12) {
                studyGroup = studyGroupFactory.createFromConsole(ZonedDateTime.now(), args);
            }
            else if (inputManager.isInScript()){
                if (args.length != 1){
                    return "неверное количество аргументов для скрипта";
                }
                String[] parsedArgs = fileCsvReader.parsingCSV(args[0]);
                if (parsedArgs == null) {
                    return "Ошибка аргументов";
                }
                studyGroup = studyGroupFactory.createFromConsole(ZonedDateTime.now(), parsedArgs);
            }
            else if (args.length == 0){
                studyGroup = studyGroupFactory.createFromConsole(ZonedDateTime.now(), inputManager.consoleArgs());
            }
            else {
                studyGroup = null;
            }
        boolean added =collectionManager.add_if_max(studyGroup);
        if (studyGroup!=null) {
            if (!added) {
                return "элемент не добавлен (не максимальный)";
            } else {
                return "элемент добавлен, условие выполнено";
            }
        }
        else {
            return "неверное количество аргументов";
        }
        }catch (IllegalArgumentException e){
            return e.getMessage();
        }
    }

    @Override
    public String execute(String args, StudyGroup studyGroup) {
        return "";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getInfo() {
        return "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }
}
