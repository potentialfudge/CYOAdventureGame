package ui;

import model.Story;
import model.StoryBoard;

import java.util.Scanner;
import java.util.ArrayList;

// Represents the adventure game and maps out the overall story
public class AdventureGame {
    private static ArrayList<String> choiceHistory = new ArrayList<>();
    Story story = new Story();
    Scanner scanner = new Scanner(System.in);
    StoryBoard currentBoard = story.getBoardFromId(1);
    boolean enteredH;

    // EFFECTS: constructs an adventure game
    @SuppressWarnings("methodlength")
    public AdventureGame() {
        while (true) {
            System.out.println(currentBoard.getDescription());

            if (currentBoard.hasWordGuesser() && currentBoard.getId() == 2) {
                WordGuesserGame wordGuesserGame = new WordGuesserGame("SOUTH");
                boolean passed2 = wordGuesserGame.playGame();
                if (!passed2) {
                    System.out.println("You failed to guess the secret code. "
                            + "The door collapses on you and you die. The End");
                    printNonChoiceOptions();
                    historyHandler();
                    if (enteredH == true) {
                        break;
                    } else {
                        restart();
                    }
                }
            } else if (currentBoard.hasWordGuesser() && currentBoard.getId() == 3) {
                WordGuesserGame wordGuesserGame = new WordGuesserGame("HUMAN");
                boolean passed3 = wordGuesserGame.playGame();
                if (!passed3) {
                    System.out.println(
                            "You failed to guess the monster's favourite food. He eats you, and you die. The End");
                    printNonChoiceOptions();
                    historyHandler();
                    if (enteredH == true) {
                        break;
                    } else {
                        restart();
                    }
                }
            }

            if (currentBoard.getChoices().isEmpty()) {
                printNonChoiceOptions();
                historyHandler();
                if (enteredH == true) {
                    break;
                } else {
                    restart();
                }
            }

            for (int i = 0; i < currentBoard.getChoices().size(); i++) {
                System.out.println((i + 1) + " - " + currentBoard.getChoices().get(i).getDescription());
            }
            int playerChoice = scanner.nextInt();
            int nextBoardId = currentBoard.getChoices().get(playerChoice - 1).getNextBoardId();
            choiceHistory.add(currentBoard.getChoices().get(playerChoice - 1).getDescription());
            currentBoard = story.getBoardFromId(nextBoardId);
        }

        scanner.close();
    }

    // MODIFIES: this
    // EFFECTS: displays the list of choices the user has made so far
    private static void viewHistory() {
        if (choiceHistory.isEmpty()) {
            System.out.println("You haven't made any choices yet!");
        } else {
            System.out.println("Here are the choices you've made so far:");
        }
        for (int i = 0; i < choiceHistory.size(); i++) {
            System.out.println((i + 1) + ": " + choiceHistory.get(i));
        }
    }

    // MODIFIES: currentBoard, this
    // EFFECTS: restarts the game, changing the current board to the starting board
    private void restart() {
        String historyInput = scanner.next();
        if (historyInput.equals("r")) {
            currentBoard = story.getBoardFromId(1);
            System.out.println(currentBoard.getDescription());
        }
    }

    // MODIFIES: this
    // EFFECTS: displays the choices for the current board
    private void displayChoices() {
        for (int i = 0; i < currentBoard.getChoices().size(); i++) {
            System.out.println((i + 1) + " - " + currentBoard.getChoices().get(i).getDescription());
        }
    }

    // MODIFIES: this
    // EFFECTS: prints the messages with options to view choice history or restart the game
    private void printNonChoiceOptions() {
        System.out.println("Enter h to view the choices you've made so far!");
        System.out.println("Enter r to restart the game.");
    }

    // MODIFIES: enteredH, this
    // EFFECTS: checks whether the user has entered "h" to view choice history,
    // if yes then displays choice history and sets enteredH to true to help
    // break the while loop
    private void historyHandler() {
        String historyInput = scanner.next();
        if (historyInput.equals("h")) {
            viewHistory();
            enteredH = true;
        }
    }
}
