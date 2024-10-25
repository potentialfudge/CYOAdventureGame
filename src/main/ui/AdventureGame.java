package ui;

import model.ChoiceHistory;
import model.StoryBoard;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import persistence.JSONReader;
import persistence.JSONWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

// Represents the adventure game and maps out the overall story
public class AdventureGame {
    // private static ArrayList<String> choiceHistory = new ArrayList<>();
    private Map<Integer, StoryBoard> story = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);
    private StoryBoard currentBoard;
    private ChoiceHistory choiceHistory = new ChoiceHistory();
    private JSONWriter jsonWriter;
    private JSONReader jsonReader;
    private static final String JSON_STORE = "./data/storyState.json";

    // EFFECTS: constructs an adventure game
    @SuppressWarnings("methodlength")
    public AdventureGame() {
        // jsonWriter = new JSONWriter(JSON_STORE);
        jsonReader = new JSONReader(JSON_STORE);
        loadStoryBoards();
        currentBoard = story.get(1);
        boolean keepGoing = true;

        while (keepGoing == true) {
            System.out.println(currentBoard.getDescription());

            if (currentBoard.hasWordGuesser() && currentBoard.getId() == 2) {
                WordGuesserGame wordGuesserGame = new WordGuesserGame("SOUTH");
                boolean passed2 = wordGuesserGame.playGame();
                if (!passed2) {
                    System.out.println("You failed to guess the secret code. "
                            + "The door collapses on you and you die. The End");
                    printNonChoiceOptions();
                    String optionInput = scanner.next();
                    if (optionInput.equals("h")) {
                        viewHistory();
                        keepGoing = false;
                    } else if (optionInput.equals("r")) {
                        currentBoard = getBoardFromId(1);
                        continue;
                    } else if (optionInput.equals("l")) {
                        loadGame();
                    } else if (optionInput.equals("q")) {
                        keepGoing = false;
                    } else {
                        System.out.println("Selection not valid.");
                    }
                }
            } else if (currentBoard.hasWordGuesser() && currentBoard.getId() == 3) {
                WordGuesserGame wordGuesserGame = new WordGuesserGame("HUMAN");
                boolean passed3 = wordGuesserGame.playGame();
                if (!passed3) {
                    System.out.println(
                            "You failed to guess the monster's favourite food. He eats you, and you die. The End");
                    printNonChoiceOptions();
                    String optionInput = scanner.next();
                    if (optionInput.equals("h")) {
                        viewHistory();
                        keepGoing = false;
                    } else if (optionInput.equals("r")) {
                        currentBoard = getBoardFromId(1);
                        continue;
                    } else if (optionInput.equals("l")) {
                        loadGame();
                    } else if (optionInput.equals("q")) {
                        keepGoing = false;
                    } else {
                        System.out.println("Selection not valid.");
                    }
                }
            }

            if (currentBoard.getChoices().isEmpty()) {
                printNonChoiceOptions();
                String optionInput = scanner.next();
                if (optionInput.equals("h")) {
                    viewHistory();
                    keepGoing = false;
                } else if (optionInput.equals("r")) {
                    currentBoard = getBoardFromId(1);
                    continue;
                } else if (optionInput.equals("l")) {
                    loadGame();
                } else if (optionInput.equals("q")) {
                    keepGoing = false;
                } else {
                    System.out.println("Selection not valid.");
                }
            }

            for (int i = 0; i < currentBoard.getChoices().size(); i++) {
                System.out.println((i + 1) + " - " + currentBoard.getChoices().get(i).getDescription());
            }
            int playerChoice = scanner.nextInt();
            int nextBoardId = currentBoard.getChoices().get(playerChoice - 1).getNextBoardId();
            choiceHistory.addHistory(currentBoard.getChoices().get(playerChoice - 1));

            printNonChoiceOptions();
            String input = scanner.next();
            if (input.equals("h")) {
                viewHistory();
                keepGoing = false;
            } else if (input.equals("r")) {
                currentBoard = getBoardFromId(1);
                continue;
            } else if (input.equals("l")) {
                loadGame();
            } else if (input.equals("q")) {
                keepGoing = false;
            } else {
                System.out.println("Selection not valid.");
            }

            currentBoard = getBoardFromId(nextBoardId);
        }
        scanner.close();
    }

    private void loadStoryBoards() {
        JSONReader jsonReader = new JSONReader("data/storyGraph.json");

        try {
            story = jsonReader.read();
        } catch (IOException e) {
            System.err.println("Failed to load storyboards: " + e.getMessage());
            System.exit(1);
        }
    }

    // MODIFIES: this
    // EFFECTS: displays the list of choices the user has made so far
    private void viewHistory() {
        if (choiceHistory.getChoices().isEmpty()) {
            System.out.println("You haven't made any choices yet!");
        } else {
            System.out.println("Here are the choices you've made so far:");
        }
        for (int i = 0; i < choiceHistory.getChoices().size(); i++) {
            System.out.println((i + 1) + ": " + choiceHistory.getChoices().get(i).getDescription());
        }
    }

    // MODIFIES: this
    // EFFECTS: prints the messages with options to view choice history or restart
    // the game
    private void printNonChoiceOptions() {
        System.out.println("Enter h to view the choices you've made so far!");
        System.out.println("Enter r to restart the game.");
        System.out.println("Enter l to load the game from file.");
        System.out.println("Enter q to quit.");
    }

    // REQUIRES: id >= 0, id must correspond to a valid storyboard in the map
    // EFFECTS: returns the StoryBoard associated with the following id.
    // if no StoryBoard is found, returns null
    private StoryBoard getBoardFromId(int id) {
        return story.get(id);
    }

    // MODIFIES: this
    // EFFECTS: loads game from file
    private void loadGame() {
        try {
            story = jsonReader.read();
            System.out.println("Loaded game from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
