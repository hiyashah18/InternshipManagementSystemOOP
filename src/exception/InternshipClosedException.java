package exception;

public class InternshipClosedException extends InternshipSystemException {
    private static final long serialVersionUID = 1L;

    public InternshipClosedException(String message) {
        super(message);
    }
}
