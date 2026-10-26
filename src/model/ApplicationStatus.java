package model;

import exception.InvalidInputException;

public enum ApplicationStatus {
    PENDING, ACCEPTED, REJECTED;

    public static ApplicationStatus fromText(String text) throws InvalidInputException {
        try {
            return ApplicationStatus.valueOf(text.trim().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidInputException("Invalid application status.");
        }
    }
}
