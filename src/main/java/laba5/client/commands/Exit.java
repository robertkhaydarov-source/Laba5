package laba5.client.commands;

import laba5.shared.actions.Request;
import laba5.shared.model.StudyGroup;

/**
 * Класс Exit завершить программу (без сохранения в файл).
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class Exit implements Command {

    private final String name="exit";
    /**
     * Выполняет команду.
     *
     * @param args аргументы команды
     */
    public String execute(String... args) {
        System.exit(0);
        return "закрытие консольного приложения";
    }
    @Override
    public String execute(Request request) {
        String arg = request.getArgs() != null ? request.getArgs().toString() : "";
        return this.execute(arg.isEmpty() ? new String[0] : new String[]{arg});
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
        return "завершить программу (без сохранения в файл)";
    }
}
