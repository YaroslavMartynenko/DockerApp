package app.exception;

public class PointExistsException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Point with such coordinates already exists!";
    }
}
