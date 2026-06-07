package laba5.server.manager;

import laba5.shared.model.*;

import java.sql.*;
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
                String fmStr = rs.getString("formofeducation");
                FormOfEducation fm = (fmStr != null) ? FormOfEducation.valueOf(fmStr) : null;
                String seme  = rs.getString("semester");
                Semester sem = (seme!= null) ? Semester.valueOf(seme) : null;
                String eye =  rs.getString("eyecolor");
                Color eyeColor = (eye != null) ? Color.valueOf(eye) : null;
                String hair =  rs.getString("haircolor");
                Color hairColor = (eye != null) ? Color.valueOf(hair) : null;
                String nat = rs.getString("nationality");
                Country natCountry = (nat != null) ? Country.valueOf(nat) : null;
                Person person = new Person(rs.getString("namegroupadmin"), rs.getString("passportid"),
                        eyeColor, hairColor, natCountry);
                StudyGroup studyGroup = new StudyGroup(id, name, coordinates, dateTime, studentsCount, shouldBeExpelled, fm, sem, person);
                studyGroups.add(studyGroup);
            }
            return studyGroups;
        }catch (Exception e){
            System.err.println("Error loading collection" + e.getMessage());
        }
        return null;
    }
    public long saveGroup(StudyGroup studyGroup, String ownerLogin){
        String sql = "INSERT INTO studygroup (name, x, y, creationdate, studentcount, shouldbeexpelled, formofeducation, semesterenum, namegroupadmin, passportid, eyecolor, haircolor, nationality, owner_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, (SELECT id FROM users WHERE login = ?))";
        try(Connection connect = databaseHandler.connect(); PreparedStatement prSt = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);){
            prSt.setString(1, studyGroup.getName());
            prSt.setInt(2, Integer.parseInt(studyGroup.getCoordinatesX()));
            prSt.setInt(3, Integer.parseInt(studyGroup.getCoordinatesY()));
            prSt.setTimestamp(4, Timestamp.from(studyGroup.getCreationDate().toInstant()));
            prSt.setInt(5, studyGroup.getStudentsCount());
            prSt.setLong(6, studyGroup.getShouldBeExpelled());
            prSt.setString(7, studyGroup.getFormOfEducation() != null ? studyGroup.getFormOfEducation().toString() : null);
            prSt.setString(8, studyGroup.getSemesterEnum() != null ? studyGroup.getSemesterEnum().toString() : null);
            prSt.setString(9, studyGroup.getGroupAdminN()!=null ? studyGroup.getGroupAdminN().toString() : null );
            prSt.setString(10, studyGroup.getGroupAdminP()!=null ? studyGroup.getGroupAdminP().toString() : null );
            prSt.setString(11, studyGroup.getGroupAdminE()!=null ? studyGroup.getGroupAdminE().toString() : null );
            prSt.setString(12, studyGroup.getGroupAdminH()!=null ? studyGroup.getGroupAdminH().toString() : null );
            prSt.setString(13, studyGroup.getGroupAdminC()!=null ? studyGroup.getGroupAdminC().toString() : null );
            prSt.setString(14, ownerLogin);
            prSt.executeUpdate();
            ResultSet gK = prSt.getGeneratedKeys();
            if(gK.next()){
                return gK.getLong(1);
            }
    }catch (Exception e){
        System.err.println("Error saving group" + e.getMessage());
        }
        return -1;
    }
    public boolean deleteStudy(long id, String ownerLogin){
       String sql =  "DELETE FROM studygroup WHERE id = ? AND owner_id = (SELECT id FROM users WHERE login = ?)";
        try(Connection connect = databaseHandler.connect(); PreparedStatement prSt = connect.prepareStatement(sql)){
           prSt.setLong(1, id);
           prSt.setString(2, ownerLogin);
           if(prSt.executeUpdate()==1){
               return true;
           }
        } catch (Exception e) {
            System.err.println("Error delete group" + e.getMessage());
        }
        return false;
    }
    public boolean updateStudy(long id, StudyGroup studyGroup, String ownerLogin){
        String sql = "UPDATE studygroup SET name = ?, x = ?, y = ?, creationdate = ?, studentcount = ?, shouldbeexpelled = ?, formofeducation = ?, semesterenum = ?, namegroupadmin = ?, passportid = ?, eyecolor = ?, haircolor = ?, nationality = ? WHERE id = ? " +
                "AND owner_id = (SELECT id FROM users WHERE login = ?)";
        try(Connection connect = databaseHandler.connect(); PreparedStatement prSt = connect.prepareStatement(sql)){
            prSt.setString(1, studyGroup.getName());
            prSt.setInt(2, Integer.parseInt(studyGroup.getCoordinatesX()));
            prSt.setInt(3, Integer.parseInt(studyGroup.getCoordinatesY()));
            prSt.setTimestamp(4, Timestamp.from(studyGroup.getCreationDate().toInstant()));
            prSt.setInt(5, studyGroup.getStudentsCount());
            prSt.setLong(6, studyGroup.getShouldBeExpelled());
            prSt.setString(7, studyGroup.getFormOfEducation() != null ? studyGroup.getFormOfEducation().toString() : null);
            prSt.setString(8, studyGroup.getSemesterEnum() != null ? studyGroup.getSemesterEnum().toString() : null);
            prSt.setString(9, studyGroup.getGroupAdminN()!=null ? studyGroup.getGroupAdminN().toString() : null );
            prSt.setString(10, studyGroup.getGroupAdminP()!=null ? studyGroup.getGroupAdminP().toString() : null );
            prSt.setString(11, studyGroup.getGroupAdminE()!=null ? studyGroup.getGroupAdminE().toString() : null );
            prSt.setString(12, studyGroup.getGroupAdminH()!=null ? studyGroup.getGroupAdminH().toString() : null );
            prSt.setString(13, studyGroup.getGroupAdminC()!=null ? studyGroup.getGroupAdminC().toString() : null );
            prSt.setInt(14, (int) studyGroup.getId());
            prSt.setString(15, ownerLogin);
            if(prSt.executeUpdate()==1){
                return true;
            }
        }catch (Exception e){
            System.err.println("Error update group" + e.getMessage());
        }
        return false;
    }
    public void clearAllGroups(String ownerLogin){
        String sql = "DELETE FROM studygroup WHERE owner_id = (SELECT id FROM users WHERE login = ?)";
        try(Connection connect = databaseHandler.connect(); PreparedStatement prSt = connect.prepareStatement(sql)){
            prSt.setString(1, ownerLogin);
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
