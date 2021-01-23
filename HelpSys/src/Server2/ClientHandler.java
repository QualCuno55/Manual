package Server2;



import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.sql.*;

public class ClientHandler extends Thread{
    private final Socket client;

    public ClientHandler(Socket client){
    this.client = client;
    start();
    }

    @Override
    public void run(){
       while(true){
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
    public void readData() throws IOException, SQLException  {
        DataInputStream reader = new DataInputStream(client.getInputStream());
        DataOutputStream writer = new DataOutputStream(client.getOutputStream());


        Connection connection;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
         //  System.out.println("Загрузка драйвера успешна");
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MySQL?useSSL=false&useJDBCComplaintTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", "admin", "admin");
          //  System.out.println("Подключение установлено");

        } catch (SQLException ex) {
         //   System.out.println("Не удалось создать соединение");
            return;
        }

        Statement statement = connection.createStatement();

       if(reader.available() <= 0){
            return;
        }
        int id = reader.readInt();
        System.out.println(id);
        switch (id){
            case 1:{
                String name = reader.readUTF();
                int group = reader.readInt();
                String date = reader.readUTF();

                String query1 = "insert into database.students (Student_Name, Group_id, DateOfEnrollment) values (?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query1);
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, group);
                preparedStatement.setString(3, date);
                preparedStatement.executeUpdate();
                break;
            }
            case 2:{
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
                break;
            }
            case 3:{
                int num = reader.readInt();
                String delete = "delete from database.students where Student_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(delete);
                preparedStatement.setInt(1, num);
                preparedStatement.executeUpdate();
                break;
            }
            case 4: {

                String vb = "select Student_id, Student_Name,Group_Name, Faculity_Name,DateOfEnrollment from database.faculity, database.groups, database.students where students.Group_id =  groups.Group_id AND groups.Faculity_id = faculity.Faculity_id group by Student_Name;";
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
                    String faculityName = resultSet1.getString("Faculity_Name");

                    writer.writeUTF(String.valueOf(student_id));
                    writer.writeUTF(studentName);
                    writer.writeUTF(groupName);
                    writer.writeUTF(faculityName);
                    writer.writeUTF(DateOfEnrollment);

                    writer.flush();
                }
            }
            case 5:{

                String grName = reader.readUTF();
                int facul_id = reader.readInt();
                String addGroup =  "insert into database.groups (Faculity_id, Group_Name) values (?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(addGroup);
                preparedStatement.setInt(1, facul_id);
                preparedStatement.setString(2, grName);
                preparedStatement.executeUpdate();

                break;
            }
            case 6:{
                int num = reader.readInt();
                String group1 = reader.readUTF();
                int facul_id = reader.readInt();
                String update = "update database.groups set Group_Name= ? , Faculity_id = ? where Group_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(update);
                preparedStatement.setString(1,group1);
                preparedStatement.setInt(2,facul_id);
                preparedStatement.setInt(3, num);
                preparedStatement.executeUpdate();

                break;
            }
            case 7:{
                int num = reader.readInt();
                String delete = "delete from database.groups where Group_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(delete);
                preparedStatement.setInt(1, num);
                preparedStatement.executeUpdate();

                break;
            }
            case 8:{
                String vb = "SELECT Group_id, Faculity_Name, Group_Name FROM database.faculity, database.groups WHERE faculity.Faculity_id=groups.Faculity_id;";
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
                    faculity_name = resultSet1.getString("Faculity_Name");


                    writer.writeUTF(group_id + "");
                    writer.writeUTF(groupName + "");
                    writer.writeUTF(faculity_name + "");

                    writer.flush();

                }

                break;
            }
            case 9:{
                String facName = reader.readUTF();
                String addFaculity =  "insert into database.faculity (Faculity_Name) values (?)";
                PreparedStatement preparedStatement = connection.prepareStatement(addFaculity);
                preparedStatement.setString(1, facName);
                preparedStatement.executeUpdate();
                break;
            }
            case 10:{

                int facul_id = reader.readInt();
                String facName = reader.readUTF();

                String update = "update database.faculity set Faculity_Name= ?  where Faculity_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(update);
                preparedStatement.setString(1,facName);
                preparedStatement.setInt(2,facul_id);
                preparedStatement.executeUpdate();
                break;
            }
            case 11:{
                int num = reader.readInt();
                String delete = "delete from database.faculity where Faculity_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(delete);
                preparedStatement.setInt(1, num);
                preparedStatement.executeUpdate();
                break;
            }
            case 12:{
                String vb = "select * from database.faculity";
                String va = "select count(*) from database.faculity";
                ResultSet resultSet = statement.executeQuery(va);
                resultSet.next();
                int a = resultSet.getInt(1);

                System.out.println(a);
                writer.writeInt(a);
                writer.flush();

                ResultSet resultSet1 = statement.executeQuery(vb);

                while (resultSet1.next()) {
                    int faculity_id;
                    String faculityName;
                    faculity_id = resultSet1.getInt("Faculity_id");
                    faculityName = resultSet1.getString("Faculity_Name");


                    writer.writeUTF(faculity_id + " ");
                    writer.writeUTF(faculityName);

                    writer.flush();

                }
                resultSet.close();
                resultSet1.close();
                break;
            }

            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }

    }

}
