package laba5.manager;

import laba5.model.FormOfEducation;
import laba5.model.StudyGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
/**
 * Класс управления коллекцией StudyGroup.
 * Содержит методы добавления, удаления и получения информации о коллекции.
 *
 * @author Khaydarov Robert P3118
 * @version 1.0
 */
public class CollectionManager {

    private long currentId=1;
    private final ArrayList<StudyGroup> collection = new ArrayList<StudyGroup>();
    private final Date initilizationDate =new Date();
    /**
     * Возвращает неизменяемое представление коллекции.
     *
     * @return список объектов StudyGroup
     */
    public List<StudyGroup> showCollection(){
        return Collections.unmodifiableList(collection);
    }
    /**
     * @return размер коллекции
     */
    public int getSize(){
        return collection.size();
    }

    /**
     * @return возвращает время инициализауии.
     */
    public final Date getInitilizationDate() {
        return initilizationDate;
    }

    /**
     * @return возвращает тип коллекции.
     */
    public final String getType(){
        return collection.getClass().getName();
    }
    /**
     * Добавляет новый элемент в коллекцию.
     *
     * @param studyGroup объект StudyGroup, который необходимо добавить
     */
    public void add(StudyGroup studyGroup){
        collection.add(studyGroup);
    }
    /**
     * Удаляет элемент коллекции по его идентификатору.
     *
     * @param id идентификатор элемента
     */
    public boolean remove_by_id(long id){
        boolean f=false;
        for(int i = 0; i<collection.size();i++){
            if(id == collection.get(i).getId()){
                collection.remove(i);
                System.out.println("элемент с введенным id удален");
                f=true;
                break;
            }
        }
        if (!f){
            System.out.println("элемента с введенным id нет");

        }
        return f;
    }

    /**
     * @return увеличивает счетчик id.
     */
    public long getCurrentId() {
        return currentId++;
    }

    /**
     * Проверка на id, чтобы продолжать с максимального значения
     */
    public void updateCurrentId() {
        long maxId=0;
        for(StudyGroup st: collection){
            if (st.getId()>maxId){
                maxId = st.getId();
            }
        }
        currentId=maxId+1;
    }
    /**
     * Удаляет последний элемент коллекции.
     * Если коллекция пустая, операция не выполняется.
     */
    public void remove_last(){
        if(!collection.isEmpty()){
            collection.sort(null);
            collection.remove(collection.size()-1);
        }
        else System.out.println("коллекция пустая");

    }

    /**
     * Удалить из коллекции все элементы, меньшие, чем заданный
     * @param studyGroup элемент
     */
    public void remove_lower(StudyGroup studyGroup){
        if (!collection.isEmpty()){
            collection.removeIf(el ->el.compareTo(studyGroup)<0);
            System.out.println("элементы удалены");
        }
        else System.out.println("коллекция пуста");

    }
    /**
     * Добавляет элемент в коллекцию,
     * если значение studentsCount больше максимального
     * значения в текущей коллекции.
     *
     * @param studyGroup элемент для добавления
     */
    public void add_if_max(StudyGroup studyGroup){
        if (!collection.isEmpty()){
            StudyGroup max=Collections.max(collection);
            if (studyGroup.compareTo(max)>0){
                collection.add(studyGroup);
            }
            else {
                currentId-=1;
                System.out.println("количество студентов меньше или равно чем у максимального элемента коллекции, элемент добавлен не будет");
            }
        }
        else collection.add(studyGroup);


    }

    /**
     * Вывести количество элементов, значение поля formOfEducation которых равно заданному
     * @param arg аргументы
     */
    public void count_by_form_of_education(String arg){
        try {
            int count=0;
            FormOfEducation target = FormOfEducation.valueOf(arg.toUpperCase());
            for(StudyGroup st:collection){
                if(st.getFormOfEducation().equals(target)){
                    count++;
                }
            }
            System.out.println("количество = " + count);
        } catch (IllegalArgumentException e) {
            System.err.println("Введено не корректное значение" + e);
        }

    }

    /**
     * Фильтрацию коллекции по имени.
     * @param name имя
     */
    public void  filter_contains_name(String name){
        boolean c =true;
        for(StudyGroup st:collection){
            if(name!=null && st.getName().toLowerCase().contains(name.toLowerCase())){
                c=false;
                System.out.println(st);
            }
        }
        if(c) System.out.println("таких элементов нет");
    }

    /**
     * Сортировка и вывод всех полей ShouldBeExpelled в порядке возрастания
     */
    public void print_field_ascending_should_be_expelled(){
        ArrayList<Long> sorted = new ArrayList<>();
        for(StudyGroup st:collection){
            if (st.getShouldBeExpelled()!=null){
                sorted.add(st.getShouldBeExpelled());
            }
        }
        sorted.sort(null);
        for(long st: sorted){
            System.out.println(st);
        }

    }
    /**
     * Полностью очищает коллекцию.
     */
    public void clear(){
        collection.clear();
    }
}
