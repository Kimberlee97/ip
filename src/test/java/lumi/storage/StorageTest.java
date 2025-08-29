package lumi.storage;

import lumi.exceptions.LumiException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class StorageTest {
    @Test
    public void loadTest() throws IOException, LumiException {
        Storage storage = new Storage("./data/test.txt");
        assertThrows(LumiException.class, storage::load);
    }
}
