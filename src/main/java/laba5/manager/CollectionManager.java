package laba5.manager;

import laba5.shared.model.FormOfEducation;
import laba5.shared.model.StudyGroup;

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
    public boolean add(StudyGroup studyGroup){
        for (StudyGroup st : collection)
            if(st.getId() == studyGroup.getId()){
                System.out.println("Элемент с id=" + studyGroup.getId() + " уже существует");
                return false;
            }
        collection.add(studyGroup);
        return true;
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

                f=true;
                break;
            }
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
    public boolean remove_last(){
        boolean f= false;
        if(!collection.isEmpty()){
            collection.sort(null);
            collection.remove(collection.size()-1);
            f = true;
        }
        else System.out.println("коллекция пустая");
        return f;
    }

    /**
     * Удалить из коллекции все элементы, меньшие, чем заданный
     * @param studyGroup элемент
     */
    public boolean remove_lower(StudyGroup studyGroup){
        if (!collection.isEmpty()){
            return collection.removeIf(el ->el.compareTo(studyGroup)<0);
        }
        else {
            System.out.println("коллекция пуста");
            return false;
        }
    }
    /**
     * Добавляет элемент в коллекцию,
     * если значение studentsCount больше максимального
     * значения в текущей коллекции.
     *
     * @param studyGroup элемент для добавления
     */
    public boolean add_if_max(StudyGroup studyGroup){
        boolean f = false;
        if (!collection.isEmpty()){
            StudyGroup max=Collections.max(collection);
            if (studyGroup.compareTo(max)>0){
                collection.add(studyGroup);
                f=true;
            }
            else {
                currentId-=1;
            }
        }
        else {
            collection.add(studyGroup);
        }
        return f;

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
            System.err.println("Введено не корректное значение " + e);
        }
    }

    /**
     * Фильтрацию коллекции по имени.
     * @param name имя
     */
    public boolean filter_contains_name(String name){
        boolean f = false;
        for(StudyGroup st:collection){
            if(name!=null && st.getName().toLowerCase().contains(name.toLowerCase())){
                f=true;
                System.out.println(st);
            }
        }
        return f;
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
