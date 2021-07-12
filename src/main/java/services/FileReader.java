package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader implements IFileReader {


    public List<String> readFile(String path) {

        ArrayList<String> data = new ArrayList<>();
        URL resource = getClass().getClassLoader().getResource(path);
        if (resource == null) {
            return data;
        }
        else {
            try {
                File file = new File(resource.toURI());
                Scanner reader = new Scanner(file);

                while (reader.hasNextLine()) {
                    data.add(reader.nextLine());
                }
            } catch (FileNotFoundException | URISyntaxException e) {
                e.printStackTrace();
            }
            return data;
        }
    }
}
