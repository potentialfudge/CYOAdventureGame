package model;

import java.util.ArrayList;
import java.util.List;

// Represents a choice history (list of choices) with empty list of choices initially
public class ChoiceHistory {
    private List<Choice> choices;

    public ChoiceHistory() {
        this.choices = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds choice to the choice history
    public void addHistory(Choice choice) {
        choices.add(choice);
    }

    // EFFECTS: returns list of choices taken so far, returns null if no choices
    // taken yet
    public List<Choice> getChoices() {
        return choices;
    }
}
