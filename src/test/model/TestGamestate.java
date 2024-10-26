package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestGamestate {
    private Gamestate testGamestate;
    private List<Choice> testChoices;
    private Choice choice1;
    private Choice choice2;

    @BeforeEach
    void runBefore() {
        testChoices = new ArrayList<>();
        choice1 = new Choice("Go left", 2);
        choice2 = new Choice("Go right", 3);
        testChoices.add(choice1);
        testChoices.add(choice2);
        testGamestate = new Gamestate(1, testChoices);
    }

    @Test
    void testConstructor() {
        assertEquals(1, testGamestate.getCurrentBoardId());
        assertEquals(2, testGamestate.getChoiceHistory().size());
        assertEquals("Go left", testGamestate.getChoiceHistory().get(0).getDescription());
        assertEquals("Go right", testGamestate.getChoiceHistory().get(1).getDescription());
    }
}
