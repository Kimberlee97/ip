package lumi.storage;

import lumi.exceptions.LumiException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link Storage} class
 * Ensures that invalid or corrupted task files are handled correctly
 */
public class StorageTest {
    /**
     * Tests that a file with an invalid statement correctly throws a {@link LumiException}
     * when the load attempt is made.
     */
    @Test
    public void loadTest() {
        Storage storage = new Storage("./data/test.txt");
        assertThrows(LumiException.class, storage::load);
    }
}
