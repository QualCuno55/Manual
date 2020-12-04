package helpsystem;

import java.io.*;
import java.util.*;

public class DataBase {

    private final String file;
    private String[][] array;

    public DataBase(String file) {
        this.file = file;
        download();
    }

    public String getFile() {
        return file;
    }

    public String[][] getData() {
        if (isCorrect()) {
            return arrayClone(array);
        } else {
            return null;
        }
    }

    public void setData(String[][] array) {
        this.array = arrayClone(array);
    }

    public int getCount() {
        if (isCorrect()) {
            return array.length;
        } else {
            return 0;
        }
    }

    public boolean isCorrect() {
        if (array != null) {
            return true;
        } else {
            return false;
        }
    }

    public void download() {
        array = getArray(read());
    }

    public boolean upload() {
        if (isCorrect()) {
            return write(getString(array));
        } else {
            return false;
        }
    }

    public void printData() {
        if (isCorrect()) {
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    System.out.print(array[i][j] + " ");
                }
                System.out.println("");
            }
            System.out.println("");
        } else {
            System.out.println("Data not found.");
        }
    }

    @Override
    public String toString() {
        if (isCorrect()) {
            return getString(array);
        } else {
            return "Data not found.";
        }
    }

    public String toString(int number) {
        String[] str = get(number);
        String string = "";
        for (int i = 0; i < str.length; i++) {
            string += str[i] + " ";
        }
        return string;
    }
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

//ЧТЕНИЕ ИЗ ФАЙЛА 
    private String read() {
        String buffer, data = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
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

//ЗАПИСЬ В ЧИСТЫЙ ФАЙЛ
    private boolean write(String data) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
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
//ПРЕОБРАЗОВАНИЕ СТРОКИ В МАССИВ
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

//ПРЕОБРАЗОВАНИЕ МАССИВА В СТРОКУ
    private String getString(String[][] array) {
        if (array == null) {
            return null;
        }

        String data = "";

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                data += array[i][j];
                if (j != (array[i].length - 1)) {
                    data += "#";
                } else {
                    data += "\r\n";
                }
            }
        }

        return data;
    }

//КЛОНИРОВАНИЕ МАССИВА
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

    private String[] arrayClone(String[] array) {
        String[] clone = new String[array.length];

        for (int i = 0; i < array.length; i++) {
            clone[i] = new String(array[i]);
        }

        return clone;
    }
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

    //ДОБАВЛЕНИЕ
    public boolean add(String[] code, int index) {

        if (code == null) {
            return false;
        }

        if (index < 0) {
            index = 0;
        }
        if (index > array.length) {
            index = array.length;
        }

        if (!isCorrect()) {
            array = new String[][]{arrayClone(code)};
            return true;
        }

        String[][] arr = new String[array.length + 1][];

        for (int i = 0, k = 0; i < array.length; i++) {
            if (i == index) {
                arr[i] = arrayClone(code);
                continue;
            }

            arr[i] = array[k];
            k++;
        }

        array = arr;
        arr = null;
        return true;
    }

//УДАЛЕНИЕ
    public boolean delete(int index) {
        if (!isCorrect()) {
            return false;
        }

        if (index >= array.length) {
            return false;
        }
        if (index < 0) {
            return false;
        }

        String[][] arr = new String[array.length - 1][];

        for (int i = 0, k = 0; i < array.length; i++) {
            if (i == index) {
                continue;
            }

            arr[i] = array[k];
            k++;
        }

        array = arr;
        return true;
    }

//РЕДАКТИРОВАНИЕ
    public boolean set(String[] code, int index) {
        if (!isCorrect()) {
            return false;
        }
        if (code == null) {
            return false;
        }

        if (index >= array.length) {
            return false;
        }
        if (index < 0) {
            return false;
        }

        array[index] = arrayClone(code);

        return true;
    }

//ПОЛУЧЕНИЕ ЭЛЕМЕНТА
    public String[] get(int index) {
        if (isCorrect()) {
            return null;
        }
        if (index >= array.length) {
            return null;
        }
        if (index < 0) {
            return null;
        }

        return arrayClone(array[index]);
    }

//ПОИСК ЭЛЕМЕНТА
    public String[][] search(String code) {
        ArrayList<String[]> list = new ArrayList<>();

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j].contains(code)) {
                    list.add(array[i]);
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
}
