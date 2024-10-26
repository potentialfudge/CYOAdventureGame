package persistence;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Choice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class JSONWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JSONWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer and throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes a JSON representation of the current game state to file
    public void writeCurrentState(int currentBoardId, List<Choice> choiceHistory) {
        JSONObject json = new JSONObject();
        json.put("currentBoardId", currentBoardId);

        JSONArray jsonChoiceHistory = new JSONArray();
        for (Choice choice : choiceHistory) {
            jsonChoiceHistory.put(choice.toJson());
        }
        json.put("choiceHistory", jsonChoiceHistory);

        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
