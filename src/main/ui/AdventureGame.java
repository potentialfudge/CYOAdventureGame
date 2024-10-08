package ui;

import model.Story;
import model.StoryBoard;

import java.util.Scanner;
import java.util.ArrayList;

public class AdventureGame {
    private static ArrayList<String> choiceHistory = new ArrayList<>();

    @SuppressWarnings("methodlength")
    public static void main(String[] args) {
        Story story = new Story();
        Scanner scanner = new Scanner(System.in);
        StoryBoard currentBoard = story.getBoardFromId(1);

        while (true) {
            System.out.println(currentBoard.getDescription());

            if (currentBoard.hasWordGuesser() && currentBoard.getId() == 2) {
                WordGuesserGame wordGuesserGame = new WordGuesserGame("SOUTH");
                boolean passed2 = wordGuesserGame.playGame();

                if (!passed2) {
                    System.out.println("You failed to guess the secret code. "
                            +
                            "The door collapses on you and you die. The End");

                    System.out.println("Enter h to view the choices you've made so far.");
                    System.out.println("Enter r to restart the game.");

                    String historyInput = scanner.next();
                    if (historyInput.equals("h")) {
                        viewHistory();
                        break;
                    } else if (historyInput.equals("r")) {
                        currentBoard = story.getBoardFromId(1);
                        System.out.println(currentBoard.getDescription());
                }
                    }
            } else if (currentBoard.hasWordGuesser() && currentBoard.getId() == 3) {
                WordGuesserGame wordGuesserGame = new WordGuesserGame("HUMAN");
                boolean passed3 = wordGuesserGame.playGame();

                if (!passed3) {
                    System.out.println(
                            "You failed to guess the monster's favourite food. He eats you, and you die. The End");

                    System.out.println("Enter h to view the choices you've made so far!");
                    System.out.println("Enter r to restart the game.");

                    String historyInput = scanner.next();
                    if (historyInput.equals("h")) {
                        viewHistory();
                        break;
                    } else if (historyInput.equals("r")) {
                        System.out.println(currentBoard.getDescription());
                        currentBoard = story.getBoardFromId(1);
                    }
                }
            }

            if (currentBoard.getChoices().isEmpty()) {
                System.out.println("Enter h to view the choices you've made so far!");
                String historyInput = scanner.next();
                if (historyInput.equals("h")) {
                    viewHistory();
                    break;
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
}
