package laba5.server.manager;

import laba5.shared.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class CollectionDao {
    private DatabaseHandler databaseHandler;
    public CollectionDao(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }
    public List<StudyGroup> loadCollection(){
        List<StudyGroup> studyGroups = new ArrayList<>();
            String sql = "SELECT * FROM studygroup";
        try(Connection connect = databaseHandler.connect(); PreparedStatement prSt = connect.prepareStatement(sql); ResultSet rs = prSt.executeQuery()){
            while(rs.next()){
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                Coordinates coordinates = new Coordinates(rs.getLong("x"), rs.getLong("y"));
                ZonedDateTime dateTime = rs.getTimestamp("creationdate").toInstant().atZone(ZoneId.systemDefault());
                int studentsCount = rs.getInt("studentcount");
                Long shouldBeExpelled = rs.getLong("shouldbeexpelled");
                FormOfEducation fm = rs.getObject("formofeducation", FormOfEducation.class);
                Semester sem = rs.getObject("semesterenum   ", Semester.class);
                Person person = new Person(rs.getString("namegroupadmin"), rs.getString("passportid"),
                        rs.getObject("eyecolor", Color.class), rs.getObject("haircolor", Color.class),
                        rs.getObject("nationality", Country.class));
                StudyGroup studyGroup = new StudyGroup(id, name, coordinates, dateTime, studentsCount, shouldBeExpelled, fm, sem, person);
                studyGroups.add(studyGroup);
            }
            return studyGroups;
        }catch (Exception e){
            System.err.println("Error loading collection" + e.getMessage());
        }
        return null;
    }
}
