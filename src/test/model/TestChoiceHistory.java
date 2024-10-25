package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestChoiceHistory {
    private Choice c1;
    private Choice c2;
    private ChoiceHistory testHistory;
    
    @BeforeEach
    void runBefore() {
        c1 = new Choice("Try to open the door", 2);
        c1 = new Choice("Try to climb up the wall", 5);
        testHistory = new ChoiceHistory();
    }

    @Test
    void testConstructor() {
        assertTrue(testHistory.getChoices().isEmpty());
    }

    @Test
    void addOneChoice() {
        testHistory.addHistory(c1);
        assertEquals(c1, testHistory.getChoices().get(0));
    }

    @Test
    void addTwoChoices() {
        testHistory.addHistory(c1);
        testHistory.addHistory(c2);
        assertEquals(c1, testHistory.getChoices().get(0));
        assertEquals(c2, testHistory.getChoices().get(1));
    }
}
