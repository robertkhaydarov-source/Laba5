package laba5.client.commands;

import laba5.server.manager.CollectionManager;
import laba5.server.manager.FileCsvReader;
import laba5.server.manager.InputManager;
import laba5.server.manager.StudyGroupFactory;
import laba5.shared.model.StudyGroup;

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
    private final FileCsvReader fileCsvReader;

    /**
     * Ищет элемент по id удаляет его и создает новый
     * @param args аргументы введенные после имени команды
     */
    public String execute(String... args) {
        try {
            if (args.length == 0) {
                return "не введен id";
            }
            long id_update = Long.parseLong(args[0]);
            if (!collectionManager.remove_by_id(id_update)) {
                return "элемент не найден";
            }
            StudyGroup studyGroup;
            if (inputManager.isInScript()) {
                if (args.length != 2) {
                    return "неверное количество аргументов для скрипта";
                }
                String[] parsedArgs = fileCsvReader.parsingCSV(args[1]);
                if (parsedArgs == null) {
                    return "Ошибка аргументов";
                }
                studyGroup = studyGroupFactory.createFromConsole(ZonedDateTime.now(), parsedArgs);
            }
            else if (args.length == 13) {
                List<String> list = new ArrayList<>(Arrays.asList(args));
                list.remove(0);
                String[] newArgs = list.toArray(new String[0]);
                studyGroup = studyGroupFactory.createFromConsole(ZonedDateTime.now(), newArgs);
                }
            else if (args.length == 1) {
                studyGroup = studyGroupFactory.createFromConsole(ZonedDateTime.now(), inputManager.consoleArgs());
            }
            else {
                studyGroup=null;

            }
            if (studyGroup != null) {
                collectionManager.add(studyGroup);
                return "элемент обновлен";
            }
            return "неверное количество аргументов";
        } catch (NumberFormatException e) {
            return "Ошибка при вводе id: " + e.getMessage();
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
    public Update(InputManager inputManager, StudyGroupFactory studyGroupFactory, FileCsvReader fileCsvReader, CollectionManager collectionManager) {
        this.inputManager = inputManager;
        this.studyGroupFactory = studyGroupFactory;
        this.fileCsvReader = fileCsvReader;
        this.collectionManager = collectionManager;


    }
}
