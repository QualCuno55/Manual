package helpsystem;

public class HelpSystem {

    private final DataBase students;
    private final DataBase groups;

    public HelpSystem() {
        students = new DataBase("students.txt");
        groups = new DataBase("groups.txt");
        new CUI(this);
    }

    public DataBase getStudents() {
        return students;
    }

    public DataBase getGroups() {
        return groups;
    }

    public static void main(String args[]) {
        new HelpSystem();
    }
}
