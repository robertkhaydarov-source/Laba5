package commands;

import manager.CommandInvoker;
import manager.InputManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Execute_script implements Comand{
    private final String name="execute_script";
    private final CommandInvoker commandInvoker;
    private final InputManager inputManager;

    public Execute_script(CommandInvoker commandInvoker, InputManager inputManager) {
        this.commandInvoker = commandInvoker;
        this.inputManager = inputManager;
    }

    public void execute(String... args) {
        if (args.length == 1) {
            File fileScript = new File(args[0]);
            try (Scanner scanner1 = new Scanner(fileScript)) {
                Scanner scanner0 = inputManager.getScanner();
                inputManager.setScanner(scanner1);
                while (scanner1.hasNextLine()) {
                    commandInvoker.execute(scanner1.nextLine());
                    inputManager.setR(false);
                }
                inputManager.setScanner(scanner0);
                inputManager.setR(true);
            } catch (FileNotFoundException e) {
                System.err.println("Ошибка при чтении" + e.getMessage());
            }
            System.out.println("скрипт выполнен");
        } else System.out.println("не указано имя файла");
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
