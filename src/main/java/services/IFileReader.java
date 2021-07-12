package services;

import java.util.List;

public interface IFileReader {
    /**
     * Reads file at path.
     * Assume file is line separated list of names (and nothing else)
     *
     * @param path - must be a path in resourhces
     * @return - returns a list of strings of lastnames
     */
    List<String> readFile(String path);
}
