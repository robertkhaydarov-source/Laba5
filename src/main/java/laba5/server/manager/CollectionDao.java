package laba5.server.manager;

import laba5.shared.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;



public class CollectionDao {
    private static final Logger log = LoggerFactory.getLogger(CollectionDao.class);
    private DatabaseHandler databaseHandler;
    public CollectionDao(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }
    public List<StudyGroup> loadCollection(){
        List<StudyGroup> studyGroups = new ArrayList<>();
            String sql = "SELECT studygroup.*, users.login AS owner_username " +
                "FROM studygroup " +
                "INNER JOIN users ON studygroup.owner_id = users.id";
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
                String seme  = rs.getString("semesterenum");
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
                studyGroup.setOwnerLogin(rs.getString("owner_username"));
                studyGroups.add(studyGroup);
            }
            return studyGroups;
        }catch (Exception e){
            System.err.println("Error loading collection" + e.getMessage());
        }
        return null;
    }
    public long saveGroup(StudyGroup studyGroup, String ownerLogin, String password) {
        long userId = -1;

        // Шаг 1: Явно достаем ID пользователя
        String userSql = "SELECT id FROM users WHERE login = ? AND password = ?";
        try (Connection connect = databaseHandler.connect();
             PreparedStatement userPs = connect.prepareStatement(userSql)) {

            userPs.setString(1, ownerLogin.trim());
            userPs.setString(2, password.trim());

            try (ResultSet rs = userPs.executeQuery()) {
                if (rs.next()) {
                    userId = rs.getLong("id");
                }
            }
        } catch (SQLException e) {
            log.error("Ошибка при поиске ID пользователя: " + e.getMessage());
        }

        // Если база не нашла юзера, мы сразу увидим это в логе сервера до падения инсерта!
        if (userId == -1) {
            log.error("!!! ОШИБКА СОПОСТАВЛЕНИЯ СЕССИИ В БД !!!");
            log.error("Искомый логин: '{}'", ownerLogin);
            log.error("Искомый хэш пароля: '{}'", password);
            return -1;
        }

        // Шаг 2: Делаем чистый INSERT с готовым числовым userId
        String sql = "INSERT INTO studygroup (name, x, y, creationdate, studentcount, shouldbeexpelled, " +
                "formofeducation, semesterenum, namegroupadmin, passportid, eyecolor, haircolor, nationality, owner_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " + // Просто знак вопроса вместо подзапроса!
                "RETURNING id";
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
            prSt.setLong(14, userId);
            try (ResultSet rs = prSt.executeQuery()) {
                if (rs.next()) {
                    long generatedId = rs.getLong(1);
                    studyGroup.setId(generatedId);
                    return generatedId;
                }
            }
    } catch (SQLException e) {
            log.error("!!! КРИТИЧЕСКАЯ ОШИБКА ПОСТГРЕСА ПРИ ДОБАВЛЕНИИ ГРУППЫ !!!");
            log.error("Сообщение ошибки: " + e.getMessage());
            log.error("SQL-статус: " + e.getSQLState());
            e.printStackTrace(); // Напечатает всю трассировку в консоль сервера
            return -1;
        }
        return -1;
    }
    public boolean deleteStudy(long id, String ownerLogin, String password){
       String sql = "DELETE FROM studygroup WHERE id = ? " +
               "AND owner_id = (SELECT id FROM users WHERE login = ? AND password = ?)";
        try(Connection connect = databaseHandler.connect(); PreparedStatement prSt = connect.prepareStatement(sql)){
           prSt.setLong(1, id);
           prSt.setString(2, ownerLogin);
           prSt.setString(3,  password);
           if(prSt.executeUpdate()==1){
               return true;
           }
        } catch (Exception e) {
            System.err.println("Error delete group" + e.getMessage());
        }
        return false;
    }
    public boolean updateStudy(long id, StudyGroup studyGroup, String ownerLogin, String password){
        String sql = "UPDATE studygroup SET name = ?, x = ?, y = ?, creationdate = ?, " +
                "studentcount = ?, shouldbeexpelled = ?, formofeducation = ?, semesterenum = ?, " +
                "namegroupadmin = ?, passportid = ?, eyecolor = ?, haircolor = ?, nationality = ? " +
                "WHERE id = ? " +
                "AND owner_id = (SELECT id FROM users WHERE login = ? AND password = ?)";
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
            prSt.setString(16,  password);
            if(prSt.executeUpdate()==1){
                return true;
            }
        }catch (Exception e){
            System.err.println("Error update group" + e.getMessage());
        }
        return false;
    }
    public int clearAllGroups(String ownerLogin, String password){
        String sql = "DELETE FROM studygroup WHERE owner_id = (SELECT id FROM users WHERE login = ? AND password = ?)";
        try(Connection connect = databaseHandler.connect(); PreparedStatement prSt = connect.prepareStatement(sql)){
            prSt.setString(1, ownerLogin);
            prSt.setString(2, password);
            return prSt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении очистки коллекции в БД: " + e.getMessage());
            return -1;
        }
    }

}
