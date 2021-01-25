package Server2;

import java.io.IOException;
import java.sql.SQLException;

public interface SQL {
    public void getAllStudents() throws IOException, SQLException;
    public void addStudent() throws IOException, SQLException;
    public void editStudent() throws IOException, SQLException;
    public void deleteStudent() throws IOException, SQLException;

    public void getAllGroups() throws IOException, SQLException;
    public void addGroup() throws IOException, SQLException;
    public void editGroup() throws IOException, SQLException;
    public void deleteGroup() throws IOException, SQLException;

    public void getAllFaculties() throws IOException, SQLException;
    public void addFaculty() throws IOException, SQLException;
    public void editFaculty() throws IOException, SQLException;
    public void deleteFaculty() throws IOException, SQLException;
}
