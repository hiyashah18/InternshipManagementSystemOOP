package service;

import exception.InternshipSystemException;
import model.Application;

/** INTERFACE: anything that lets a student apply for an internship. */
public interface Applicable {
    Application apply(String studentId, String internshipId) throws InternshipSystemException;
}
