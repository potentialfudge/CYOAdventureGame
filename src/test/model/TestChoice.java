package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestChoice {
    private Choice testChoice;
    
    @BeforeEach
    void runBefore() {
        testChoice = new Choice("Try to open the door", 2);
    }

    @Test
    void testConstructor() {
        assertEquals("Try to open the door", testChoice.getDescription());
        assertEquals(2, testChoice.getNextBoardId());
    }
}
