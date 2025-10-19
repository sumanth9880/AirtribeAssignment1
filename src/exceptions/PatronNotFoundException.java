package exceptions;

public class PatronNotFoundException extends Exception {
    public PatronNotFoundException(String message) {
        super(message);
    }
}