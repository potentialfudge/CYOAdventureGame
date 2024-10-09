package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TestStoryBoard {
    private StoryBoard testBoard;
    private StoryBoard testEndingBoard;
    private ArrayList<Choice> testChoices;
    private ArrayList<Choice> emptyChoices;
    private Choice choice1;
    private Choice choice2;
    
    @BeforeEach
    void runBefore() {
        choice1 = new Choice("Try to cross it", 3);
        choice2 = new Choice("Try to walk around it", 4);
        testChoices = new ArrayList<>();
        testChoices.add(choice1);
        testChoices.add(choice2);
        emptyChoices = new ArrayList<>();
        testBoard = new StoryBoard(2, "You come across a river.", testChoices, true);
        testEndingBoard = new StoryBoard(5, "You have discovered the lost treasure! The end.", emptyChoices, false);
    }

    @Test
    void testConstructor() {
        assertEquals(2, testBoard.getId());
        assertEquals("You come across a river.", testBoard.getDescription());
        assertEquals(testChoices, testBoard.getChoices());
        assertEquals(true, testBoard.hasWordGuesser());
    }

    @Test
    void testEndingBoard() {
        assertEquals(0, testEndingBoard.getChoices().size());
    }
}
