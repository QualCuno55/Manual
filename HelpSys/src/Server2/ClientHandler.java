package Server2;


import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.sql.*;

public class ClientHandler extends Thread implements SQL{
    private final Socket client;
    static Connection connection;
    static Statement statement;




    public ClientHandler(Socket client) throws SQLException {
        this.client = client;
        start();
    }

    @Override
    public void run() {
        connect();
        while (true) {
            try {
                readData();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void connect()  {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            System.out.println("Загрузка драйвера успешна");
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MySQL?useSSL=false&useJDBCComplaintTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", "admin", "admin");
            System.out.println("Подключение установлено");

        } catch (SQLException ex) {
            System.out.println("Не удалось создать соединение");
            return;
        }

    }

    public void readData() throws IOException, SQLException {
        DataInputStream reader = new DataInputStream(client.getInputStream());

         statement  = connection.createStatement();

        if (reader.available() <= 0) {
            return;
        }
        System.out.println("Вышел");
        int id = reader.readInt();
        System.out.println(id);
        switch (id) {
            case 1: {
                addStudent();
                break;
            }
            case 2: {
                editStudent();
                break;
            }
            case 3: {
                deleteStudent();
                break;
            }
            case 4: {
                getAllStudents();
                break;
            }
            case 5: {
                addGroup();
                break;
            }
            case 6: {
                editGroup();
                break;
            }
            case 7: {
                deleteGroup();
                break;
            }
            case 8: {
                getAllGroups();
                break;
            }
            case 9: {
                addFaculty();
                break;
            }
            case 10: {
                editFaculty();
                break;
            }
            case 11: {
                deleteFaculty();
                break;
            }
            case 12: {
                getAllFaculties();
                break;
            }

            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }

    }

    @Override
    public void getAllStudents() throws IOException, SQLException {
        DataOutputStream writer = new DataOutputStream(client.getOutputStream());
        String vb = "select Student_id, Student_Name,Group_Name, Faculty_Name,DateOfEnrollment from database.faculties, database.groups, database.students where students.Group_id =  groups.Group_id AND groups.Faculty_id = faculties.Faculty_id group by Student_Name;";
        String va = "select count(*) from database.students";
        ResultSet resultSet = statement.executeQuery(va);
        resultSet.next();
        int a = resultSet.getInt(1);
        writer.writeInt(a);
        writer.flush();
        ResultSet resultSet1 = statement.executeQuery(vb);

        while (resultSet1.next()) {
            int student_id = resultSet1.getInt("Student_id");
            String groupName = resultSet1.getString("Group_Name");
            String studentName = resultSet1.getString("Student_Name");
            String DateOfEnrollment = resultSet1.getString("DateOfEnrollment");
            String faculityName = resultSet1.getString("Faculty_Name");

            writer.writeUTF(String.valueOf(student_id));
            writer.writeUTF(studentName);
            writer.writeUTF(groupName);
            writer.writeUTF(faculityName);
            writer.writeUTF(DateOfEnrollment);

            writer.flush();
        }
    }

    @Override
    public void addStudent() throws IOException, SQLException {
        DataInputStream reader = new DataInputStream(client.getInputStream());
        String name = reader.readUTF();
        int group = reader.readInt();
        String date = reader.readUTF();

        String query1 = "insert into database.students (Student_Name, Group_id, DateOfEnrollment) values (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query1);
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, group);
        preparedStatement.setString(3, date);
        preparedStatement.executeUpdate();
    }

    @Override
    public void editStudent() throws IOException, SQLException {
        DataInputStream reader = new DataInputStream(client.getInputStream());
        int num = reader.readInt();
        String name = reader.readUTF();
        int num1 = reader.readInt();
        String date = reader.readUTF();

        String update = "update database.students set Student_Name = ? , Group_id = ? , DateOfEnrollment = ? where Student_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, num1);
        preparedStatement.setString(3, date);
        preparedStatement.setInt(4, num);
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteStudent() throws IOException, SQLException {
        DataInputStream reader = new DataInputStream(client.getInputStream());
        int num = reader.readInt();
        String delete = "delete from database.students where Student_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(delete);
        preparedStatement.setInt(1, num);
        preparedStatement.executeUpdate();
    }

    @Override
    public void getAllGroups() throws IOException, SQLException {
        DataOutputStream writer = new DataOutputStream(client.getOutputStream());
        String vb = "SELECT Group_id, Faculty_Name, Group_Name FROM database.faculties, database.groups WHERE faculties.Faculty_id=groups.Faculty_id;";
        String va = "select count(*) from database.groups";
        ResultSet resultSet = statement.executeQuery(va);
        resultSet.next();
        int a = resultSet.getInt(1);
        writer.writeInt(a);
        writer.flush();
        ResultSet resultSet1 = statement.executeQuery(vb);

        while (resultSet1.next()) {
            int group_id;
            String groupName;
            String faculity_name;
            group_id = resultSet1.getInt("Group_id");
            groupName = resultSet1.getString("Group_Name");
            faculity_name = resultSet1.getString("Faculty_Name");


            writer.writeUTF(group_id + "");
            writer.writeUTF(groupName + "");
            writer.writeUTF(faculity_name + "");

            writer.flush();

        }
    }

    @Override
    public void addGroup() throws IOException, SQLException {
        DataInputStream reader = new DataInputStream(client.getInputStream());
        String grName = reader.readUTF();
        int facul_id = reader.readInt();
        System.out.println(grName + facul_id);
        String addGroup = "insert into database.groups (Faculty_id, Group_Name) values (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(addGroup);
        preparedStatement.setInt(1, facul_id);
        preparedStatement.setString(2, grName);
        preparedStatement.executeUpdate();
    }

    @Override
    public void editGroup() throws IOException, SQLException {
        DataInputStream reader = new DataInputStream(client.getInputStream());
        int num = reader.readInt();
        String group1 = reader.readUTF();
        int facul_id = reader.readInt();
        String update = "update database.groups set Group_Name= ? , Faculty_id = ? where Group_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setString(1, group1);
        preparedStatement.setInt(2, facul_id);
        preparedStatement.setInt(3, num);
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteGroup() throws IOException, SQLException {
        DataInputStream reader = new DataInputStream(client.getInputStream());
        int num = reader.readInt();
        String delete = "delete from database.groups where Group_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(delete);
        preparedStatement.setInt(1, num);
        preparedStatement.executeUpdate();
    }

    @Override
    public void getAllFaculties() throws IOException, SQLException {
        DataOutputStream writer = new DataOutputStream(client.getOutputStream());
        String vb = "select * from database.faculties";
        String va = "select count(*) from database.faculties";
        ResultSet resultSet = statement.executeQuery(va);
        resultSet.next();
        int a = resultSet.getInt(1);

        System.out.println(a);
        writer.writeInt(a);
        writer.flush();

        ResultSet resultSet1 = statement.executeQuery(vb);

        while (resultSet1.next()) {
            int faculty_id;
            String facultyName;
            faculty_id = resultSet1.getInt("Faculty_id");
            facultyName = resultSet1.getString("Faculty_Name");


            writer.writeUTF(faculty_id + " ");
            writer.writeUTF(facultyName);

            writer.flush();

        }


    }

    @Override
    public void addFaculty() throws IOException, SQLException {
        DataInputStream reader = new DataInputStream(client.getInputStream());
        String facName = reader.readUTF();
        String addFaculty = "insert into database.faculties (Faculty_Name) values (?)";
        PreparedStatement preparedStatement = connection.prepareStatement(addFaculty);
        preparedStatement.setString(1, facName);
        preparedStatement.executeUpdate();
    }

    @Override
    public void editFaculty() throws IOException, SQLException {
        DataInputStream reader = new DataInputStream(client.getInputStream());
        int facul_id = reader.readInt();
        String facName = reader.readUTF();

        String update = "update database.faculties set Faculty_Name= ?  where Faculty_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setString(1, facName);
        preparedStatement.setInt(2, facul_id);
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteFaculty() throws IOException, SQLException {
        DataInputStream reader = new DataInputStream(client.getInputStream());
        int num = reader.readInt();
        String delete = "delete from database.faculties where Faculty_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(delete);
        preparedStatement.setInt(1, num);
        preparedStatement.executeUpdate();
    }
}
