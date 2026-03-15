package commands;

import manager.CommandInvoker;

public class Help implements Comand {

    private String name = "help";
    private final CommandInvoker invoker;

    public Help(CommandInvoker invoker) {
        this.invoker = invoker;
    }


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
