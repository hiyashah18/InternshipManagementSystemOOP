package service;

import exception.InternshipSystemException;

import java.util.List;

/** INTERFACE (GENERIC): common add / remove / list operations for any record type. */
public interface Manageable<T> {
    void add(T item) throws InternshipSystemException;

    boolean remove(String id) throws InternshipSystemException;

    T findById(String id);

    List<T> getAll();
}
