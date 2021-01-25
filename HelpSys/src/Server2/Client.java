package Server2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;


public class Client implements SQL {
    private static Socket socket;
    static JTable jTable = new JTable();
    static JFrame jFrame = new JFrame("Form");
    static JPanel students = new JPanel(new BorderLayout());
    static JPanel groups = new JPanel(new BorderLayout());
    static JPanel faculity = new JPanel(new BorderLayout());

    public static void main(String[] args) throws IOException {
        connect();
        // while(true) {
        Client client = new Client();
        client.CUI();
        //    }
        // end();
    }


    public static void connect() throws IOException {
        socket = new Socket("localhost", 8000);
    }


    public static void end() throws IOException {
        socket.close();
    }

    public void CUI() {

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTabbedPane jTabbedPane = new JTabbedPane();

        JPanel studentsButtons = new JPanel(new FlowLayout());
        JPanel groupsButtons = new JPanel(new FlowLayout());
        JPanel facultiesButtons = new JPanel(new FlowLayout());

        students.setLayout(new BorderLayout());
        groups.setLayout(new BorderLayout());
        faculity.setLayout(new BorderLayout());


        jTabbedPane.addTab("stundets", students);
        jTabbedPane.addTab("groups", groups);
        jTabbedPane.addTab("faculity", faculity);


        JButton button = new JButton("Добавить студента");
        JButton button2 = new JButton("Изменить студента");
        JButton button3 = new JButton("Удалить студента");
        JButton button4 = new JButton("Таблица студентов");

        JButton button5 = new JButton("Добавить группу");
        JButton button6 = new JButton("Изменить группу");
        JButton button7 = new JButton("Удалить группу");
        JButton button8 = new JButton("Таблица групп");

        JButton button9 = new JButton("Добавить факультет");
        JButton button10 = new JButton("Изменить факультет");
        JButton button11 = new JButton("Удалить факультет");
        JButton button12 = new JButton("Таблица факультетов");

        studentsButtons.add(button);
        studentsButtons.add(button2);
        studentsButtons.add(button3);
        studentsButtons.add(button4);

        groupsButtons.add(button5);
        groupsButtons.add(button6);
        groupsButtons.add(button7);
        groupsButtons.add(button8);

        facultiesButtons.add(button9);
        facultiesButtons.add(button10);
        facultiesButtons.add(button11);
        facultiesButtons.add(button12);

        students.add(studentsButtons, BorderLayout.SOUTH);
        groups.add(groupsButtons, BorderLayout.SOUTH);
        faculity.add(facultiesButtons, BorderLayout.SOUTH);

        jFrame.add(jTabbedPane);
        jFrame.setSize(900, 720);
        jFrame.setVisible(true);

        button.addActionListener(Client -> {
            try {
                addStudent();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        button2.addActionListener(Client -> {
            try {
                editStudent();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        button3.addActionListener((Client -> {
            try {
                deleteStudent();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        button4.addActionListener(Client -> {
            try {
                getAllStudents();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        button5.addActionListener(Client -> {
            try {
                addGroup();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        button6.addActionListener(Client -> {
            try {
                editGroup();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        button7.addActionListener(Client -> {
            try {
                deleteGroup();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        button8.addActionListener(Client -> {
            try {
                getAllGroups();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        button9.addActionListener(Client -> {
            try {
                addFaculty();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        button10.addActionListener(Client -> {
            try {
                editFaculty();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        button11.addActionListener(Client -> {
            try {
                deleteFaculty();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        button12.addActionListener(Client -> {
            try {
                getAllFaculties();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return;
    }

    @Override
    public void getAllStudents() throws IOException {
        DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
        DataInputStream reader = new DataInputStream(socket.getInputStream());
        jTable.removeAll();
        Object[] columnsForStudents = {"Id", "Имя студента", "Название группы", "Название факультета", "Дата зачисления"};
        try {
            writer.writeInt(4);
            System.out.println("4");
            writer.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        int a1 = 0;
        try {
            a1 = reader.readInt();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        Object[][] allStudents = new Object[a1][6];
        for (int i = 0; i < a1; i++) {
            for (int j = 0; j < 1; j++) {
                try {
                    allStudents[i][j] = reader.readUTF();
                    allStudents[i][j + 1] = reader.readUTF();
                    allStudents[i][j + 2] = reader.readUTF();
                    allStudents[i][j + 3] = reader.readUTF();
                    allStudents[i][j + 4] = reader.readUTF();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        TableModel modelForStudents = new DefaultTableModel(allStudents, columnsForStudents);
        jTable.setModel(modelForStudents);
        students.add(jTable, BorderLayout.CENTER);
        students.add(new JScrollPane(jTable));
        jFrame.repaint();

    }

    @Override
    public void addStudent() throws IOException {
        DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
        JFrame jFrame = new JFrame();

        JPanel panel = new JPanel();
        JPanel panelForButton = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        JButton button = new JButton("Ввести");
        JLabel label = new JLabel("ФИО студента");
        JLabel label2 = new JLabel("id группы");
        JLabel label3 = new JLabel("Дата зачисления");

        JTextField enter1 = new JTextField();
        JTextField enter2 = new JTextField();
        JTextField enter3 = new JTextField();

        panel.add(label);
        panel.add(enter1);
        panel.add(label2);
        panel.add(enter2);
        panel.add(label3);
        panel.add(enter3);

        panelForButton.add(button);

        jFrame.add(panel, BorderLayout.CENTER);
        jFrame.add(panelForButton, BorderLayout.SOUTH);
        jFrame.setSize(500, 200);
        jFrame.setVisible(true);
        jFrame.setResizable(false);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s1 = enter1.getText();
                int s2 = Integer.parseInt(enter2.getText());
                String s3 = enter3.getText();
                try {
                    writer.writeInt(1);
                    System.out.println("1");
                    writer.flush();
                    writer.writeUTF(s1);
                    writer.flush();
                    writer.writeInt(s2);
                    writer.flush();
                    writer.writeUTF(s3);
                    writer.flush();

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                jFrame.dispose();

            }
        });
    }

    @Override
    public void editStudent() throws IOException {
        DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
        JFrame jFrame = new JFrame();


        JButton button = new JButton("Ввести");

        JPanel panel = new JPanel();
        JPanel panelForButton = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel label = new JLabel("Id студента");
        JLabel label2 = new JLabel("Новое ФИО");
        JLabel label3 = new JLabel("Новый id группы");
        JLabel label4 = new JLabel("Новая дата зачисления");

        JTextField enter1 = new JTextField();
        JTextField enter2 = new JTextField();
        JTextField enter3 = new JTextField();
        JTextField enter4 = new JTextField();

        panel.add(label);
        panel.add(enter1);
        panel.add(label2);
        panel.add(enter2);
        panel.add(label3);
        panel.add(enter3);
        panel.add(label4);
        panel.add(enter4);

        panelForButton.add(button);

        jFrame.add(panel, BorderLayout.CENTER);
        jFrame.add(panelForButton, BorderLayout.SOUTH);
        jFrame.setSize(500, 300);
        jFrame.setResizable(false);
        jFrame.setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int s = Integer.parseInt(enter1.getText());
                String s2 = enter2.getText();
                int s3 = Integer.parseInt(enter3.getText());
                String s4 = enter4.getText();
                try {
                    writer.writeInt(2);
                    System.out.println("2");
                    writer.flush();
                    writer.writeInt(s);
                    writer.flush();
                    writer.writeUTF(s2);
                    writer.flush();
                    writer.writeInt(s3);
                    writer.flush();
                    writer.writeUTF(s4);
                    writer.flush();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                jFrame.dispose();
            }
        });
    }

    @Override
    public void deleteStudent() throws IOException {
        DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
        JFrame jFrame = new JFrame();

        JPanel panel = new JPanel();
        JPanel panelForButton = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        JButton button = new JButton("Ввести");
        JLabel label = new JLabel("Id студента");
        JTextField enter1 = new JTextField();

        panel.add(label);
        panel.add(enter1);
        panelForButton.add(button);

        jFrame.add(panel, BorderLayout.CENTER);
        jFrame.add(panelForButton, BorderLayout.SOUTH);
        jFrame.setSize(500, 100);
        jFrame.setResizable(false);
        jFrame.setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int s = Integer.parseInt(enter1.getText());

                try {
                    writer.writeInt(3);
                    System.out.println("3");
                    writer.flush();
                    writer.writeInt(s);
                    writer.flush();

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                jFrame.dispose();

            }
        });

    }

    @Override
    public void getAllGroups() throws IOException {
        jTable.removeAll();
        DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
        DataInputStream reader = new DataInputStream(socket.getInputStream());

        Object[] columnsForGroups = {"Id", "Название группы", "Название факультета"};
        try {
            writer.writeInt(8);
            writer.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        int a1 = 0;

        try {
            a1 = reader.readInt();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        String[][] allGroups = new String[a1][3];
        for (int i = 0; i < a1; i++) {
            for (int j = 0; j < 1; j++) {
                try {
                    allGroups[i][j] = reader.readUTF();
                    allGroups[i][j + 1] = reader.readUTF();
                    allGroups[i][j + 2] = reader.readUTF();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        }
        TableModel modelForGroups = new DefaultTableModel(allGroups, columnsForGroups);
        jTable.setModel(modelForGroups);
        groups.add(jTable, BorderLayout.CENTER);
        groups.add(new JScrollPane(jTable));
        jFrame.repaint();
    }


    @Override
    public void addGroup() throws IOException {
        DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
        JFrame jFrame = new JFrame();
        JPanel panel = new JPanel();
        JPanel panelForButton = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        JButton button = new JButton("Ввести");

        JLabel label = new JLabel("Название группы");
        JLabel label2 = new JLabel("id факультета");

        JTextField enter1 = new JTextField();
        JTextField enter2 = new JTextField();

        panel.add(label);
        panel.add(enter1);
        panel.add(label2);
        panel.add(enter2);

        panelForButton.add(button, BorderLayout.SOUTH);

        jFrame.add(panel, BorderLayout.CENTER);
        jFrame.add(panelForButton, BorderLayout.SOUTH);
        jFrame.setSize(500, 150);
        jFrame.setResizable(false);
        jFrame.setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = enter1.getText();
                int s1 = Integer.parseInt(enter2.getText());
                try {
                    writer.writeInt(5);
                    writer.flush();
                    writer.writeUTF(s);
                    writer.flush();
                    writer.writeInt(s1);
                    writer.flush();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                jFrame.dispose();
            }
        });
    }

    @Override
    public void editGroup() throws IOException {
        DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
        JFrame jFrame = new JFrame();
        JPanel panel = new JPanel();
        JPanel panelForButton = new JPanel();
        panel.setLayout(new GridLayout(3, 2));


        JButton button = new JButton("Ввести");

        JLabel label = new JLabel("Id нужной группы");
        JLabel label2 = new JLabel("Новое название группы");
        JLabel label3 = new JLabel("Новый id факультета");

        JTextField enter1 = new JTextField();
        JTextField enter2 = new JTextField();
        JTextField enter3 = new JTextField();

        panel.add(label);
        panel.add(enter1);
        panel.add(label2);
        panel.add(enter2);
        panel.add(label3);
        panel.add(enter3);

        panelForButton.add(button);

        jFrame.add(panel, BorderLayout.CENTER);
        jFrame.add(panelForButton, BorderLayout.SOUTH);
        jFrame.setSize(500, 200);
        jFrame.setResizable(false);
        jFrame.setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int s = Integer.parseInt(enter1.getText());
                String s2 = enter2.getText();
                int s3 = Integer.parseInt(enter3.getText());
                try {
                    writer.writeInt(6);
                    writer.flush();
                    writer.writeInt(s);
                    writer.flush();
                    writer.writeUTF(s2);
                    writer.flush();
                    writer.writeInt(s3);
                    writer.flush();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                jFrame.dispose();
            }
        });

    }

    @Override
    public void deleteGroup() throws IOException {
        DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
        JFrame jFrame = new JFrame();
        JPanel panel = new JPanel();
        JPanel panelForButton = new JPanel();
        panel.setLayout(new GridLayout(1, 2));


        JButton button = new JButton("Ввести");

        JLabel label = new JLabel("Id нужной группы");

        JTextField enter1 = new JTextField();


        panel.add(label);
        panel.add(enter1);

        panelForButton.add(button);

        jFrame.add(panel, BorderLayout.CENTER);
        jFrame.add(panelForButton, BorderLayout.SOUTH);



        jFrame.setSize(500, 100);
        jFrame.setResizable(false);
        jFrame.setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int s = Integer.parseInt(enter1.getText());

                try {
                    writer.writeInt(7);
                    writer.flush();
                    writer.writeInt(s);
                    writer.flush();

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                jFrame.dispose();
            }
        });

    }

    @Override
    public void getAllFaculties() throws IOException {
        jTable.removeAll();
        Object[] columnsForFaculties = {"Id", "Название факультета"};
        DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
        DataInputStream reader = new DataInputStream(socket.getInputStream());
        try {
            writer.writeInt(12);
            writer.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        int a1 = 0;

        try {
            a1 = reader.readInt();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        String[][] allFaculties = new String[a1][2];
        for (int i = 0; i < a1; i++) {
            for (int j = 0; j < 1; j++) {
                try {
                    allFaculties[i][j] = reader.readUTF();
                    allFaculties[i][j + 1] = reader.readUTF();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        }
        TableModel modelForFaculties = new DefaultTableModel(allFaculties, columnsForFaculties);
        jTable.setModel(modelForFaculties);
        faculity.add(jTable, BorderLayout.CENTER);
        faculity.add(new JScrollPane((jTable)));
        jFrame.repaint();
    }


    @Override
    public void addFaculty() throws IOException {
        DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
        JFrame jFrame = new JFrame();

        JPanel panel = new JPanel();
        JPanel panelForButton = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        JButton button = new JButton("Ввести");

        JLabel label = new JLabel("Название факультета");

        JTextField enter1 = new JTextField();

        panel.add(label);
        panel.add(enter1);
        panelForButton.add(button);

        jFrame.add(panel, BorderLayout.CENTER);
        jFrame.add(panelForButton, BorderLayout.SOUTH);


        jFrame.setSize(500, 100);
        jFrame.setResizable(false);
        jFrame.setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = enter1.getText();
                try {
                    writer.writeInt(9);
                    writer.flush();
                    writer.writeUTF(s);
                    writer.flush();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                jFrame.dispose();
            }
        });
        return;
    }

    @Override
    public void editFaculty() throws IOException {
        DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
        JFrame jFrame = new JFrame();
        JPanel panel = new JPanel();
        JPanel panelForButton = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        JButton button = new JButton("Ввести");

        JLabel label = new JLabel("Id нужного факультета");
        JLabel label2 = new JLabel("Новое название факультета");

        JTextField enter1 = new JTextField();
        JTextField enter2 = new JTextField();

        panel.add(label);
        panel.add(enter1);
        panel.add(label2);
        panel.add(enter2);
        panelForButton.add(button);

        jFrame.add(panel, BorderLayout.CENTER);
        jFrame.add(panelForButton, BorderLayout.SOUTH);



        jFrame.setSize(500, 150);
        jFrame.setResizable(false);
        jFrame.setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int s = Integer.parseInt(enter1.getText());
                String s2 = enter2.getText();
                try {
                    writer.writeInt(10);
                    writer.flush();
                    writer.writeInt(s);
                    writer.flush();
                    writer.writeUTF(s2);
                    writer.flush();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                jFrame.dispose();
            }
        });
        return;
    }

    @Override
    public void deleteFaculty() throws IOException {
        DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
        JFrame jFrame = new JFrame();
        JPanel panel = new JPanel();
        JPanel panelForButton = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        JButton button = new JButton("Ввести");
        JLabel label = new JLabel("Id нужного факультета");
        JTextField enter1 = new JTextField();

        panel.add(label);
        panel.add(enter1);
        panelForButton.add(button);

        jFrame.add(panel, BorderLayout.CENTER);
        jFrame.add(panelForButton, BorderLayout.SOUTH);

        jFrame.setSize(500, 100);
        jFrame.setResizable(false);
        jFrame.setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int s = Integer.parseInt(enter1.getText());
                try {
                    writer.writeInt(11);
                    writer.flush();
                    writer.writeInt(s);
                    writer.flush();

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                jFrame.dispose();
            }
        });

    }
}
