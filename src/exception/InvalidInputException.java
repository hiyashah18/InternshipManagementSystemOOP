package exception;

public class InvalidInputException extends InternshipSystemException {
    private static final long serialVersionUID = 1L;

    public InvalidInputException(String message) {
        super(message);
    }
}
