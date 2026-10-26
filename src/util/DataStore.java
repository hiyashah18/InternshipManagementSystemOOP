package util;

import exception.DataStorageException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * GENERIC file handler: saves and loads a list of ANY serializable type T.
 * Example: new DataStore<Student>("data/students.dat")
 */
public class DataStore<T extends Serializable> {

    private final File file;

    public DataStore(String fileName) {
        this.file = new File(fileName);
    }

    public void save(List<T> items) throws DataStorageException {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(new ArrayList<T>(items));
        } catch (IOException e) {
            throw new DataStorageException("Could not save data to " + file.getName(), e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> load() throws DataStorageException {
        if (!file.exists()) {
            return new ArrayList<T>();          // first run: no file yet
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<T>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new DataStorageException("Could not read data from " + file.getName()
                    + ". Delete the file in the data folder to reset.", e);
        }
    }
}
