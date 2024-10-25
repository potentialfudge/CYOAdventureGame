package ui;

import model.ChoiceHistory;
import model.Story;
import model.StoryBoard;

import java.util.Scanner;
import java.util.ArrayList;

// Represents the adventure game and maps out the overall story
public class AdventureGame {
    // private static ArrayList<String> choiceHistory = new ArrayList<>();
    Story story = new Story();
    Scanner scanner = new Scanner(System.in);
    StoryBoard currentBoard = story.getBoardFromId(1);
    boolean enteredH;
    ChoiceHistory choiceHistory = new ChoiceHistory();

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
                    String historyInput = scanner.next();
                    if (historyInput.equals("h")) {
                        viewHistory();
                        break;
                    } else {
                        currentBoard = story.getBoardFromId(1);
                        continue;
                    }
                }
            } else if (currentBoard.hasWordGuesser() && currentBoard.getId() == 3) {
                WordGuesserGame wordGuesserGame = new WordGuesserGame("HUMAN");
                boolean passed3 = wordGuesserGame.playGame();
                if (!passed3) {
                    System.out.println(
                            "You failed to guess the monster's favourite food. He eats you, and you die. The End");
                    printNonChoiceOptions();
                    String historyInput = scanner.next();
                    if (historyInput.equals("h")) {
                        viewHistory();
                        break;
                    } else {
                        currentBoard = story.getBoardFromId(1);
                        continue;
                    }
                }
            }

            if (currentBoard.getChoices().isEmpty()) {
                printNonChoiceOptions();
                String historyInput = scanner.next();
                if (historyInput.equals("h")) {
                    viewHistory();
                    break;
                } else {
                    currentBoard = story.getBoardFromId(1);
                    continue;
                }
            }

            for (int i = 0; i < currentBoard.getChoices().size(); i++) {
                System.out.println((i + 1) + " - " + currentBoard.getChoices().get(i).getDescription());
            }
            int playerChoice = scanner.nextInt();
            int nextBoardId = currentBoard.getChoices().get(playerChoice - 1).getNextBoardId();
            choiceHistory.addHistory(currentBoard.getChoices().get(playerChoice - 1));
            currentBoard = story.getBoardFromId(nextBoardId);
        }
        scanner.close();
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
    }
}
