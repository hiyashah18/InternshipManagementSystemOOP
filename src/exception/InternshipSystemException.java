package exception;

/** Base class of all custom (checked) exceptions in the system. */
public class InternshipSystemException extends Exception {
    private static final long serialVersionUID = 1L;

    public InternshipSystemException(String message) {
        super(message);
    }

    public InternshipSystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
