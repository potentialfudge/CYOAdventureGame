# My Personal Project

## A choose-your-own-adventure game with word puzzles along the way

My application will:
- Let users play an adventure game where they can choose how they want the story to go.
- Have users be presented with two options at each part of the storyline, where they will choose one of the two.
- Present users with a word-guessing puzzle (inspired by *Wordle*) at certain points in the story, where they can only proceed further upon successful completion of the puzzle. After 6 unsuccessful guesses, they lose all their progress and return to the first stage of the game.

Who can use it? \
Anyone who has an interest in adventure games and fun word puzzles!

Why is this project of interest to *me*? \
I personally am one of those people really interested in both choose-your-own-adventure (CYOA) games and word puzzles like *Wordle*, and I haven't seen a game that combines both of those. Because of that, I thought it would be an interesting and challenging task to take on for the term project.

## User Stories

As a user, I want to be able to:
- Choose one of the two options presented to me that determines what happens next, thus adding to the list of my choices made in the game.
- View the list of choices I've made at each stage so far.
- Play a word guessing puzzle at certain stages, adding variety to the game.
- Get feedback after each word puzzle guess, so I can understand which letters are correct and improve my guesses.
- Have different endings based on my choices and puzzle outcomes, so I can play the game multiple times to discover all possible endings.
- Be presented with an option to save the current state of the game, i.e. save the current storyboard that I am at to file.
- Be presented with an option to reload the last saved state or storyboard of the game when I open up the game again, and resume where I left off.

## Instructions for End User
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by selecting one of the choices presented to you at different stages in the story, which adds your selected choices to your choice history. The choice history so far is always displayed at the bottom of the window.
- You can generate the second required action related to the user story "adding multiple Xs to a Y", which is removing a choice from the choice history, by right clicking on the choice you want to remove and clicking "Remove Choice from History".
- You can locate my visual component when you first start up the application, as it is an image splash screen.
- You can save the state of my application by going to the top menu and clicking File > Save and Exit.
- You can reload the state of my application by going to the top menu and clicking Game Options > Restart.

### Phase 4: Task 2
Storyboards successfully loaded \
Selected choice: Explore the forest \
Selected choice: Try to open the door \
You guessed the code! \
Selected choice: Rizz her up \
Selected choice: Go down the trapdoor \
Choice removed from history: Try to open the door \
You guessed the code! \
Selected choice: Go left

### Phase 4: Task 3
One thing I would want to refactor is the relationship between GameLogic and StoryBoard. GameLogic has a map/graph of all the storyboards, and retrieves one whenever it is needed. I would add something like a storyboard manager class which handles only the logic of traversing through storyboards. This would also make it easier if storyboards need to be added or manipulated in the future.