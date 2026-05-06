package laba5.shared.model;

import java.io.Serializable;
import java.time.ZonedDateTime;
/**
 * Класс представляет учебную группу.
 * Содержит информацию о названии группы,
 * координатах, количестве студентов и администраторе.
 * Реализует интерфейс Comparable для сортировки объектов.
 */
public class StudyGroup implements Comparable<StudyGroup>, Serializable {
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
    public String validate(){
        if(id<=0) return "id должен быть больше 0";
        if(name==null || name.isEmpty()) return "имя не может быть пустым";
        if(coordinates==null || !coordinates.validate().equals("validate")) return coordinates.validate();
        if(creationDate==null) return "время создания не может быть нулевым";
        if(studentsCount<=0) return "количество студентов должно быть больше 0";
        if(shouldBeExpelled<=0) return "количество студентов на отчисление должно быть больше 0";
        if(groupAdmin==null || !groupAdmin.validate().equals("validate")) return groupAdmin.validate();
        return "validate";
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
    /**
     * Возвращает строковое представление объекта StudyGroup.
     *
     * @return строка с информацией о группе
     */
    @Override
    public String toString() {
        return " id = " + id + "\n" +
                " name = " + name + "\n" +
                " coordinates = (" + coordinates.getX() + ", " + coordinates.getY() + ")\n" +
                " creationDate = " + creationDate + "\n" +
                " studentsCount = " + studentsCount + "\n" +
                " shouldBeExpelled = " + shouldBeExpelled + "\n" +
                " formOfEducation = " + formOfEducation + "\n" +
                " semester = " + semesterEnum + "\n" +
                " groupAdmin:\n" +
                "   name = " + groupAdmin.getName() + "\n" +
                "   passportID = " + groupAdmin.getPassportID() + "\n" +
                "   eyeColor = " + groupAdmin.getEyeColor() + "\n" +
                "   hairColor = " + groupAdmin.getHairColor() + "\n" +
                "   nationality = " + groupAdmin.getNationality() + "\n";
    }
    /**
     * Сравнивает текущий объект StudyGroup с другим.
     *
     * @param other другой объект StudyGroup
     * @return отрицательное число, если текущий объект меньше,
     *         ноль если равны,
     *         положительное число если больше
     */
    @Override
    public int compareTo(StudyGroup other) {
        return Long.compare(this.studentsCount, other.studentsCount);
    }
}
