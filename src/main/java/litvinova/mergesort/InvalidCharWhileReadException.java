package litvinova.mergesort;

public class InvalidCharWhileReadException extends Exception {
    public InvalidCharWhileReadException(String string, String fileName, int stringNumber) {
        System.err.println("Недопустимый символ при чтении файла " + fileName + " в строке "
                + stringNumber + ": '" + string + "'" + ". Данный файл был считан до этой строки");
    }
}
