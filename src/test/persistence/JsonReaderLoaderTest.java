package persistence;

import org.junit.jupiter.api.Test;

import model.Gamestate;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderLoaderTest {
    private Gamestate testStory;

    @Test
    void testReaderLoaderNonExistentFile() {
        JsonReaderLoader readerLoader = new JsonReaderLoader("./data/noSuchFile.json");
        try {
            testStory = readerLoader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderLoaderStartState() {
        JsonReaderLoader readerLoader = new JsonReaderLoader("./data/testReaderLoaderSaveState.json");
        try {
            testStory = readerLoader.read();
            assertEquals(2, testStory.getCurrentBoardId());
            assertEquals(1, testStory.getChoiceHistory().size());
            assertEquals("Go into ruins", testStory.getChoiceHistory().get(0).getDescription());
            assertEquals(2, testStory.getChoiceHistory().get(0).getNextBoardId());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
