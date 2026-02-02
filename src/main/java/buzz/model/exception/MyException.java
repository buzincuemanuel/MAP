package buzz.model.exception;

public class MyException extends Exception {
    public MyException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "MyException: " + getMessage();
    }
}
