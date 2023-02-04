package litvinova.mergesort;

import java.io.*;
import java.util.ArrayList;

public class MergeSortInt {
    public static void Sort(String[] fileNames, int k, int sortMode) throws NoRequiredParamException {
        var arr1 = new int[0];
        if((fileNames.length - k) >= 2) {
            String outFile = fileNames[k];
            k++;

            for (int i = k; i < fileNames.length; i++) {
                var arr2 = readFromFile(fileNames[i], sortMode);

                if (sortMode == 0) {
                    arr1 = MergeInc(arr1, arr2);
                } else if (sortMode == 1) {
                    arr1 = MergeDec(arr2, arr1);
                }
            }
            writeToFile(outFile, arr1);
        } else {
            throw new NoRequiredParamException("Для выполнения сортировки необходим выходной файл и как минимум один входной");
        }
    }

    public static int[] readFromFile(String fileName, int sortMode) {
        var arr2 = new int[0];
        BufferedReader reader = null;
        ArrayList<String> strings = new ArrayList<String>();
        try {
            var file = new File(fileName);
            //создаем объект FileReader для объекта File
            //создаем BufferedReader с существующего FileReader для построчного считывания
            reader = new BufferedReader(new FileReader(file));
            // считаем сначала первую строку
            var line = reader.readLine();
            strings = new ArrayList<String>();
            var stringNumber = 1;
            if (isNumeric(line)) {
                strings.add(line);
            } else {
                throw new InvalidCharWhileReadException(line, fileName, stringNumber);
            }
            line = reader.readLine();
            stringNumber++;
            while (line != null) {
                if (isNumeric(line)) {
                    if (isSorted(Integer.parseInt(strings.get(strings.size() - 1)), Integer.parseInt(line), sortMode))
                        strings.add(line);
                    else {
                        throw new DataIsNotSortedException(fileName, stringNumber);
                    }
                } else {
                    throw new InvalidCharWhileReadException(line, fileName, stringNumber);
                }
                line = reader.readLine();
                stringNumber++;
            }
        } catch (FileNotFoundException e) {
            System.err.println("Файл " + fileName + " не найден! Сортировка выполнена без данного файла");
        } catch (IOException e) {
            System.err.println("Ошибка при чтении данных из файла " + fileName + "! Сортировка выполнена без данного файла");
        } catch (InvalidCharWhileReadException | DataIsNotSortedException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            arr2 = new int[strings.size()];
            int j = 0;
            for (String string : strings) {
                arr2[j++] = Integer.parseInt(string);
            }
            return arr2;
        }
    }

    public static boolean isNumeric(String string) {
        int intValue;

        if (string == null || string.equals("")) {
            return false;
        }

        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            System.err.println("Считанная строка не может быть преобразована к числу");

        }
        return false;
    }

    private static boolean isSorted(int number1, int number2, int sortMode) {
        if(sortMode == 0) {
            return number1 <= number2;
        }
        else {
            return number1 >= number2;
        }
    }

    public static int[] MergeInc(int[] arr1, int[] arr2) {

        int[] res = new int[arr1.length + arr2.length];

        int pos1 = 0;
        int pos2 = 0;
        int i = 0;

        while (pos1 < arr1.length && pos2 < arr2.length) {
            if (arr1[pos1] < arr2[pos2]) {
                res[i++] = arr1[pos1++];
            } else {
                res[i++] = arr2[pos2++];
            }
        }
        while (pos1 < arr1.length) {
            res[i++] = arr1[pos1++];
        }
        while (pos2 < arr2.length) {
            res[i++] = arr2[pos2++];
        }

        return res;
    }

    public static int[] MergeDec(int[] arr1, int[] arr2) {

        int[] res = new int[arr1.length + arr2.length];

        int pos1 = 0;
        int pos2 = 0;
        int i = 0;

        while (pos1 < arr1.length && pos2 < arr2.length) {
            if (arr1[pos1] > arr2[pos2]) {
                res[i++] = arr1[pos1++];
            } else {
                res[i++] = arr2[pos2++];
            }
        }
        while (pos1 < arr1.length) {
            res[i++] = arr1[pos1++];
        }
        while (pos2 < arr2.length) {
            res[i++] = arr2[pos2++];
        }

        return res;
    }

    public static void writeToFile(String fileName, int[] arr1) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            String lineSeparator = System.getProperty("line.separator");
            for (int number : arr1) {
                writer.write(number + lineSeparator);
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.err.println("Ошибка при записи данных в файл!");
            e.printStackTrace();
        }
    }
}
