package laba5.model;
/**
 * Класс Person содержит описывающую информацию об админе группы.
 */

public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String passportID; //Длина строки не должна быть больше 48, Поле может быть null
    private Color eyeColor; //Поле не может быть null
    private Color hairColor; //Поле не может быть null
    private Country nationality; //Поле не может быть null
    public Person(String name, String passportID, Color eyeColor, Color hairColor, Country nationality){

        this.eyeColor=eyeColor;
        this.name=name;
        this.hairColor=hairColor;
        this.passportID=passportID;
        this.nationality=nationality;

    }
    @Override
    public String toString(){
        return "("+name  + ",  "+ eyeColor  + ",  " + hairColor + ",  " + passportID  + ",  " + nationality +")";
    }

    public String getName() {
        return name;
    }
    public String validate(){
            if(name==null || name.isEmpty()) return "имя не может быть пустым";
            if(passportID.length()>48) return "паспортные данные должны быть меньше 48";
            if(eyeColor==null|| hairColor==null || nationality==null)return "цвета и национальность не могут быть пустыми";
            return "validate";
    }
    public Color getEyeColor() {
        return eyeColor;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public String getPassportID() {
        return passportID;
    }

    public Country getNationality() {
        return nationality;
    }
}
