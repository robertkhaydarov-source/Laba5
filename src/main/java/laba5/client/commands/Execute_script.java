package laba5.client.commands;

import laba5.server.manager.CommandInvoker;
import laba5.server.manager.InputManager;
import laba5.shared.actions.Request;
import laba5.shared.model.StudyGroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Класс Execute_script считать и исполнить скрипт из указанного файла.
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class Execute_script implements Command {

    private final String name = "execute_script";
    private final CommandInvoker commandInvoker;
    private final InputManager inputManager;
    private final Set<String> execute = new HashSet<>();

    public Execute_script(CommandInvoker commandInvoker, InputManager inputManager) {
        this.commandInvoker = commandInvoker;
        this.inputManager = inputManager;
    }

    // Основной метод, который будет вызываться сервером
    public String execute(Request request) {
        if (request.getArgs() == null || request.getArgs().toString().isEmpty()) {
            return "Не указано имя файла скрипта";
        }

        String fileName = request.getArgs().toString().trim();
        File fileScript = new File(fileName);

        if (execute.contains(fileName)) {
            return "Обнаружен рекурсивный запуск скрипта: " + fileName;
        }

        execute.add(fileName);
        Scanner scanner0 = inputManager.getScanner();

        if (!fileScript.exists()) {
            execute.remove(fileName);
            return "Файл не найден: " + fileName;
        }

        StringBuilder scriptResult = new StringBuilder("Выполнение скрипта " + fileName + ":\n");

        try (Scanner scanner1 = new Scanner(fileScript)) {
            inputManager.setScanner(scanner1);
            inputManager.setInScript(true);

            while (scanner1.hasNextLine()) {
                String line = scanner1.nextLine().trim();
                if (line.isEmpty()) continue;

                // Вызываем твой метод-обертку, передавая строку команды, логин и хэш пароля
                String cmdResult = commandInvoker.execute(line, request.getUserName(), request.getPassword());
                scriptResult.append("> ").append(line).append(": ").append(cmdResult).append("\n");
            }

        } catch (FileNotFoundException e) {
            return "Ошибка при чтении файла скрипта: " + e.getMessage();
        } finally {
            inputManager.setInScript(false);
            inputManager.setScanner(scanner0);
            execute.remove(fileName);
        }

        return scriptResult.toString();
    }

    @Override
    public String execute(String... args) {
        return "Ошибка: на сервере должен вызываться метод с Request";
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
        return "считать и исполнить скрипт из указанного файла.";
    }
}