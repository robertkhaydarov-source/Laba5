package laba5.server.manager;

import laba5.client.commands.Command;
import laba5.shared.actions.Request;
import laba5.shared.model.StudyGroup;

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
    private final InputManager inputManager;
    private final Map<String, Command> comandMap =new HashMap<>();

    public CommandInvoker(InputManager inputManager) {
        this.inputManager = inputManager;
    }


    public String execute(String commandWithArgs, String currentUsername, String passwordHash) {
        String[] parts = commandWithArgs.trim().split(" ", 2);
        String name = parts[0];
        String args = (parts.length > 1) ? parts[1] : "";

        Request artificialRequest = new Request(name, args);
        artificialRequest.setUserName(currentUsername);
        artificialRequest.setPassword(passwordHash);
        return this.execute(artificialRequest);
    }
    public String execute(Request request){
        String commandName = request.getName();
        if (comandMap.containsKey(commandName)) {
            // Теперь передаем РЕКВЕСТ целиком! Логин и пароль не потеряются!
            return comandMap.get(commandName).execute(request);
        }
        else return "Такой команды не существует или неверные данные";
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
