package litvinova.mergesort;

public class DataIsNotSortedException extends Exception{
    public DataIsNotSortedException(String fileName, int stringNumber) {
        System.err.println("Данные в файле " + fileName + " не упорядочены, начиная со строки " + stringNumber
                + ". Данный файл был считан до этой строки");
    }
}
