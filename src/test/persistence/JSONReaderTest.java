package persistence;

import org.junit.jupiter.api.Test;

import model.StoryBoard;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class JSONReaderTest {
    private Map<Integer, StoryBoard> testStory;

    @Test
    void testReaderNonExistentFile() {
        JSONReader reader = new JSONReader("./data/noSuchFile.json");
        try {
            testStory = reader.read("nonExistent");
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderStartState() {
        JSONReader reader = new JSONReader("./data/testReaderStartingState.json");
        try {
            testStory = reader.read("startBoard");
            assertEquals(1, testStory.get(1).getId());
            assertFalse(testStory.get(1).hasWordGuesser());
            assertEquals("Go into ruins", testStory.get(1).getChoices().get(0).getDescription());
            assertEquals("Explore the forest", testStory.get(1).getChoices().get(1).getDescription());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderEndState() {
        JSONReader reader = new JSONReader("./data/testReaderEndState.json");
        try {
            testStory = reader.read("endBoard");
            assertEquals(4, testStory.get(1).getId());
            assertFalse(testStory.get(1).hasWordGuesser());
            assertEquals(0, testStory.get(1).getChoices().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
