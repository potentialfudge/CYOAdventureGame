package model;

import java.util.List;

// Represents a story board with an id, description, choices and 
// whether it has a word guesser or not
public class StoryBoard {
    private int id;
    private String description;
    private List<Choice> choices;
    private boolean hasWordGuesser;

    /*
     * REQUIRES: length of choices cannot be more than 2
     * EFFECTS: constructs a StoryBoard with given description,
     * list of possible choices and whether it has a word guesser or not
     */
    public StoryBoard(int id, String description, List<Choice> choices, boolean hasWordGuesser) {
        // stub
    }

    public int getId() {
        return 0;
    }

    public String getDescription() {
        return "";
    }

    public List<Choice> getChoices() {
        return null;
    }

    public boolean hasWordGuesser() {
        return false;
    }
}
