package exception;

/** Thrown when a data file cannot be read or written (file-related errors). */
public class DataStorageException extends InternshipSystemException {
    private static final long serialVersionUID = 1L;

    public DataStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
