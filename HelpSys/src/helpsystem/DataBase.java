package helpsystem;

import java.io.*;
import java.util.*;

public class DataBase {

    private final String fileName;
    private String[][] data;



    public void test() {
        System.out.println("Test");
    }

    //Инициализация переменных класса
    public DataBase(String file) {
        this.fileName = file;
        download();
    }

    //Получение имени файла
    public String getFileName() {
        return fileName;
    }

    //Получение массива данных
    public String[][] getData() {
        if (isCorrect()) {
            return arrayClone(data);
        } else {
            return null;
        }
    }

    //Установка новых данных
    public void setData(String[][] array) {
        data = arrayClone(array);
    }

    //Получение длины массива
    public int getCount() {
        if (isCorrect()) {
            return data.length;
        } else {
            return 0;
        }
    }

    //Проверка массива на null
    public boolean isCorrect() {
        if (data != null) {
            return true;
        } else {
            return false;
        }
    }

    //Загрузка данных из файла в массив
    public void download() {
        data = getArray(read());
    }

    //Загрузка данных из массива в файл
    public boolean upload() {
        if (isCorrect()) {
            return write(getString(data));
        } else {
            return false;
        }
    }

    //Печать данных в консоль
    public void printData() {
        if (isCorrect()) {
            for (int i = 0; i < data.length; i++) {

                if (data[i] == null) {
                    System.out.println("null");
                    continue;
                }

                for (int j = 0; j < data[i].length; j++) {
                    if (data[i][j] != null) {
                        System.out.print(data[i][j] + " ");
                    } else {
                        System.out.print("null ");
                    }
                }

                System.out.println("");
            }

            System.out.println("");
        } else {
            System.out.println("Data not found.");
        }
    }

    //Вывод данных в строку
    @Override
    public String toString() {
        if (isCorrect()) {
            return getString(data);
        } else {
            return "Data not found.";
        }
    }

    //Вывод элемента в строку
    public String toString(int number) {
        String[] str = get(number);
        if (str == null) {
            return "Data not found.";
        }

        String string = "";
        for (int i = 0; i < str.length; i++) {
            if (str[i] != null) {
                string += str[i] + " ";
            } else {
                string += "null ";
            }
        }

        return string;
    }
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

    //Чтение из файла
    private String read() {
        String buffer, data = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while ((buffer = reader.readLine()) != null) {
                if (buffer.length() != 0) {
                    buffer = buffer + "\r\n";
                }
                data += buffer;
            }
            reader.close();
            return data;
        } catch (IOException ex) {
            return null;
        }
    }

    //Запись в файл
    private boolean write(String data) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(data + "\r\n");
            writer.flush();
            writer.close();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    //Преобразование строки в массив
    private String[][] getArray(String string) {
        if (string == null) {
            return null;
        }

        String[] strArr = string.split("\r\n");

        String[][] array = new String[strArr.length][];
        for (int i = 0; i < array.length; i++) {
            array[i] = strArr[i].split("#");
        }

        return array;
    }

    //Преобразование массива в строку
    private String getString(String[][] array) {
        if (array == null) {
            return null;
        }

        String data = "";

        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                data += "null\r\n";
                continue;
            }

            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] != null) {
                    data += array[i][j];
                } else {
                    data += "null";
                }

                if (j != (array[i].length - 1)) {
                    data += "#";
                } else {
                    data += "\r\n";
                }
            }
        }

        return data;
    }

    //Клонирование двумерного массива
    private String[][] arrayClone(String[][] array) {
        String[][] clone = new String[array.length][];

        for (int i = 0; i < array.length; i++) {
            clone[i] = new String[array[i].length];
            for (int j = 0; j < array[i].length; j++) {
                clone[i][j] = new String(array[i][j]);
            }
        }

        return clone;
    }

    //Клонирование одномерного массива
    private String[] arrayClone(String[] array) {
        String[] clone = new String[array.length];

        for (int i = 0; i < array.length; i++) {
            clone[i] = new String(array[i]);
        }

        return clone;
    }

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    //Добавление
    public boolean add(String[] code, int index) {
        if (code == null) {
            return false;
        }
        if (!isCorrect()) {
            data = new String[][]{arrayClone(code)};
            return true;
        }

        if (index < 0) {
            index = 0;
        }
        if (index > data.length) {
            index = data.length;
        }

        String[][] array = new String[data.length + 1][];

        for (int i = 0, k = 0; i < array.length; i++) {
            if (i == index) {
                array[i] = arrayClone(code);
                continue;
            }

            array[i] = data[k];
            k++;
        }

        data = array;
        return true;
    }

    //Удаление
    public boolean delete(int index) {
        if (!isCorrect()) {
            return false;
        }
        if (index < 0) {
            return false;
        }
        if (index >= data.length) {
            return false;
        }

        String[][] array = new String[data.length - 1][];

        for (int i = 0, k = 0; i < data.length; i++) {
            if (i == index) {
                continue;
            }

            array[k] = data[i];
            k++;
        }

        data = array;
        return true;
    }

    //Редактирование
    public boolean set(String[] array, int index) {
        if (!isCorrect()) {
            return false;
        }
        if (array == null) {
            return false;
        }

        if (index >= data.length) {
            return false;
        }
        if (index < 0) {
            return false;
        }

        data[index] = arrayClone(array);

        return true;
    }

    //Получение элемента
    public String[] get(int index) {
        if (!isCorrect()) {
            return null;
        }
        if (index >= data.length) {
            return null;
        }
        if (index < 0) {
            return null;
        }

        return arrayClone(data[index]);
    }

    //Поиск
    public String[][] search(String code, boolean isWideSearch) {
        if (!isCorrect()) {
            return null;
        }

        ArrayList<String[]> list = new ArrayList<>();

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (isWideSearch) {
                    if (data[i][j].contains(code)) {
                        list.add(data[i]);
                        break;
                    }
                } else {
                    if (data[i][j].equals(code)) {
                        list.add(data[i]);
                        break;
                    }
                }
            }
        }

        if (list.isEmpty()) {
            return null;
        } else {
            String[][] array = new String[list.size()][];
            for (int i = 0; i < array.length; i++) {
                array[i] = list.get(i);
            }
            return array;
        }
    }

    private void printData(String[][] array) {
        if (array != null) {
            for (int i = 0; i < array.length; i++) {

                if (array[i] == null) {
                    System.out.println("null");
                    continue;
                }

                for (int j = 0; j < array[i].length; j++) {
                    if (array[i][j] != null) {
                        System.out.print(array[i][j] + " ");
                    } else {
                        System.out.print("null ");
                    }
                }

                System.out.println("");
            }

            System.out.println("");
        } else {
            System.out.println("null");
        }
    }
}
