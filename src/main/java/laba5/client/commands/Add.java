package laba5.client.commands;

import laba5.server.manager.CollectionManager;
import laba5.server.manager.FileCsvReader;
import laba5.server.manager.InputManager;
import laba5.server.manager.StudyGroupFactory;
import laba5.shared.model.StudyGroup;

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
    private final FileCsvReader fileCsvReader;
    /**
     * Конструктор команды Add.
     *
     * @param collectionManager менеджер коллекции
     * @param inputManager менеджер ввода
     * @param studyGroupFactory фабрика объектов StudyGroup
     */
    public Add(CollectionManager collectionManager, InputManager inputManager, StudyGroupFactory studyGroupFactory, FileCsvReader fileCsvReader) {
        this.collectionManager = collectionManager;
        this.inputManager=inputManager;
        this.studyGroupFactory = studyGroupFactory;
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
            else if (inputManager.isInScript()) {
                if (args.length != 1) {
                    return "неверное количество аргументов для скрипта";
                }
                String[] parsedArgs = fileCsvReader.parsingCSV(args[0]);
                if (parsedArgs == null) {
                    return "Ошибка аргументов";
                }
                studyGroup = studyGroupFactory.createFromConsole(ZonedDateTime.now(), parsedArgs);
            }
            else if (args.length == 0) {
                studyGroup = studyGroupFactory.createFromConsole(ZonedDateTime.now(), inputManager.consoleArgs());

            }
            else {
                studyGroup = null;
            }
            if (studyGroup != null) {
                if (collectionManager.add(studyGroup)) {
                    return "элемент добавлен в коллекцию";
                }
            }
            else {
                return "неверное количество аргументов";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        return "";
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
        return "добавить новый элемент в коллекцию";
    }
}
