package manager;

import commands.Comand;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CommandInvoker  {
    private final Map<String, Comand> comandMap =new HashMap<>();
    public void execute(String comand)
    {
        String[] arr = comand.split(" ");
        String[] arg = Arrays.copyOfRange(arr, 1, arr.length);
        if (comandMap.containsKey(arr[0])) comandMap.get(arr[0]).execute(arg);
        else System.out.println("такой команды не существует");
    }
    //прописать как каждое имя регистрация сущностей для хелп
    public void register(Comand comand){
        comandMap.put(comand.getName(), comand);
    }
    public Collection<Comand> getComands(){
        return comandMap.values();
    }

}
