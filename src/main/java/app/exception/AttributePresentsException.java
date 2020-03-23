package app.exception;

public class AttributePresentsException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Such attribute already presents for this point!";
    }
}
