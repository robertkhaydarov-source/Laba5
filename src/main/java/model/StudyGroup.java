package model;

import java.time.ZonedDateTime;

public class StudyGroup implements Comparable<StudyGroup> {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно 0быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int studentsCount; //Значение поля должно быть больше 0
    private Long shouldBeExpelled; //Значение поля должно быть больше 0, Поле может быть null
    private FormOfEducation formOfEducation; //Поле может быть null
    private Semester semesterEnum; //Поле может быть null
    private Person groupAdmin; //Поле не может быть null
    public StudyGroup (long id, String name, Coordinates coordinates, ZonedDateTime creationDate, int studentsCount, Long shouldBeExpelled, FormOfEducation formOfEducation, Semester semesterEnum, Person groupAdmin){

        this.id=id;
        this.name=name;
        this.coordinates=coordinates;
        this.creationDate=creationDate;
        this.studentsCount=studentsCount;
        this.shouldBeExpelled=shouldBeExpelled;
        this.formOfEducation=formOfEducation;
        this.semesterEnum=semesterEnum;
        this.groupAdmin=groupAdmin;

    }
    public boolean validate(){
        if(id<=0) return false;
        if(name==null || name.isEmpty()) return false;
        if(!coordinates.validate() || coordinates==null) return false;
        if(creationDate==null) return false;
        if(studentsCount<=0) return false;
        if(shouldBeExpelled<=0) return false;
        if(groupAdmin==null || !groupAdmin.validate()) return false;
        return true;
    }
    public long getId(){
            return id;
    }

    public String getName() {
        return name;
    }

    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public String getCoordinatesX() {
        return coordinates.getX();
    }

    public int getStudentsCount() {
        return studentsCount;
    }

    public String getCoordinatesY() {
        return coordinates.getY();
    }

    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    public Color getGroupAdminH() {
        return groupAdmin.getHairColor();
    }

    public Color getGroupAdminE() {
        return groupAdmin.getEyeColor();
    }

    public String getGroupAdminN() {
        return groupAdmin.getName();
    }

    public String getGroupAdminP() {
        return groupAdmin.getPassportID();
    }
    public Country getGroupAdminC() {
        return groupAdmin.getNationality();
    }

    public Long getShouldBeExpelled() {
        return shouldBeExpelled;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }
    public String toString(){
        return id + ",  " + name + ",  " + coordinates+ ",  " + creationDate + ",  " + studentsCount+ ",  " +shouldBeExpelled+ ",  " +formOfEducation + ",  " + semesterEnum + ",  " + groupAdmin;
    }
    @Override
    public int compareTo(StudyGroup other) {
        return Long.compare(this.studentsCount, other.studentsCount);
    }
}
