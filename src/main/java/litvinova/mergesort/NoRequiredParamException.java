package litvinova.mergesort;

public class NoRequiredParamException extends Exception{
    public NoRequiredParamException(String string) {
        System.err.println("Отсутствует или недопустимый параметр\n\t" + string);
    }
}
