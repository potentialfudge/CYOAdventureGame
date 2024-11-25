package persistence;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Gamestate;
import model.Choice;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {
    private Gamestate gamestate;

    @BeforeEach
    void setUp() {
        gamestate = new Gamestate(0, null);
    }

    @Test
    void testWriterInvalidFile() {
        JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
        assertThrows(IOException.class, writer::open, "IOException was expected");
    }

    @Test
    void testWriterSaveState() {
        JsonWriter writer = new JsonWriter("./data/testWriterSaveState.json");
        JsonReaderLoader readerLoader = new JsonReaderLoader("./data/testWriterSaveState.json");
        try {
            List<Choice> testHistory = new ArrayList<>();
            testHistory.add(new Choice("Go left", 3));
            testHistory.add(new Choice("Go right", 4));
            writer.open();
            writer.writeCurrentState(5, testHistory);
            writer.close();

            gamestate = readerLoader.read();
            assertEquals(5, gamestate.getCurrentBoardId());
            assertEquals(2, gamestate.getChoiceHistory().size());
            assertEquals("Go left", gamestate.getChoiceHistory().get(0).getDescription());
            assertEquals(3, gamestate.getChoiceHistory().get(0).getNextBoardId());
            assertEquals("Go right", gamestate.getChoiceHistory().get(1).getDescription());
            assertEquals(4, gamestate.getChoiceHistory().get(1).getNextBoardId());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
