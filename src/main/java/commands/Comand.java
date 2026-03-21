package commands;

/**
 * интерфейс Comand
 * выделяет общие методы для всех команд: execute, getName, getInfo.
 */
public interface Comand {

   void execute(String... args);
   String getName();
   String getInfo();
}
