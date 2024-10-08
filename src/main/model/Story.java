package model;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

// Represents a story graph
public class Story {
    private Map<Integer, StoryBoard> boards;

    // EFFECTS: Initializes the boards HashMap with storyboards
    public Story() {
        boards = new HashMap<>();
        createStoryBoards();
    }

    // MODIFIES: this
    // EFFECTS: initializes and adds StoryBoards to the HashMap with their IDs
    private void createStoryBoards() {
        ArrayList<Choice> choices1 = new ArrayList<>();
        choices1.add(new Choice("Go into ruins", 2));
        choices1.add(new Choice("Explore the forest", 3));

        StoryBoard board1 = new StoryBoard(1,
        "You're hiking in the forest by some abandoned ruins when you see something in the bushes out of the corner of your eye. You look for it to see what it is, and it's a clay tablet that looks like it's from at least 2000 years ago. On it, there's something written in a strange language you don't understand, but a few drawings at the bottom resemble a treasure chest of gold, and some other creatures you've never heard of before. There might be a lost treasure, you think.",
        choices1, false);

        ArrayList<Choice> choices2 = new ArrayList<>();
        choices2.add(new Choice("Guess the secret code", 4));

        StoryBoard board2 = new StoryBoard(2, "When you further go into the obscured part of the ruins, you find an extremely dusty locked door, but the lock is nothing like you've ever seen before. It looks like you have to enter some sort of password or code to unlock it.",
        choices2, true);

        ArrayList<Choice> choices3 = new ArrayList<>();
        choices3.add(new Choice("Guess his favourite food", 6));

        StoryBoard board3 = new StoryBoard(3, "You hear something rustling in the distance. You walk a little further and suddenly, an enormous creature jumps in front of you. It's the monster from the tablet! He says that he would let you pass through on one condition: you guess his favourite food. Otherwise, he eats you and you die.",
        choices3, true);

        StoryBoard board4 = new StoryBoard(4, "Congratulations, you have guessed the secret code and unlocked the door! You open the door to find a room with multiple chests full of gold coins. The End",
        new ArrayList<>(), false);
        
        StoryBoard board6 = new StoryBoard(6, "That's right, it's human -- so he eats you anyway! The End",
        new ArrayList<>(), false);

        boards.put(board1.getId(), board1);
        boards.put(board2.getId(), board2);
        boards.put(board3.getId(), board3);
        boards.put(board4.getId(), board4);
        boards.put(board6.getId(), board6);
    }

    // REQUIRES: id >= 0, id must correspond to a valid storyboard in the map
    // EFFECTS: returns the StoryBoard associated with the following id.
    // if no StoryBoard is found, returns null
    public StoryBoard getBoardFromId(int id) {
        return boards.get(id);
    } 
}
