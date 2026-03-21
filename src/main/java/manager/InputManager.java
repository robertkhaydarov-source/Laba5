package manager;

import model.*;

import java.util.ArrayList;
import java.util.Scanner;
/**
 * Класс InputManager отвечает за чтения данных из консоли или из скрипта.
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class InputManager {

    private Scanner scanner;
    private boolean r=true;
    public InputManager(Scanner scanner){
        this.scanner=scanner;
    }

    /**
     * Интерактивный ввод, парсинг каждого значения, проверка на то откуда идут значения,
     * если из скрипта то приглашений к вводу нет
     * @return массив StudyGroup
     */
    public String[] consoleArgs(){
        ArrayList<String> arg = new ArrayList<>();
        if (r) {System.out.println("Введите name:");}
        while (true){
            String name=scanner.nextLine();
            if(name.isBlank()){
                if(r)System.out.println("имя не может быть пустым");
                continue;
            }
            arg.add(name);
            break;
        }
        if (r) {System.out.println("Введите координату x:");}
        while (true){
            String input = scanner.nextLine();
            try {
                Long.parseLong(input);
                arg.add(input);
                break;
            } catch (NumberFormatException e) {
                if(r)System.out.println("неверный формат координаты x");
            }
        }
        if (r) {System.out.println("Введите координату y:");}
        while (true){
            String input = scanner.nextLine();
            try {
                long y=Long.parseLong(input);
                if (y<=-728){
                    if(r)System.out.println("неверный диапазон координаты y");
                    continue;
                }
                arg.add(input);
                break;
            } catch (NumberFormatException e) {
                if(r)System.out.println("неверный формат координаты y");
            }
        }
        if (r) {System.out.println("Введите количество студентов:");}
        readInt(arg);
        if (r) {System.out.println("Введите количество студентов в списке на отчисление :");}
        readInt(arg);
        if (r) {
            System.out.println("Введите одну из форм обучения:" +'\n'+
                    "1 - DISTANCE_EDUCATION," + '\n'+
                    "2 - FULL_TIME_EDUCATION," + '\n'+
                    "3 - EVENING_CLASSES");
        }
        readEnum(FormOfEducation.class, arg);
        if (r) {
            System.out.println("Введите семестр:" + '\n' +
                    "1 - THIRD," + '\n' +
                    "2 - FOURTH," + '\n' +
                    "3 - FIFTH," + '\n' +
                    "4 - SEVENTH");
        }
        readEnum(Semester.class, arg);
        if (r) {System.out.println("Введите имя админа: ");}
        while (true){
            String name=scanner.nextLine();
            if(name.isBlank()){
                if(r)System.out.println("имя не может быть пустым");
                continue;
            }
            arg.add(name);
            break;
        }
        if (r) {System.out.println("Введите паспортные данные админа группы: ");}
        while (true){
            String pasportId=scanner.nextLine();
            if(pasportId.length()>48 ){
                if(r)System.out.println("не валидное значение");
                continue;
            }
            arg.add(pasportId);
            break;
        }
        if (r) {System.out.println("Введите цвет глаз админа:" +'\n'+
                "1 - GREEN" +'\n'+
                "2 - RED," + '\n'+
                "3 - BLACK," + '\n'+
                "4 - ORANGE," +'\n'+
                "5 - WHITE");}
        readEnum(Color.class, arg);
        if (r) {System.out.println("Введите цвет волос админа:" +'\n'+
                        "1 - GREEN" +'\n'+
                        "2 - RED," + '\n'+
                        "3 - BLACK," + '\n'+
                        "4 - ORANGE," +'\n'+
                        "5 - WHITE");}
        readEnum(Color.class, arg);
        if (r) {System.out.println("Введите национальность админа:" +'\n' +
                    "1 - RUSSIA," +'\n' +
                    "2 - GERMANY," +'\n' +
                    "3 - SPAIN," +'\n' +
                    "4 - ITALY," +'\n' +
                    "5 - JAPAN");}
        readEnum(Country.class, arg);
        String[] args = arg.toArray(new String[0]);
        return args;
        }

    /**
     * Смена сканера для команды execute_script.
      * @param scanner сканнер
     */
    public void setScanner(Scanner scanner) {
        this.scanner=scanner;
    }
    /**
     * Считывает целое число из консоли и проверяет его корректность.
     *
     * @param arg список аргументов
     */
    public void readInt(ArrayList<String> arg){
       while (true){
           String string=scanner.nextLine();
           try {
               int value=Integer.parseInt(string);
               if(value <=0){
                   System.out.println("не валидное значение");
                   continue;
               }
               arg.add(string);
               break;

           } catch (NumberFormatException e) {
              System.out.println("неверный формат");
           }
       }
    }

    /**
     * Универсальный парсинг Enum классов, а также возможность писать число вместо строки.
     * @param enumclass Enum класс
     * @param arg аргументы
     */
    public void readEnum(Class<? extends Enum> enumclass, ArrayList<String> arg) {
        while (true) {
            String string = scanner.nextLine();
            try {
                int value = Integer.parseInt(string);
                Object[] enums=enumclass.getEnumConstants();
                if (value < 1 || value > enums.length) {
                    System.out.println("не валидное значение");
                    continue;
                }
                arg.add(String.valueOf(enums[value-1]));
                break;
            } catch (NumberFormatException e) {
                try {
                    Enum<?> constant = Enum.valueOf((Class<Enum>)enumclass, string.toUpperCase());
                    arg.add(String.valueOf(constant));
                    break;
                } catch (IllegalArgumentException r) {
                    System.out.println("неверный формат");
                }
            }
        }
    }

    /**
     * Смена значения в зависимости от того выполняется ли скрпит.
     * @param r флаг
     */
    public void setR(boolean r) {
        this.r = r;
    }

    /**
     * @return возвращает текущий сканер.
     */
    public Scanner getScanner() {
        return scanner;
    }
}
