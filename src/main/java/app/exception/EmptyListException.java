package app.exception;

public class EmptyListException extends RuntimeException {
    @Override
    public String getMessage() {
        return "No existing elements!";
    }
}
