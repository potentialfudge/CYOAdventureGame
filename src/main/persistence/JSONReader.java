package persistence;

import model.Choice;
import model.ChoiceHistory;
import model.StoryBoard;
import ui.AdventureGame;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.json.*;

public class JSONReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JSONReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads adventure game from file and returns it as a map;
    // throws IOException if an error occurs reading data from file
    public Map<Integer, StoryBoard> read() throws IOException {
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

    // EFFECTS: parses story from JSON object and returns it
    private Map<Integer, StoryBoard> parseStory(JSONObject jsonObject) {
        Map<Integer, StoryBoard> story = new HashMap<>();

        JSONArray jsonArray = jsonObject.getJSONArray("storyBoards");
        for (Object storyBoardObj : jsonArray) {
            JSONObject nextBoard = (JSONObject) storyBoardObj;
            StoryBoard storyBoard = parseStoryBoard(nextBoard);
            story.put(storyBoard.getId(), storyBoard);
        }

        return story;
    }

    // EFFECTS: parses a StoryBoard from JSON object and returns it
    private StoryBoard parseStoryBoard(JSONObject jsonObject) {
        int id = jsonObject.getInt("id");
        String description = jsonObject.getString("description");
        JSONArray jsonChoices = jsonObject.getJSONArray("choices");

        List<Choice> choices = new ArrayList<>();
        for (int i = 0; i < jsonChoices.length(); i++) {
            JSONObject jsonChoice = jsonChoices.getJSONObject(i);
            String choiceDescription = jsonChoice.getString("description");
            int nextBoardId = jsonChoice.getInt("nextBoardId");
            choices.add(new Choice(choiceDescription, nextBoardId));
        }

        boolean hasWordGuesser = jsonObject.getBoolean("hasWordGuesser");
        return new StoryBoard(id, description, choices, hasWordGuesser);
    }
}
