# Tic-Tac-Toe
A repo for a tic-tac-toe game in Java created by David Brunner 

## Instructions

This is a JavaFX program; thus, the user is required to download and install the JavaFX SDK components needed for the program to work without any errors. The files can be compiled and run either from the computer command line or from an IDE like Eclipse or NetBeans. If Visual Studio Code is used, the necessary extensions and settings must be downloaded and adjusted. Also remember to add the javafx.controls, javafx.fxml, and javafx.media modules from the JavaFX SDK download. Documentation and tutorials can be referenced online if any complications occur, but from now on this README assumes the user has the necessary components. 

The first screen on the display will prompt the user to select the background of the Tic-Tac-Toe board as well as the number of human players. If zero is selected then there will be two AI players, if one, then there will be one human and one AI, and if two, then there will be two human players playing against each other. If an AI is present, then the option for selecting the avatar for the CPU will be shown. However, the user must be careful as each image corresponds with a skill level, starting from beginner, to intermediate, and ending with impossible. If there are two AI players, then only the first CPU avatar will determine the difficulty. 

Now the user is also able to select the avatar for himself or the two human players whenever possible. Just be certain to not choose the same image as then the game is unable to begin whenever the new game button is clicked. On the same start screen, the total number of wins for Player 1 and Player 2 are also shown. But whenever the number of players is changed for the next round, the totals will go back to the default of zero. 

If zero players are selected and the new game button is pressed, the display will move on to the next screen where the result is shown with the winner either congratulated or the unfortunate draw deplored with an animation. If one player is selected, then the number of total turns is displayed next to the user’s avatar. Whenever a square is clicked on the board game, it is filled with the avatar image and the program moves on to determine and display the AI’s choice. Just be warned, the CPU will trash talk the user during their turn. Whenever there is no move possible, the user lost, or the user won, the corresponding animation will automatically play. The final option of two human players will show a screen with the current player’s avatar and turn number, waiting until a square selection has been made. After the continue game button is clicked within the results screen, the game will go back to the first screen and redisplay the options for the user. 

For those desiring to know how the CPU determines its choices, the beginner will just select a random square that isn’t taken. The intermediate will play to block a two in a row but otherwise just selects a random square as well. The difficult skill level will calculate the best possible move within the board after every turn, thus it is impossible to beat. 

## Skills Learned
- Learning and adapting algorithm
- Code organization
- Object-oriented programming
- Animations in JavaFX
