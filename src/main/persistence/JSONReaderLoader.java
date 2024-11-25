package persistence;

import model.Choice;
import model.Gamestate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.json.*;

public class JsonReaderLoader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReaderLoader(String source) {
        this.source = source;
    }

    // EFFECTS: reads adventure game from file and returns it as a map;
    // throws IOException if an error occurs reading data from file
    public Gamestate read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseStory(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses game state from JSON object and returns it
    private Gamestate parseStory(JSONObject jsonObject) {
        int newBoardId = jsonObject.getInt("newBoardId");
        List<Choice> choiceHistory = new ArrayList<>();
        JSONArray jsonChoiceHistory = jsonObject.getJSONArray("choiceHistory");

        for (int i = 0; i < jsonChoiceHistory.length(); i++) {
            JSONObject jsonChoice = jsonChoiceHistory.getJSONObject(i);
            String description = jsonChoice.getString("description");
            int nextBoardId = jsonChoice.getInt("nextBoardId");
            choiceHistory.add(new Choice(description, nextBoardId));
        }
        return new Gamestate(newBoardId, choiceHistory);
    }
}