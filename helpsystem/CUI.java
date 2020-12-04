package helpsystem;

import java.util.Scanner;

public class CUI {

    private final Scanner sc;
    private boolean isEnd;
    private final HelpSystem system;

    public CUI(HelpSystem system) {
        sc = new Scanner(System.in);
        isEnd = false;
        this.system = system;
        run();
    }

    private void run() {

        System.out.println("Справочная система университета.\n"
                + "Введите \"help\" для вызова помощи или \"exit\" для завершения программы.\n");
        help();
        status();

        String str;
        while (!isEnd) {
            str = sc.nextLine();

            switch (str) {
                case "exit":
                    isEnd = true;
                    System.out.println("Завершение программы...");
                    break;

                case "help":
                    help();
                    break;

                case "status":
                    status();
                    break;

                case "":
                    break;

                case "students":
                    students();
                    break;

                case "groups":
                    groups();
                    break;

                case "list":
                    system.getStudents().printData();
                    system.getGroups().printData();
                    break;

                case "count":
                    System.out.println("Кол-во студентов: " + system.getStudents().getCount() + ", кол-во групп: " + system.getGroups().getCount() + "\n");
                    break;

                default:
                    System.out.println("\"" + str + "\" не является командой, исполняемой программой.\n");
            }
        }
    }

    private void help() {
        System.out.println("Список команд, исполняемых программой:\n"
                + "     - help - вызов справочника по команд.\n"
                + "     - exit - завершение работы программы.\n"
                + "     - status - вывод информации по таблицам(true - таблица создана"
                + "и готова к работе, false - не создана).\n"
                + "     - students - список студентов.\n"
                + "     - groups - список групп.\n"
                + "     - list - показать все списки.\n"
                + "Список команд при работе с таблицами:\n"
                + "     - help\n"
                + "     - exit\n"
                + "     - back - Возврат в главное меню.\n"
                + "     - add - Добавить новый элемент.\n"
                + "     - delete - Удалить выбранный по номеру элемент.\n"
                + "     - set - Изменить выбранный по номеру элемент.\n"
                + "     - list - показать весь список таблицы.\n");
    }

    private void status() {
        System.out.println("Students: " + system.getStudents().isCorrect());
        System.out.println("Groups: " + system.getGroups().isCorrect() + "\n");
    }

    private void students() {
        DataBase data = system.getStudents();
        String[][] array = data.getData();
        String[] newData;
        int number;
        boolean isChanged = false;
        boolean isBreak = false;

        data.printData();

        String str;
        while (!isBreak) {
            str = sc.nextLine();

            switch (str) {
                case "exit":
                    isBreak = true;
                    isEnd = true;
                    System.out.println("Завершение программы...");
                    break;

                case "back":
                    isBreak = true;
                    System.out.println("Выход в главное меню...\n");
                    break;

                case "help":
                    help();
                    break;

                case "":
                    break;

                case "add":
                    System.out.println("Введите данные студента для добавления: ");
                    newData = createStudent();
                    if (!checkGroup(Integer.parseInt(newData[1]))) {
                        System.out.println("Группа не найдена, студент не добавлен.\n");
                        break;
                    }
                    if (data.add(newData, data.getCount())) {
                        System.out.println("Студент добавлен.\n");
                        isChanged = true;
                    } else {
                        System.out.println("Ошибка при добавлении студента.\n");
                    }
                    break;

                case "delete":
                    System.out.print("Введите номер студента для удаления: ");
                    number = Integer.parseInt(sc.next());
                    if (number < 0 || number >= data.getCount()) {
                        System.out.println("Студент не найден.");
                        break;
                    }
                    if (data.delete(number)) {
                        System.out.println("Студент удален.\n");
                        isChanged = true;
                    } else {
                        System.out.println("Ошибка при удалении студента.\n");
                    }
                    break;

                case "set":
                    System.out.print("Введите номер студента для редактирования: ");
                    number = Integer.parseInt(sc.next());
                    if (number < 0 || number >= data.getCount()) {
                        System.out.println("Студент не найден.");
                        break;
                    }
                    newData = createStudent();
                    if (!checkGroup(Integer.parseInt(newData[1]))) {
                        System.out.println("Группа не найдена, студент не добавлен.\n");
                        break;
                    }
                    if (data.add(newData, data.getCount())) {
                        System.out.println("Студент отредактирован.\n");
                        isChanged = true;
                    } else {
                        System.out.println("Ошибка при редактировании студента.\n");
                    }
                    break;

                case "list":
                    data.printData();
                    break;

                case "get":
                    System.out.print("Введите номер студента: ");
                    number = Integer.parseInt(sc.next());
                    if (number < 0 || number >= data.getCount()) {
                        System.out.println("Студент не найден.\n");
                        break;
                    } else {
                        System.out.println(data.toString(number));
                    }
                    break;

                case "group":
                    System.out.print("Введите номер группы: ");
                    number = Integer.parseInt(sc.next());
                    if (!checkGroup(number)) {
                        System.out.println("Группа не найдена.\n");
                        break;
                    }
                    System.out.println("Список студентов данной группы: ");
                    for (int i = 0; i < data.getCount(); i++) {
                        if (array[i][1].equals(number)) {
                            System.out.println(data.toString(i));
                        }
                    }
                    break;

                default:
                    System.out.println("\"" + str + "\" не является командой, исполняемой программой.\n");
            }
        }

        if (isChanged) {
            data.setData(array);
            data.upload();
        }
    }

    private void groups() {
        DataBase data = system.getGroups();
        String[][] array = data.getData();
        String[] newData;
        int number;
        boolean isChanged = false;
        boolean isBreak = false;

        data.printData();

        String str;
        while (!isBreak) {
            str = sc.nextLine();

            switch (str) {
                case "exit":
                    isBreak = true;
                    isEnd = true;
                    System.out.println("Завершение программы...");
                    break;

                case "back":
                    isBreak = true;
                    System.out.println("Выход в главное меню...\n");
                    break;

                case "help":
                    help();
                    break;

                case "":
                    break;

                case "add":
                    System.out.println("Введите данные группы для добавления: ");
                    newData = createGroup();
                    if (data.add(newData, data.getCount())) {
                        System.out.println("Группа добавлена.\n");
                        isChanged = true;
                    } else {
                        System.out.println("Ошибка при добавлении группы.\n");
                    }
                    break;

                case "delete":
                    System.out.print("Введите номер группы для удаления: ");
                    number = Integer.parseInt(sc.next());
                    if (number < 0 || number >= data.getCount()) {
                        System.out.println("Группа не найдена.");
                        break;
                    }
                    if (data.delete(number)) {
                        System.out.println("Группа удалена.\n");
                        isChanged = true;
                    } else {
                        System.out.println("Ошибка при удалении группы.\n");
                    }
                    break;

                case "set":
                    System.out.print("Введите номер группы для редактирования: ");
                    number = Integer.parseInt(sc.next());
                    if (number < 0 || number >= data.getCount()) {
                        System.out.println("Группа не найдена.");
                        break;
                    }
                    newData = createGroup();
                    if (data.add(newData, data.getCount())) {
                        System.out.println("Группа отредактирована.\n");
                        isChanged = true;
                    } else {
                        System.out.println("Ошибка при редактировании группы.\n");
                    }
                    break;

                case "list":
                    data.printData();
                    break;

                case "count":
                    System.out.print("Введите номер группы: ");
                    number = Integer.parseInt(sc.next());
                    if (!checkGroup(number)) {
                        System.out.println("Группа не найдена.\n");
                        break;
                    }
                    int count = 0;
                    String[][] students = system.getStudents().getData();
                    for (int i = 0; i < students.length; i++) {
                        if (students[i][1].equals(number)) {
                            count++;
                        }
                    }
                    System.out.println("Кол-во студентов в группе №" + number + ": " + count + "\n");
                    break;

                case "get":
                    System.out.print("Введите номер группы: ");
                    number = Integer.parseInt(sc.next());
                    if (number < 0 || number >= data.getCount()) {
                        System.out.println("Группа не найдена.\n");
                        break;
                    } else {
                        System.out.println(data.toString(number));
                    }
                    break;

                case "stat":
                    int[] n = new int[data.getCount()];
                    int min = n[0];
                    int max = n[0];
                    for (int i = 1; i < n.length; i++) {
                        if (n[i] > max) {
                            max = n[i];
                        }
                        if (n[i] < min) {
                            min = n[i];
                        }
                    }
                    float mid = system.getStudents().getCount() / data.getCount();
                    System.out.println("Кол-во студентов в группах:\n"
                            + "Минимальное - " + min + "\n"
                            + "Максимальное - " + max + "\n"
                            + "Среднее - " + mid + "\n");
                    break;
                case "student":
                    System.out.print("Введите ФИО студента:");
                    str = sc.nextLine();
                    System.out.println("Поиск по совпадениям:");
                    String[][] arr = system.getStudents().getData();
                    for (int i = 0; i < arr.length; i++) {
                        if (arr[i][0].contains(str)) {
                            System.out.println(system.getStudents().toString(i));
                        }
                    }
                    System.out.println("Поиск завершен.\n");
                    break;

                default:
                    System.out.println("\"" + str + "\" не является командой, исполняемой программой.\n");
            }
        }

        if (isChanged) {
            data.setData(array);
            data.upload();
        }
    }

    private String[] createStudent() {
        String[] item = new String[3];

        System.out.print("ФИО студента: ");
        item[0] = sc.next();

        System.out.print("Группа: ");
        item[1] = sc.next();

        System.out.print("Дата зачисления: ");
        item[2] = sc.next();

        return item;
    }

    private String[] createGroup() {
        String[] item = new String[2];

        System.out.print("Номер группы: ");
        item[0] = sc.next();

        System.out.print("Факультет: ");
        item[1] = sc.next();

        return item;
    }

    private boolean checkGroup(int n) {
        String[][] groups = system.getGroups().getData();
        for (int i = 0; i < groups.length; i++) {
            if (groups[i].equals(n)) {
                return true;
            }
        }
        return false;
    }
}
