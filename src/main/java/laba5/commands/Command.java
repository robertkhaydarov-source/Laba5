package laba5.commands;

/**
 * интерфейс Comand
 * выделяет общие методы для всех команд: execute, getName, getInfo.
 */
public interface Command {

   void execute(String... args);
   String getName();
   String getInfo();
}
