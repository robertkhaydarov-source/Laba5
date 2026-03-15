package manager;

import model.FormOfEducation;
import model.StudyGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CollectionManager {
    //здесь будет коллекция!
    private long currentId=1;
    private final ArrayList<StudyGroup> collection = new ArrayList<StudyGroup>();
    private final Date initilizationDate =new Date();
    private long id;
    public List<StudyGroup> show() {
        return  Collections.unmodifiableList(collection);
    }
    public int getSize(){
        return collection.size();
    }
    public final Date getInitilizationDate() {
        return initilizationDate;
    }
    public final String getType(){
        return collection.getClass().getName();
    }
    public void add(StudyGroup studyGroup){
        collection.add(studyGroup);
        System.out.println("элемент добавлен в коллекцию");
    }
    public void remove_by_id(long id){
        for(int i=0; i<collection.size();i++){
            if(id == collection.get(i).getId()){
                collection.remove(i);
                System.out.println("элемент с введенным id удален");
                break;
            }
        }
    }

    public long getCurrentId() {
        return currentId++;
    }
    public void updateCurrentId() {
        long maxId=0;
        for(StudyGroup st: collection){
            if (st.getId()>maxId){
                maxId = st.getId();
            }
        }
        currentId=maxId+1;
    }
    public void remove_last(){
        collection.sort(null);
        collection.remove(collection.size()-1);
    }
    public void remove_lower(StudyGroup studyGroup){
            collection.removeIf(el ->el.compareTo(studyGroup)<0);
    }
    public void add_if_max(StudyGroup studyGroup){
        StudyGroup max=Collections.max(collection);
        if (studyGroup.compareTo(max)>0){
            collection.add(studyGroup);
        }
        else {
            currentId-=1;
            System.out.println("колличество студентов меньше чем у макимального элемента коллекции, элемент добавлен не будет");
        }

    }
    public void count_by_form_of_education(String arg){
        int count=0;
        FormOfEducation target = FormOfEducation.valueOf(arg.toUpperCase());
        for(StudyGroup st:collection){
            if(st.getFormOfEducation().equals(target)){
                count++;
            }
        }
        System.out.println("колличество= " + count);
    }
    public void  filter_contains_name(String name){
        for(StudyGroup st:collection){
            if(st.getName().toLowerCase().contains(name.toLowerCase()) && name!=null){
                System.out.println(st);
            }
        }
    }
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

    public void clear(){
        collection.clear();
    }
}
