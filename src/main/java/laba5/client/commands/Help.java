package laba5.client.commands;

import laba5.server.manager.CommandInvoker;
import laba5.shared.model.StudyGroup;

/**
 * Класс Help вывести элементы, значение поля name которых содержит заданную подстроку.
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class Help implements Command {

    private String name = "help";
    private final CommandInvoker invoker;
    /**
     * Конструктор команды Help.
     * @param invoker менеджер управления командами
     */
    public Help(CommandInvoker invoker) {
        this.invoker = invoker;
    }

    /**
     * Выполняет команду.
     *
     * @param args аргументы команды
     */
    public String execute(String... args) {
        String line = "";
        for(Command comand: invoker.getComands()){
            line += comand.getName() + ": " + comand.getInfo() + '\n';
        }
        return line;
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
    public String getInfo(){
        return "вывести справку по доступным командам";
    }

}
