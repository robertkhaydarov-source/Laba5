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
    public boolean validate(){
            if(name==null || name.isEmpty()) return false;
            if(passportID.length()>48) return false;
            if(eyeColor==null|| hairColor==null || nationality==null)return false;
            return true;
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
