package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistence.JsonReader;
import persistence.JsonReaderLoader;
import persistence.JsonWriter;

// Represents the logic of the game
public class GameLogic {
    private Map<Integer, StoryBoard> story = new HashMap<>();
    private StoryBoard currentBoard;
    private ChoiceHistory choiceHistory = new ChoiceHistory();
    private Gamestate gameState;
    private static final String JSON_STORE = "./data/storyGraph.json";
    private static final String JSON_SAVE = "./data/saveState.json";

    public GameLogic() {
        loadStoryBoards();
        currentBoard = story.get(1);
    }

    // MODIFIES: this
    // EFFECTS: loads storyboards from JSON
    private void loadStoryBoards() {
        JsonReader jsonReader = new JsonReader(JSON_STORE);
        try {
            story = jsonReader.read("storyBoards");
        } catch (IOException e) {
            System.err.println("Failed to load game: " + e.getMessage());
        }
    }

    // MODIFIES: this
    // EFFECTS: starts new game from beginning
    public void startNewGame() {
        choiceHistory.clearHistory();
        currentBoard = story.get(1);
    }

    // MODIFIES: this
    // EFFECTS: updates the game state based on player's choice and returns next
    // board id
    public int makeChoice(int choiceIndex) {
        Choice selectedChoice = currentBoard.getChoices().get(choiceIndex);
        choiceHistory.addHistory(selectedChoice);
        currentBoard = getBoardFromId(selectedChoice.getNextBoardId());
        return currentBoard.getId();
    }

    // MODIFIES: this
    // EFFECTS: moves to the next storyboard if word guesser has been successful
    public void proceedSuccessfulWordGuesser() {
        Choice wordChoice = currentBoard.getChoices().get(0);
        choiceHistory.addHistory(wordChoice);
        currentBoard = getBoardFromId(wordChoice.getNextBoardId());
    }

    // MODIFIES: this
    // EFFECTS: moves to the next storyboard if word guesser has been unsuccessful,
    // i.e. user did not guess correct word after 6 tries
    public void proceedFailedWordGuesser() {
        Choice wordChoice = currentBoard.getChoices().get(1);
        choiceHistory.addHistory(wordChoice);
        currentBoard = getBoardFromId(wordChoice.getNextBoardId());
    }

    // MODIFIES: this
    // EFFECTS: loads the saved game state from file and returns whether it was able
    // to load or not
    public boolean loadGame() {
        JsonReaderLoader jsonReaderLoader = new JsonReaderLoader(JSON_SAVE);
        try {
            gameState = jsonReaderLoader.read();
            currentBoard = getBoardFromId(gameState.getCurrentBoardId());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // EFFECTS: returns the current board id
    public int getCurrentBoardId() {
        return currentBoard.getId();
    }

    // EFFECTS: returns the current board description
    public String getCurrentBoardDescription() {
        return currentBoard.getDescription();
    }

    // EFFECTS: gets the board from the story by id
    private StoryBoard getBoardFromId(int id) {
        return story.get(id);
    }

    // EFFECTS: gets the current board's available choices
    public List<Choice> getCurrentBoardChoices() {
        return currentBoard.getChoices();
    }

    // EFFECTS: returns true if the current board has a word guesser game
    public boolean hasWordGuesser() {
        return currentBoard.hasWordGuesser();
    }

    // EFFECTS: checks if the game is over, i.e the current board has no choices
    public boolean isGameOver() {
        return currentBoard.getChoices().isEmpty();
    }

    // EFFECTS: returns the player's choice history
    public ChoiceHistory getChoiceHistory() {
        return choiceHistory;
    }

    // MODIFIES: this
    // EFFECTS: handles game save functionality
    public void saveGame() {
        JsonWriter jsonWriter = new JsonWriter("data/saveState.json");
        try {
            jsonWriter.open();
            jsonWriter.writeCurrentState(
                    choiceHistory.getChoices().get(choiceHistory.getChoices().size() - 1).getNextBoardId(),
                    choiceHistory.getChoices());
            jsonWriter.close();
        } catch (IOException e) {
            System.err.println("Failed to save game: " + e.getMessage());
        }
    }

    // MODIFIES: this
    // EFFECTS: removes choice from choice history given description, if found
    public void removeChoiceByDescription(String description) {
        choiceHistory.removeChoiceByDescription(description);
    }
}
