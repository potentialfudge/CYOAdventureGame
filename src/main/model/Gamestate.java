package model;

import java.util.List;

// Represents a gamestate with a current board ID and choice history so far
public class Gamestate {
    private int currentBoardId;
    private List<Choice> choiceHistory;

    public Gamestate(int currentBoardId, List<Choice> choiceHistory) {
        this.currentBoardId = currentBoardId;
        this.choiceHistory = choiceHistory;
    }

    public int getCurrentBoardId() {
        return currentBoardId;
    }

    public List<Choice> getChoiceHistory() {
        return choiceHistory;
    }
}
