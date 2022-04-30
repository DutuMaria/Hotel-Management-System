package exception;

public class InvalidUsernameException extends Exception {
    public InvalidUsernameException() {
    }

    public InvalidUsernameException(String message) {
        super(message);
    }
}
