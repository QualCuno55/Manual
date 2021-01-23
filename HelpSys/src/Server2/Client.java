package Server2;


import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static Socket socket;

    public static void main(String[] args) throws IOException {
        connect();
        // while(true) {
        CUI();
        //  }
        // end();
    }


    public static void connect() throws IOException {
        socket = new Socket("localhost", 8000);
    }

    public static void handle() {

    }

    public static void end() throws IOException {
        socket.close();
    }

    public static void CUI() throws IOException {
        DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
        DataInputStream reader = new DataInputStream(socket.getInputStream());


        JFrame jFrame = new JFrame("Form");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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


        button.setBounds(170, 500, 150, 30);
        button2.setBounds(170, 550, 150, 30);
        button3.setBounds(170, 600, 150, 30);
        button4.setBounds(170, 650, 150, 30);

        button5.setBounds(350, 500, 150, 30);
        button6.setBounds(350, 550, 150, 30);
        button7.setBounds(350, 600, 150, 30);
        button8.setBounds(350, 650, 150, 30);

        button9.setBounds(530, 500, 170, 30);
        button10.setBounds(530, 550, 170, 30);
        button11.setBounds(530, 600, 170, 30);
        button12.setBounds(530, 650, 170, 30);


        jFrame.add(button);
        jFrame.add(button2);
        jFrame.add(button3);
        jFrame.add(button4);
        jFrame.add(button5);
        jFrame.add(button6);
        jFrame.add(button7);
        jFrame.add(button8);
        jFrame.add(button9);
        jFrame.add(button10);
        jFrame.add(button11);
        jFrame.add(button12);
        jFrame.setSize(900, 720);
        jFrame.getContentPane().setLayout(null); //надо запретить измен. размеров
        jFrame.setLayout(null);
        jFrame.setVisible(true);


        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame addfaculity = new JFrame();
                addfaculity.setSize(500, 500);


                JButton enter = new JButton("Ввести");
                enter.setBounds(180,400,100,30);
                JLabel label = new JLabel("ФИО студента");
                JLabel label2 = new JLabel("id группы");
                JLabel label3 = new JLabel("Дата зачисления");
                label.setBounds(180,50,200,30);
                label2.setBounds(195,140,200,30);
                label3.setBounds(170,220,200,30);
                JTextField enter1 = new JTextField();
                JTextField enter2 = new JTextField();
                JTextField enter3 = new JTextField();
                enter1.setBounds(110,90,250,30);
                enter2.setBounds(180,180,100,30);
                enter3.setBounds(180,270,100,30);



                addfaculity.add(enter);
                addfaculity.add(label);
                addfaculity.add(label2);
                addfaculity.add(label3);
                addfaculity.add(enter2);
                addfaculity.add(enter1);
                addfaculity.add(enter3);
                addfaculity.getContentPane().setLayout(null); //надо запретить измен. размеров
                addfaculity.setLayout(null);
                addfaculity.setVisible(true);

                enter.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                       String s1 = enter1.getText();
                       int s2 = Integer.parseInt(enter2.getText());
                       String s3 = enter3.getText();
                        try {
                            writer.writeInt(1);
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
                        addfaculity.dispose();
                    }
                });
                return;
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame addfaculity = new JFrame();
                addfaculity.setSize(500, 500);


                JButton enter = new JButton("Ввести");
                enter.setBounds(180,400,100,30);
                JLabel label = new JLabel("Id студента");
                JLabel label2 = new JLabel("Новое ФИО");
                JLabel label3 = new JLabel("Новый id группы");
                JLabel label4 = new JLabel("Новыая дата зачисления");
                label.setBounds(180,50,200,30);
                label2.setBounds(180,140,200,30);
                label3.setBounds(170,220,200,30);
                label4.setBounds(160,310,200,30);
                JTextField enter1 = new JTextField();
                JTextField enter2 = new JTextField();
                JTextField enter3 = new JTextField();
                JTextField enter4 = new JTextField();
                enter1.setBounds(180,90,100,30);
                enter2.setBounds(110,180,250,30);
                enter3.setBounds(180,270,100,30);
                enter4.setBounds(160,350,150,30);



                addfaculity.add(enter);
                addfaculity.add(label);
                addfaculity.add(label2);
                addfaculity.add(label3);
                addfaculity.add(label4);
                addfaculity.add(enter2);
                addfaculity.add(enter1);
                addfaculity.add(enter3);
                addfaculity.add(enter4);
                addfaculity.getContentPane().setLayout(null); //надо запретить измен. размеров
                addfaculity.setLayout(null);
                addfaculity.setVisible(true);

                enter.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int s = Integer.parseInt(enter1.getText());
                        String s2 = enter2.getText();
                        int s3 = Integer.parseInt(enter3.getText());
                        String s4 = enter4.getText();
                        try {
                            writer.writeInt(2);
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
                        addfaculity.dispose();
                    }
                });
                return;
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame addfaculity = new JFrame();
                addfaculity.setSize(500, 500);


                JButton enter = new JButton("Ввести");
                enter.setBounds(180,400,100,30);
                JLabel label = new JLabel("Id студента");

                label.setBounds(150,50,200,30);
                JTextField enter1 = new JTextField();
                enter1.setBounds(180,90,100,30);




                addfaculity.add(enter);
                addfaculity.add(label);
                addfaculity.add(enter1);
                addfaculity.getContentPane().setLayout(null); //надо запретить измен. размеров
                addfaculity.setLayout(null);
                addfaculity.setVisible(true);

                enter.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int s = Integer.parseInt(enter1.getText());

                        try {
                            writer.writeInt(3);
                            writer.flush();
                            writer.writeInt(s);
                            writer.flush();

                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        addfaculity.dispose();
                    }
                });
                return;
            }
        });
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    writer.writeInt(4);
                    writer.flush();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }


                Object [][] all = new Object[50][5];
                int a1 = 0;

                try {
                    a1 = reader.readInt();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                for(int i=0;i<a1;i++){
                    for(int j=0;j<1;j++){
                        try {
                            all[i][j]= reader.readUTF();
                            all[i][j+1] = reader.readUTF();
                            all[i][j+2] = reader.readUTF();
                            all[i][j+3] = reader.readUTF();
                            all[i][j+4] = reader.readUTF();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                    }
                }
                Object [] columns = {"Id","Имя студента", "Название группы","Название факультета","Дата зачисления"};
                JTable jtable = new JTable(all,columns);
                jtable.setBounds(10, 100, 650, 300);
                jFrame.add(jtable);

            }
        });
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame addfaculity = new JFrame();
                addfaculity.setSize(500, 500);


                JButton enter = new JButton("Ввести");
                enter.setBounds(180,400,100,30);
                JLabel label = new JLabel("Название группы");
                JLabel label2 = new JLabel("id факультета");
                label.setBounds(160,50,150,30);
                label2.setBounds(150,140,200,30);
                JTextField enter1 = new JTextField();
                JTextField enter2 = new JTextField();
                enter1.setBounds(180,90,100,30);
                enter2.setBounds(180,180,100,30);



                addfaculity.add(enter);
                addfaculity.add(label);
                addfaculity.add(label2);
                addfaculity.add(enter1);
                addfaculity.add(enter2);
                addfaculity.getContentPane().setLayout(null); //надо запретить измен. размеров
                addfaculity.setLayout(null);
                addfaculity.setVisible(true);

                enter.addActionListener(new ActionListener() {
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
                        addfaculity.dispose();
                    }
                });
                return;
            }
        });
        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame addfaculity = new JFrame();
                addfaculity.setSize(500, 500);


                JButton enter = new JButton("Ввести");
                enter.setBounds(180,400,100,30);
                JLabel label = new JLabel("Id нужной группы");
                JLabel label2 = new JLabel("Новое название группы");
                JLabel label3 = new JLabel("Новый id факультета");
                label.setBounds(170,50,200,30);
                label2.setBounds(150,140,200,30);
                label3.setBounds(160,220,200,30);
                JTextField enter1 = new JTextField();
                JTextField enter2 = new JTextField();
                JTextField enter3 = new JTextField();
                enter1.setBounds(180,90,100,30);
                enter2.setBounds(180,180,100,30);
                enter3.setBounds(180,270,100,30);



                addfaculity.add(enter);
                addfaculity.add(label);
                addfaculity.add(label2);
                addfaculity.add(label3);
                addfaculity.add(enter2);
                addfaculity.add(enter1);
                addfaculity.add(enter3);
                addfaculity.getContentPane().setLayout(null); //надо запретить измен. размеров
                addfaculity.setLayout(null);
                addfaculity.setVisible(true);

                enter.addActionListener(new ActionListener() {
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
                        addfaculity.dispose();
                    }
                });
                return;
            }
        });
        button7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame addfaculity = new JFrame();
                addfaculity.setSize(500, 500);


                JButton enter = new JButton("Ввести");
                enter.setBounds(180,400,100,30);
                JLabel label = new JLabel("Id нужной группы");

                label.setBounds(150,50,200,30);
                JTextField enter1 = new JTextField();
                enter1.setBounds(180,90,100,30);




                addfaculity.add(enter);
                addfaculity.add(label);
                addfaculity.add(enter1);
                addfaculity.getContentPane().setLayout(null); //надо запретить измен. размеров
                addfaculity.setLayout(null);
                addfaculity.setVisible(true);

                enter.addActionListener(new ActionListener() {
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
                        addfaculity.dispose();
                    }
                });
                return;
            }
        });
        button8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    writer.writeInt(8);
                    writer.flush();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }


                Object [][] all = new Object[50][3];
                int a1 = 0;

                try {
                    a1 = reader.readInt();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                for(int i=0;i<a1;i++){
                    for(int j=0;j<1;j++){
                        try {
                            all[i][j]= reader.readUTF();
                            all[i][j+1] = reader.readUTF();
                            all[i][j+2] = reader.readUTF();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                    }
                }
                Object [] columns = {"Id","Название группы", "Название факультета"};
                JTable jtable = new JTable(all,columns);
                jtable.setBounds(10, 100, 650, 300);
                jFrame.add(jtable);
            }
        });
        button9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    JFrame addfaculity = new JFrame();
                addfaculity.setSize(500, 500);


                JButton enter = new JButton("Ввести");
                enter.setBounds(180,400,100,30);
                JLabel label = new JLabel("Название факультета");
                label.setBounds(160,50,150,30);
                JTextField enter1 = new JTextField();
                enter1.setBounds(180,90,100,30);



                addfaculity.add(enter);
                addfaculity.add(label);
                addfaculity.add(enter1);
                addfaculity.getContentPane().setLayout(null); //надо запретить измен. размеров
                addfaculity.setLayout(null);
                addfaculity.setVisible(true);

                enter.addActionListener(new ActionListener() {
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
                          addfaculity.dispose();
                    }
                });
                         return;
            }
        });
        button10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame addfaculity = new JFrame();
                addfaculity.setSize(500, 500);


                JButton enter = new JButton("Ввести");
                enter.setBounds(180,400,100,30);
                JLabel label = new JLabel("Id нужного факультета");
                JLabel label2 = new JLabel("Новое название факультета");
                label.setBounds(160,50,200,30);
                label2.setBounds(150,140,200,30);
                JTextField enter1 = new JTextField();
                JTextField enter2 = new JTextField();
                enter1.setBounds(180,90,100,30);
                enter2.setBounds(180,180,100,30);



                addfaculity.add(enter);
                addfaculity.add(label);
                addfaculity.add(label2);
                addfaculity.add(enter2);
                addfaculity.add(enter1);
                addfaculity.getContentPane().setLayout(null); //надо запретить измен. размеров
                addfaculity.setLayout(null);
                addfaculity.setVisible(true);

                enter.addActionListener(new ActionListener() {
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
                        addfaculity.dispose();
                    }
                });
                return;
            }
        });
        button11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame addfaculity = new JFrame();
                addfaculity.setSize(500, 500);


                JButton enter = new JButton("Ввести");
                enter.setBounds(180,400,100,30);
                JLabel label = new JLabel("Id нужного факультета");

                label.setBounds(160,50,200,30);
                JTextField enter1 = new JTextField();
                enter1.setBounds(180,90,100,30);




                addfaculity.add(enter);
                addfaculity.add(label);
                addfaculity.add(enter1);
                addfaculity.getContentPane().setLayout(null); //надо запретить измен. размеров
                addfaculity.setLayout(null);
                addfaculity.setVisible(true);

                enter.addActionListener(new ActionListener() {
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
                        addfaculity.dispose();
                    }
                });
                return;
            }
        });
        button12.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    writer.writeInt(12);
                    writer.flush();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                Object [][] all = new Object[50][2];
                int a1 = 0;

                try {
                    a1 = reader.readInt();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                for(int i=0;i<a1;i++){
                    for(int j=0;j<1;j++){
                        try {
                            all[i][j]= reader.readUTF();
                            all[i][j+1] = reader.readUTF();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                    }
                }
                Object [] columns1 = {"1","12"};
                JTable jtable = new JTable(all,columns1);
                jtable.setBounds(10, 100, 650, 300);
                jFrame.add(jtable);
            }
        });



        return;
    }

}
