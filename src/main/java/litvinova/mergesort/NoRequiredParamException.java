package litvinova.mergesort;

public class NoRequiredParamException extends Exception{
    public NoRequiredParamException(String string) {
        System.err.println("Отсутствует или недопустимый обязательный параметр\n\t" + string);
    }
}
