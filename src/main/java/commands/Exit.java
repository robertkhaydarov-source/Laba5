package commands;
/**
 * Класс Exit завершить программу (без сохранения в файл).
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class Exit implements Comand{

    private final String name="exit";
    /**
     * Выполняет команду.
     *
     * @param args аргументы команды
     */
    public void execute(String... args) {
        System.out.println("закрытие консольного приложения ...");
        System.exit(0);
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
