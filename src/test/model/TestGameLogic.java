package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestGameLogic {
    private GameLogic testGameLogic;

    @BeforeEach
    void setUp() {
        testGameLogic = new GameLogic();
    }

    @Test
    void testStartNewGame() {
        testGameLogic.startNewGame();
        assertNotNull(testGameLogic.getCurrentBoardDescription());
        assertEquals(1, testGameLogic.getCurrentBoardId());
        assertEquals(2, testGameLogic.getCurrentBoardChoices().size());
        assertEquals(0, testGameLogic.getChoiceHistory().getChoices().size());
    }

    @Test
    void testMakeChoice() {
        testGameLogic.startNewGame();
        int initialBoardId = testGameLogic.getCurrentBoardId();
        testGameLogic.makeChoice(0);
        int newBoardId = testGameLogic.getCurrentBoardId();
        assertNotEquals(initialBoardId, newBoardId);
    }

    @Test
    void testSuccessWordGuess() {
        testGameLogic.startNewGame();
        testGameLogic.makeChoice(0);
        testGameLogic.makeChoice(1);
        assertTrue(testGameLogic.hasWordGuesser());
        testGameLogic.proceedSuccessfulWordGuesser();
        assertEquals(7, testGameLogic.getCurrentBoardId());
        assertEquals(3, testGameLogic.getChoiceHistory().getChoices().size());
    }

    @Test
    void testFailedWordGuess() {
        testGameLogic.startNewGame();
        testGameLogic.makeChoice(0);
        testGameLogic.makeChoice(1);
        assertTrue(testGameLogic.hasWordGuesser());
        testGameLogic.proceedFailedWordGuesser();
        assertEquals(5, testGameLogic.getCurrentBoardId());
        assertEquals(3, testGameLogic.getChoiceHistory().getChoices().size());
        assertTrue(testGameLogic.isGameOver());
    }

    @Test
    void testLoadSavedGame() {
        testGameLogic.startNewGame();
        testGameLogic.makeChoice(0);
        testGameLogic.saveGame();
        assertTrue(testGameLogic.loadGame());
        assertEquals(3, testGameLogic.getCurrentBoardId());
    }

    @Test
    void testLoadNoGame() {
        testGameLogic.startNewGame();
        File saveFile = new File("./data/saveState.json");
        saveFile.delete();
        assertFalse(testGameLogic.loadGame());
        assertEquals(1, testGameLogic.getCurrentBoardId());
    }

    @Test
    void removeValidChoiceDescription() {
        testGameLogic.startNewGame();
        testGameLogic.makeChoice(0);
        testGameLogic.makeChoice(1);
        testGameLogic.removeChoiceByDescription("Go into ruins");
        assertEquals(1, testGameLogic.getChoiceHistory().getChoices().size());
        assertEquals("Go down the stairs", testGameLogic.getChoiceHistory().getChoices().get(0).getDescription());
    }
}
