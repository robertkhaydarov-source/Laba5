package commands;

public class Exit implements Comand{
    private final String name="exit";
    public void execute(String... args) {
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
