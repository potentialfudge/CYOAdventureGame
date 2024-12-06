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

    // MODIFIES: this
    // EFFECTS: clears choice history
    public void clearHistory() {
        choices.clear();
    }

    // EFFECTS: returns list of choices taken so far, returns null if no choices
    // taken yet
    public List<Choice> getChoices() {
        return choices;
    }

    // MODIFIES: this
    // EFFECTS: removes a choice at the specified index, if valid
    public boolean removeChoiceAt(int index) {
        if (index >= 0 && index < choices.size()) {
            choices.remove(index);
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: removes a choice by its description, if found
    public boolean removeChoiceByDescription(String description) {
        return choices.removeIf(choice -> choice.getDescription().equals(description));
    }
}
