package laba5.server.manager;

import laba5.shared.model.FormOfEducation;
import laba5.shared.model.StudyGroup;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;

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
        List<StudyGroup> colection1 = Collections.unmodifiableList(collection);
        return colection1.stream().sorted(Comparator.comparing(StudyGroup::getName)).collect(Collectors.toList());
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
    public String add(StudyGroup studyGroup){
        boolean exists = collection.stream().anyMatch(st -> st.getId()==studyGroup.getId());
        if(exists){
            return "Элемент с id=" + studyGroup.getId() + " уже существует";
        }
        collection.add(studyGroup);
        return "Элемент добавлен";
    }
    /**
     * Удаляет элемент коллекции по его идентификатору.
     *
     * @param id идентификатор элемента
     */
    public boolean remove_by_id(long id){
            return collection.remove(collection.stream().filter(st -> st.getId()==id).findFirst().get());
    }

    /**
     * @return увеличивает счетчик id.
     */
    public long getCurrentId() {
        return currentId++;
    }

    public void setCurrentId(long currentId) {
        this.currentId = currentId;
    }

    /**
     * Проверка на id, чтобы продолжать с максимального значения
     */
    public void updateCurrentId() {
        if(!collection.isEmpty()){
            long maxId=collection.stream().max(Comparator.comparingLong(StudyGroup::getId)).get().getId();
            currentId=maxId+1;
        }
    }
    /**
     * Удаляет последний элемент коллекции.
     * Если коллекция пустая, операция не выполняется.
     */
    public boolean remove_last() {
        if (collection.isEmpty()) {
            return false;
        }
        return collection.stream()
                .sorted()
                .reduce((a, b) -> b)
                .map(last -> collection.remove(last))
                .orElse(false);
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
            StudyGroup max=collection.stream().max(Comparator.naturalOrder()).get();
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
    public String count_by_form_of_education(String arg){
        String line;
        try {
            FormOfEducation target = FormOfEducation.valueOf(arg.toUpperCase());
            long count = collection.stream().filter(st->st.getFormOfEducation()==target).count();
            line = "количество = " + count;
        } catch (IllegalArgumentException e) {
            line="Введено не корректное значение " + e;
        }
        return line;
    }

    /**
     * Фильтрацию коллекции по имени.
     * @param name имя
     */
    public String filter_contains_name(String name) {
        StringBuilder result = new StringBuilder();
        List<StudyGroup> studyGroups = collection.stream().filter(st -> st.getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
        if(!studyGroups.isEmpty()){
            for (StudyGroup studyGroup : studyGroups) {
                result.append(studyGroup.getName());
            }
            return result.toString();
        }
        return "нет совпадений";
    }

    /**
     * Сортировка и вывод всех полей ShouldBeExpelled в порядке возрастания
     */
    public String print_field_ascending_should_be_expelled(){
        StringBuilder stringBuilder = new StringBuilder();
        List<Long> sbe = collection.stream().filter(st->st.getShouldBeExpelled()!=null).map(StudyGroup::getShouldBeExpelled).sorted().collect(Collectors.toList());
        for(long st: sbe){
            stringBuilder.append(st);
        }
        return stringBuilder.toString();
    }
    /**
     * Полностью очищает коллекцию.
     */
    public void clear(){
        collection.clear();
    }
}
