package commands;

public class Filter_contains_name implements Comand{
    private final String name="filter_contains_name";
    @Override
    public void execute(String... args) {

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getInfo() {
        return "вывести элементы, значение поля name которых содержит заданную подстроку";
    }
}
