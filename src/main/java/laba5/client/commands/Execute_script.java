package laba5.client.commands;

import laba5.manager.CommandInvoker;
import laba5.manager.InputManager;
import laba5.shared.model.StudyGroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
/**
 * Класс Execute_script считать и исполнить скрипт из указанного файла..
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class Execute_script implements Command {

    private final String name="execute_script";
    private final CommandInvoker commandInvoker;
    private final InputManager inputManager;
    /**
     * Конструктор команды Add.
     * @param commandInvoker менеджер управления командами
     * @param inputManager менеджер ввода
     */
    public Execute_script(CommandInvoker commandInvoker, InputManager inputManager) {
        this.commandInvoker = commandInvoker;
        this.inputManager = inputManager;
    }

    final Set<String> execute = new HashSet<>();

    /**
     * Метод execute проверяет на рекурсивность,
     * переключает сканер для InputManager если встречает команды типа Add
     * в остальном связан с CommandInvoker и выполняет все остальные команды
     * @param args
     */
    public String execute(String... args) {
        if (args.length == 1) {
            File fileScript = new File(args[0]);
            if (execute.contains(args[0])) {
                return "Рекурсивный запуск";
            } else {
                execute.add(args[0]);
                Scanner scanner0 = inputManager.getScanner();
                if (!fileScript.exists()) {
                    return "Файл не найден: " + args[0];
                }
                try (Scanner scanner1 = new Scanner(fileScript)) {
                    inputManager.setScanner(scanner1);
                    inputManager.setInScript(true);
                    while (scanner1.hasNextLine()) {
                        commandInvoker.execute(scanner1.nextLine());
                    }

                } catch (FileNotFoundException e) {
                    return "Ошибка при чтении" + e.getMessage();
                } finally {
                    inputManager.setInScript(false);
                    inputManager.setScanner(scanner0);
                    execute.remove(args[0]);
                }
                return "скрипт " + args[0] + " выполнен";

            }

        } else return "не указано имя файла";
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
        return "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }
}
