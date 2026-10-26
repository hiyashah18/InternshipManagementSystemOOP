package exception;

public class AuthenticationException extends InternshipSystemException {
    private static final long serialVersionUID = 1L;

    public AuthenticationException(String message) {
        super(message);
    }
}
