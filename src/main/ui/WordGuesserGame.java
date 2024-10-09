package ui;

import java.util.Scanner;

// Represents a word guesser game which has a correct word to be guessed in 6 tries
// and provides feedback on each guess
public class WordGuesserGame {
    final String green = "\u001B[32m";
    final String yellow = "\u001B[33m";
    final String reset = "\u001B[0m";
    private String correctWord;
    private String guessedWord;
    private Scanner scanner = new Scanner(System.in);
    private boolean isCorrect;

    // EFFECTS: constructs a word guesser game with a correct word to be guessed
    public WordGuesserGame(String correctWord) {
        this.correctWord = correctWord;
    }

    // MODIFIES: isCorrect, this
    // EFFECTS: starts the guesser game, providing feedback on each guess and gives up to 6 tries.
    // green if the letter is in the word in the same spot, yellow if it's in the word but in
    // a different spot, and white/grey if it's not in the word (inspired by Wordle)
    // returns whether the word has been guessed correctly or not
    public boolean playGame() {
        for (int round = 0; round < 6; round++) {
            System.out.println("Please guess your word!");
            guessedWord = scanner.nextLine().toUpperCase();

            for (int i = 0; i < 5; i++) {
                if (guessedWord.substring(i, i + 1).equals(correctWord.substring(i, i + 1))) {
                    System.out.print(green + guessedWord.substring(i, i + 1) + reset);
                } else if (correctWord.indexOf(guessedWord.substring(i, i + 1)) > -1) {
                    System.out.print(yellow + guessedWord.substring(i, i + 1) + reset);
                } else {
                    System.out.print(guessedWord.substring(i, i + 1));
                }
            }

            System.out.println("");

            if (guessedWord.equals(correctWord)) {
                isCorrect = true;
                break;
            } else {
                isCorrect = false;
            }
        }

        return isCorrect;
    }

    public String getCorrectWord() {
        return correctWord;
    }
}
