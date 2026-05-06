package laba5.shared.actions;

import laba5.shared.model.StudyGroup;

import java.io.File;
import java.io.Serializable;

public class Request implements Serializable {
    private String name;
    private Serializable args;
    private StudyGroup studyGroup;
    public Request (String name, Serializable args, StudyGroup studyGroup){
        this.name=name;
        this.args=args;
        this.studyGroup =studyGroup;
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public Request (String name, String arg){
        this.name=name;
        this.args=arg;
    }
    public Request (String name){
        this.name=name;
    }
    public Request (){
        this("");
    }
    public String getName() {
        return name;
    }

    public Serializable getArgs() {
        return args;
    }
    public boolean isEmpty() {
        return name.isEmpty()  && args == null;
    }

    @Override
    public String toString() {
        return "Request[" + name + ", "  + args + "]";
    }
}
