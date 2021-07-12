package services;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class FileReaderTests {

    private FileReader service = new FileReader();

    @Test
    public void read_test() {
        List<String> names = service.readFile("test.txt");

        Assert.assertNotNull(names);
        Assert.assertTrue(!names.isEmpty());
        Assert.assertTrue(names.contains("smith"));
        Assert.assertTrue(names.contains("johnson"));
    }
}
