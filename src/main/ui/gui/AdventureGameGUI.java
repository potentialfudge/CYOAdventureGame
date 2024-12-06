package ui.gui;

import javax.swing.*;

import model.GameLogic;
import model.Choice;
import model.ChoiceHistory;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

// Represents the main game graphical user interface
public class AdventureGameGUI {
    private GameLogic gameLogic;
    private JFrame frame;
    private JTextArea gameDescriptionArea;
    private JButton choiceButton1;
    private JButton choiceButton2;
    private JTextField wordGuessInputField;
    private JButton submitWordGuessButton;
    private JPanel panel;
    private ChoiceHistory choiceHistory;
    private JTextArea choiceHistoryArea;
    private JLabel attemptsRemainingLabel;
    private int wordGuessAttempts = 0;
    private JButton quitButton;
    private JButton restartButton;
    private JPanel wordGuesserFeedbackPanel;

    public AdventureGameGUI() {
        gameLogic = new GameLogic();
        choiceHistory = gameLogic.getChoiceHistory();
        setupUI();
        askToLoadSavedGame();
    }

    // MODIFIES: this
    // EFFECTS: sets up game window with necessary panels, frames, buttons, etc.
    @SuppressWarnings("methodlength")
    public void setupUI() {
        frame = new JFrame("Adventure Game");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        gameDescriptionArea = new JTextArea();
        gameDescriptionArea.setEditable(false);
        gameDescriptionArea.setLineWrap(true);
        gameDescriptionArea.setWrapStyleWord(true);
        panel.add(gameDescriptionArea);

        choiceButton1 = new JButton();
        choiceButton2 = new JButton();
        panel.add(choiceButton1);
        panel.add(choiceButton2);

        wordGuessInputField = new JTextField();
        submitWordGuessButton = new JButton("Submit");
        panel.add(wordGuessInputField);
        panel.add(submitWordGuessButton);

        attemptsRemainingLabel = new JLabel("no. of attempts remaining: 6");
        panel.add(attemptsRemainingLabel);

        choiceHistoryArea = new JTextArea();
        choiceHistoryArea.setEditable(false);
        choiceHistoryArea.setPreferredSize(new Dimension(300, 100));
        panel.add(new JScrollPane(choiceHistoryArea));

        choiceHistoryArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    showContextMenu(e);
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    showContextMenu(e);
                }
            }});

        wordGuesserFeedbackPanel = new JPanel();
        wordGuesserFeedbackPanel.setLayout(new BoxLayout(wordGuesserFeedbackPanel, BoxLayout.Y_AXIS));
        panel.add(wordGuesserFeedbackPanel);

        quitButton = new JButton("Quit");
        restartButton = new JButton("Restart");
        panel.add(quitButton);
        panel.add(restartButton);

        frame.add(panel);
        addMenu();
        frame.setVisible(true);
        updateGameView();
        addButtonListener();
    }

    // MODIFIES: this
    // EFFECTS: shows menu option to remove selected choice from choice history
    private void showContextMenu(MouseEvent e) {
        try {
            int lineNum = getLineAtPoint(e.getPoint());    
            String clickedText = getTextAtLine(lineNum);
            
            JPopupMenu contextMenu = new JPopupMenu();
            JMenuItem removeMenuItem = new JMenuItem("Remove Choice from History");
            contextMenu.add(removeMenuItem);
            removeMenuItem.addActionListener(ae -> {
                gameLogic.removeChoiceByDescription(clickedText);
                updateChoiceHistoryDisplay();
            });
            contextMenu.show(choiceHistoryArea, e.getX(), e.getY());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // EFFECTS: gets the line number at a specific point position
    private int getLineAtPoint(Point point) {
        FontMetrics fontMetrics = choiceHistoryArea.getFontMetrics(choiceHistoryArea.getFont());
        int lineHeight = fontMetrics.getHeight();
        return point.y / lineHeight;
    }

    // EFFECTS: gets the text at a specific line number
    private String getTextAtLine(int lineNum) {
        try {
            int startOffset = choiceHistoryArea.getLineStartOffset(lineNum);
            int endOffset = choiceHistoryArea.getLineEndOffset(lineNum);
            return choiceHistoryArea.getText(startOffset, endOffset - startOffset).trim();
        } catch (Exception e) {
            return "";
        }
    }

    // MODIFIES: this
    // EFFECTS: adds options like save & exit, quit and restart as menu options and
    // adds the menu to the UI
    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveExitMenuItem = new JMenuItem("Save & Exit");
        saveExitMenuItem.addActionListener(e -> askToSaveAndExit());
        fileMenu.add(saveExitMenuItem);
        JMenuItem deleteSavedGameMenuItem = new JMenuItem("Delete Saved Game");
        deleteSavedGameMenuItem.addActionListener(e -> deleteSavedGame());
        fileMenu.add(deleteSavedGameMenuItem);
        menuBar.add(fileMenu);

        JMenu optionsMenu = new JMenu("Game Options");
        JMenuItem quitMenuItem = new JMenuItem("Quit");
        JMenuItem restartMenuItem = new JMenuItem("Restart");
        quitMenuItem.addActionListener(e -> quitGame());
        restartMenuItem.addActionListener(e -> restartGame());
        optionsMenu.add(quitMenuItem);
        optionsMenu.add(restartMenuItem);
        menuBar.add(optionsMenu);

        frame.setJMenuBar(menuBar);
    }

    // MODIFIES: this
    // EFFECTS: adds action listeners to buttons
    private void addButtonListener() {
        choiceButton1.addActionListener(e -> processChoice(0));
        choiceButton2.addActionListener(e -> processChoice(1));

        submitWordGuessButton.addActionListener(e -> processWordGameGuess());

        quitButton.addActionListener(e -> quitGame());
        restartButton.addActionListener(e -> restartGame());
    }

    // MODIFIES: this
    // EFFECTS: updates game view according to visibility settings at every stage of
    // the game
    private void updateGameView() {
        if (!gameLogic.hasWordGuesser()) {
            wordGuesserFeedbackPanel.removeAll();
            wordGuesserFeedbackPanel.revalidate();
            wordGuesserFeedbackPanel.repaint();
        }

        gameDescriptionArea.setText(gameLogic.getCurrentBoardDescription());

        List<Choice> choices = gameLogic.getCurrentBoardChoices();

        boolean isWordGuesser = gameLogic.hasWordGuesser();
        choiceButton1.setVisible(!isWordGuesser && choices.size() > 0);
        choiceButton2.setVisible(!isWordGuesser && choices.size() > 1);

        if (choices.size() > 0) {
            choiceButton1.setText(choices.get(0).getDescription());
        }
        if (choices.size() > 1) {
            choiceButton2.setText(choices.get(1).getDescription());
        }

        wordGuessInputField.setVisible(isWordGuesser);
        submitWordGuessButton.setVisible(isWordGuesser);
        attemptsRemainingLabel.setVisible(isWordGuesser);

        boolean isEnding = gameLogic.isGameOver();
        quitButton.setVisible(isEnding);
        restartButton.setVisible(isEnding);

        updateChoiceHistoryDisplay();
    }

    // MODIFIES: this
    // EFFECTS: progresses the game once a choice is made by user and updates UI
    private void processChoice(int choiceIndex) {
        gameLogic.makeChoice(choiceIndex);
        updateGameView();
    }

    // MODIFIES: this
    // EFFECTS: handles word game guesses and gives feedback, moves onto next board
    // depending on
    // whether it was solved in 6 tries or not
    private void processWordGameGuess() {
        String guess = wordGuessInputField.getText().toUpperCase();
        String target = getWordGuessTarget();
        if (guess.length() == target.length()) {
            boolean correct = evaluateGuess(guess, target);
            wordGuesserFeedbackPanel.revalidate();
            wordGuesserFeedbackPanel.repaint();
            handleWordGuessAttempt(correct);
        } else {
            JOptionPane.showMessageDialog(frame, "Your guess must be " + target.length() + " letters long.");
        }
    }

    // MODIFIES: this
    // EFFECTS: helper for processWordGameGuess(), provides feedback on each letter
    // in the word by showing
    // a color background for each word (like Wordle)
    private Boolean evaluateGuess(String guess, String target) {
        boolean correctLetter = true;
        JPanel guessRowPanel = new JPanel();
        guessRowPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        for (int i = 0; i < guess.length(); i++) {
            JLabel label = new JLabel(String.valueOf(guess.charAt(i)));
            if (guess.charAt(i) == target.charAt(i)) {
                label.setBackground(Color.GREEN);
            } else if (target.contains(String.valueOf(guess.charAt(i)))) {
                label.setBackground(Color.YELLOW);
                correctLetter = false;
            } else {
                label.setBackground(Color.GRAY);
                correctLetter = false;
            }
            label.setOpaque(true);
            guessRowPanel.add(label);
            wordGuesserFeedbackPanel.add(guessRowPanel);
        }
        return correctLetter;
    }

    // MODIFIES: this
    // EFFECTS: sets the word to be guessed depending on which storyboard the game
    // is at
    private String getWordGuessTarget() {
        String tgt;
        if (gameLogic.getCurrentBoardId() == 2) {
            tgt = "SOUTH";
        } else if (gameLogic.getCurrentBoardId() == 9) {
            tgt = "SIGMA";
        } else if (gameLogic.getCurrentBoardId() == 12) {
            tgt = "FREAK";
        } else {
            tgt = "GRONK";
        }
        return tgt;
    }

    // MODIFIES: this
    // EFFECTS: displays messages according to user's word guess
    private void handleWordGuessAttempt(Boolean status) {
        if (status) {
            JOptionPane.showMessageDialog(frame, "Correct! Moving to the next stage.");
            gameLogic.proceedSuccessfulWordGuesser();
            wordGuessAttempts = 6;
            clearInputField();
            updateGameView();
        } else {
            JOptionPane.showMessageDialog(frame, "Incorrect guess. Try again.");
            wordGuessAttempts++;
            int attemptsRemaining = 6 - wordGuessAttempts;
            attemptsRemainingLabel.setText("no. of attempts remaining: " + attemptsRemaining);
            updateGameView();
            if (wordGuessAttempts >= 6) {
                JOptionPane.showMessageDialog(frame, "Sorry, you did not guess the code in 6 tries.");
                gameLogic.proceedFailedWordGuesser();
                wordGuessAttempts = 6;
                clearInputField();
                updateGameView();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: handles updating the choice history at the bottom of the screen
    private void updateChoiceHistoryDisplay() {
        StringBuilder historyText = new StringBuilder();
        for (Choice choice : choiceHistory.getChoices()) {
            historyText.append(choice.getDescription()).append("\n");
        }
        choiceHistoryArea.setText(historyText.toString());
    }

    // MODIFIES: this
    // EFFECTS: asks user if they want to load a saved game when application starts
    // up and handles choice accordingly
    private void askToLoadSavedGame() {
        int option = JOptionPane.showConfirmDialog(frame, "Do you want to load a saved game?",
                "Load Saved Game", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            loadSavedGame();
        } else {
            startNewGame();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads saved game
    private void loadSavedGame() {
        if (gameLogic.loadGame()) {
            JOptionPane.showMessageDialog(frame, "Game loaded successfully!");
            updateGameView();
        } else {
            JOptionPane.showMessageDialog(frame, "No saved game found, starting a new game.");
            startNewGame();
            updateGameView();
        }
    }

    // MODIFIES: this
    // EFFECTS: starts new game
    private void startNewGame() {
        gameLogic.startNewGame();
        updateGameView();
    }

    // MODIFIES: this
    // EFFECTS: asks user if they want to save and exit when selected from menu and
    // handles user choice
    public void askToSaveAndExit() {
        int option = JOptionPane.showConfirmDialog(
                frame,
                "Would you like to save your game and exit?",
                "Save and Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            gameLogic.saveGame();

            JOptionPane.showMessageDialog(frame, "Game saved. Exiting...");
            SwingUtilities.invokeLater(() -> frame.dispose());
        } else if (option == JOptionPane.NO_OPTION) {
            // continue playing
        }
    }

    // MODIFIES: this
    // EFFECTS: shows confirmation dialog, quits game if user selects yes
    public void quitGame() {
        int option = JOptionPane.showConfirmDialog(
                frame,
                "Are you sure you want to quit the game? This will not save any new progress.",
                "Quit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            SwingUtilities.invokeLater(() -> frame.dispose());
        } else if (option == JOptionPane.NO_OPTION) {
            // continue playing
        }
    }

    // MODIFIES: this
    // EFFECTS: shows confirmation dialog, restarts game if user selects yes
    public void restartGame() {
        int option = JOptionPane.showConfirmDialog(
                frame,
                "Are you sure you want to restart? Your progress will be lost.",
                "Restart",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            gameLogic.startNewGame();
            clearInputField();
            updateGameView();
            askToLoadSavedGame();
        } else if (option == JOptionPane.NO_OPTION) {
            // continue playing
        }
    }

    // MODIFIES: this
    // EFFECTS: clears the word guess text input field
    private void clearInputField() {
        wordGuessInputField.setText("");
    }

    // MODIFIES: this
    // EFFECTS: clears the information in the save file
    private void deleteSavedGame() {
        int option = JOptionPane.showConfirmDialog(
                frame,
                "Are you sure you want to delete your saved game?",
                "Delete Saved Game",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            File saveFile = new File("./data/saveState.json");
            saveFile.delete();
            JOptionPane.showMessageDialog(frame, "Saved game deleted successfully.");
            updateGameView();
        } else if (option == JOptionPane.NO_OPTION) {
            // continue playing
        }
    }
}