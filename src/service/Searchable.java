package service;

import java.util.List;

/** INTERFACE (also GENERIC): anything that can be searched by a keyword. */
public interface Searchable<T> {
    List<T> search(String keyword);
}
