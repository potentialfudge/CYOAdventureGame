package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestStory {
    private Story testStory;
    private StoryBoard board1;
    private StoryBoard board2;
    
    @BeforeEach
    void runBefore() {
        testStory = new Story();
    }

    @Test
    void testConstructor() {
        assertNotNull(testStory);
        assertNotNull(testStory.getBoardFromId(1));
        assertNotNull(testStory.getBoardFromId(2));
    }

    @Test
    void testGetBoardFromValidId() {
        board1 = testStory.getBoardFromId(1);
        board2 = testStory.getBoardFromId(2);
        assertEquals(board1, testStory.getBoardFromId(1));
        assertEquals(board2, testStory.getBoardFromId(2));
    }

    @Test
    void testGetBoardFromInvalidId() {;
        assertNull(testStory.getBoardFromId(-2));
        assertNull(testStory.getBoardFromId(51));
    }

}
