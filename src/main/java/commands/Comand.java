package commands;

public interface Comand {
   void execute(String... args);
   String getName();
   String getInfo();
}
