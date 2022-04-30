package exception;

public class RoomDoesntExistException extends Exception {
    public RoomDoesntExistException() {
    }

    public RoomDoesntExistException(String message) {
        super(message);
    }
}
