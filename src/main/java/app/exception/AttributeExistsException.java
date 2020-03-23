package app.exception;

public class AttributeExistsException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Attribute with such name already exists!";
    }
}
