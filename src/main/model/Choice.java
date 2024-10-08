package model;


// Represents a choice with a description and a next node
public class Choice {
    private String description;
    private int nextNodeId;

    /*
     * EFFECTS: constructs a Choice with given description and the ID to the StoryBoard it leads to
     */
    public Choice(String description, int nextNodeId) {
        this.description = description;
        this.nextNodeId = nextNodeId;
    }

    public String getDescription() {
        return description;
    }

    public int getNextNodeId() {
        return nextNodeId;
    }
}
