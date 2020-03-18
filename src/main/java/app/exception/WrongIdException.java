package app.exception;

public class WrongIdException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Object with such id is not found!";
    }
}
