package util;

import exception.InvalidInputException;

/** Central place for input validation; throws InvalidInputException on bad data. */
public final class Validator {

    private Validator() { }

    public static String requireText(String value, String field) throws InvalidInputException {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidInputException(field + " cannot be empty.");
        }
        return value.trim();
    }

    public static String requireEmail(String value) throws InvalidInputException {
        String v = requireText(value, "Email");
        if (!v.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")) {
            throw new InvalidInputException("Please enter a valid email address.");
        }
        return v.toLowerCase();
    }

    public static String requirePassword(String value) throws InvalidInputException {
        if (value == null || value.length() < 4) {
            throw new InvalidInputException("Password must be at least 4 characters long.");
        }
        return value;
    }

    public static int requireInRange(int value, int min, int max, String field) throws InvalidInputException {
        if (value < min || value > max) {
            throw new InvalidInputException(field + " must be between " + min + " and " + max + ".");
        }
        return value;
    }

    public static int parseInt(String text, String field) throws InvalidInputException {
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException | NullPointerException e) {
            throw new InvalidInputException(field + " must be a whole number.");
        }
    }
}
