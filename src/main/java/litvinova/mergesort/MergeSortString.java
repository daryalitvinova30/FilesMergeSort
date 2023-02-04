package litvinova.mergesort;

import java.io.*;
import java.util.ArrayList;

public class MergeSortString {
    public static void Sort(String[] fileNames, int k, int sortMode) throws NoRequiredParamException {
        var arr1 = new ArrayList<String>();
        if((fileNames.length - k) >= 2) {
            String outFile = fileNames[k];
            k++;

            for (int i = k; i < fileNames.length; i++) {
                var strings = readFromFile(fileNames[i], sortMode);

                if (sortMode == 0) {
                    arr1 = MergeInc(arr1, strings);
                } else if (sortMode == 1) {
                    arr1 = MergeDec(arr1, strings);
                }
            }
            writeToFile(outFile, arr1);
        }
        else {
            throw new NoRequiredParamException("Для выполнения сортировки необходим выходной файл и как минимум один входной");
        }
    }

    public static ArrayList<String> readFromFile(String fileName, int sortMode) {
        var strings = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            var file = new File(fileName);
            //создаем объект FileReader для объекта File
            //создаем BufferedReader с существующего FileReader для построчного считывания
            reader = new BufferedReader(new FileReader(file));
            // считаем сначала первую строку
            var line = reader.readLine();
            var stringNumber = 1;
            if (line.indexOf(' ') == -1) {
                strings.add(line);
            } else {
                throw new InvalidCharWhileReadException(" ", fileName, stringNumber);
            }
            boolean error = false;
            line = reader.readLine();
            stringNumber++;
            while (line != null && !error) {
                if (line.indexOf(' ') == -1) {
                    var previousString = strings.get(strings.size() - 1);
                    if (isSorted(previousString, line, sortMode)) {
                        strings.add(line);
                    } else {
                        throw new DataIsNotSortedException(fileName, stringNumber);
                    }
                } else {
                    throw new InvalidCharWhileReadException(" ", fileName, stringNumber);
                }
                line = reader.readLine();
                stringNumber++;
            }
        } catch (FileNotFoundException e) {
            System.err.println("Файл " + fileName + " не найден! Сортировка выполнена без данного файла");;
        } catch (IOException e) {
            System.err.println("Ошибка при чтении данных из файла " + fileName + "! Сортировка выполнена без данного файла");
        } catch (InvalidCharWhileReadException | DataIsNotSortedException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return strings;
        }
    }

    private static boolean isSorted(String string1, String string2, int sortMode) {
        if(sortMode == 0) {
            return string1.compareTo(string2) <= 0;
        }
        else
            return string2.compareTo(string1) <= 0;
    }

    public static ArrayList<String> MergeInc(ArrayList<String> arr1, ArrayList<String> arr2) {

        var res = new ArrayList<String>();

        int pos1 = 0;
        int pos2 = 0;

        while (pos1 < arr1.size() && pos2 < arr2.size()) {
            if (arr1.get(pos1).compareTo(arr2.get(pos2)) < 0) {
                res.add(arr1.get(pos1++));
            } else {
                res.add(arr2.get(pos2++));
            }
        }

        while (pos1 < arr1.size()) {
            res.add(arr1.get(pos1++));
        }
        while (pos2 < arr2.size()) {
            res.add(arr2.get(pos2++));
        }
        return res;
    }

    public static ArrayList<String> MergeDec(ArrayList<String> arr1, ArrayList<String> arr2) {

        var res = new ArrayList<String>();

        int pos1 = 0;
        int pos2 = 0;

        while (pos1 < arr1.size() && pos2 < arr2.size()) {
            if (arr2.get(pos2).compareTo(arr1.get(pos1)) < 0) {
                res.add(arr1.get(pos1++));
            } else {
                res.add(arr2.get(pos2++));
            }
        }

        while (pos1 < arr1.size()) {
            res.add(arr1.get(pos1++));
        }
        while (pos2 < arr2.size()) {
            res.add(arr2.get(pos2++));
        }
        return res;
    }

    public static void writeToFile(String fileName, ArrayList<String> arr1) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            String lineSeparator = System.getProperty("line.separator");
            for (String string : arr1) {
                writer.write(string + lineSeparator);
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
