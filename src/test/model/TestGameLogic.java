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
        assertTrue(testGameLogic.hasWordGuesser());
        testGameLogic.proceedSuccessfulWordGuesser();
        assertEquals(4, testGameLogic.getCurrentBoardId());
        assertTrue(testGameLogic.isGameOver());
    }

    @Test
    void testFailedWordGuess() {
        testGameLogic.startNewGame();
        testGameLogic.makeChoice(0);
        assertTrue(testGameLogic.hasWordGuesser());
        testGameLogic.proceedFailedWordGuesser();
        assertEquals(5, testGameLogic.getCurrentBoardId());
        assertTrue(testGameLogic.isGameOver());
    }

    @Test
    void testLoadSavedGame() {
        testGameLogic.startNewGame();
        testGameLogic.makeChoice(0);
        testGameLogic.saveGame();
        assertTrue(testGameLogic.loadGame());
        assertEquals(2, testGameLogic.getCurrentBoardId());
    }

    @Test
    void testLoadNoGame() {
        testGameLogic.startNewGame();
        File saveFile = new File("./data/saveState.json");
        saveFile.delete();
        assertFalse(testGameLogic.loadGame());
        assertEquals(1, testGameLogic.getCurrentBoardId());
    }
}
