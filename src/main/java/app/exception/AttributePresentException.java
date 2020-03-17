package app.exception;

public class AttributePresentException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Such attribute already present for this point!";
    }
}
