package exception;

public class DuplicateApplicationException extends InternshipSystemException {
    private static final long serialVersionUID = 1L;

    public DuplicateApplicationException(String message) {
        super(message);
    }
}
