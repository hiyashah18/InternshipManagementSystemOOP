package exception;

public class InternshipNotFoundException extends InternshipSystemException {
    private static final long serialVersionUID = 1L;

    public InternshipNotFoundException(String message) {
        super(message);
    }
}
