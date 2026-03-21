package commands;

import manager.CommandInvoker;
/**
 * Класс Help вывести элементы, значение поля name которых содержит заданную подстроку.
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class Help implements Comand {

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
    public void execute(String... args) {
        for(Comand comand: invoker.getComands()){
            System.out.println(comand.getName() + ": " + comand.getInfo());
        }
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
