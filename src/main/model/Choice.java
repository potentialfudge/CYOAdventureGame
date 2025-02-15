package model;

import org.json.JSONObject;

// Represents a choice with a description and a next node
public class Choice {
    private String description;
    private int nextBoardId;

    /*
     * EFFECTS: constructs a Choice with given description and the ID to the
     * StoryBoard it leads to
     */
    public Choice(String description, int nextBoardId) {
        this.description = description;
        this.nextBoardId = nextBoardId;
    }

    public String getDescription() {
        return description;
    }

    public int getNextBoardId() {
        return nextBoardId;
    }

    // EFFECTS: converts choice to a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("description", description);
        json.put("nextBoardId", nextBoardId);
        return json;
    }
}
