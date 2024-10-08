package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestStory {
    private Story testStory;
    private StoryBoard board0;
    private StoryBoard board1;
    
    @BeforeEach
    void runBefore() {
        testStory = new Story();
    }

    @Test
    void testConstructor() {
        assertNotNull(testStory);
        assertNotNull(testStory.getBoardFromId(0));
        assertNotNull(testStory.getBoardFromId(1));
    }

    @Test
    void testGetBoardFromValidId() {
        board0 = testStory.getBoardFromId(0);
        board1 = testStory.getBoardFromId(1);
        assertEquals(board0, testStory.getBoardFromId(0));
        assertEquals(board1, testStory.getBoardFromId(1));
    }

    @Test
    void testGetBoardFromInvalidId() {;
        assertNull(testStory.getBoardFromId(-2));
        assertNull(testStory.getBoardFromId(51));
    }

}
