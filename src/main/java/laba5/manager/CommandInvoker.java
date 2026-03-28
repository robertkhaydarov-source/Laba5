package laba5.manager;

import laba5.commands.Command;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
/**
 * Класс CommandInvoker отвечает за выполнение программ и их регистрацию.
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class CommandInvoker  {

    private final Map<String, Command> comandMap =new HashMap<>();
    public void execute(String comand)
    {
        String[] arr = comand.trim().split("\\s+");
        String[] arg = Arrays.copyOfRange(arr, 1, arr.length);
        if (comandMap.containsKey(arr[0])) comandMap.get(arr[0]).execute(arg);
        else System.out.println("такой команды не существует");

    }

    /**
     * Добавляет команды в список команд.
     * @param comand имя команды
     */
    public void register(Command comand){
        comandMap.put(comand.getName(), comand);
    }

    /**
     * @return возвращает команды из списка добавленных.
     */
    public Collection<Command> getComands(){
        return comandMap.values();
    }

}
