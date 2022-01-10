/**
 * Developer: David Brunner
 * Description: Creates a Main class that extends Application and implements JavaFX features to create a representation of a Tic-Tac-Toe game 
 */

import java.io.File;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/** The public class Main contains several methods and data fields for designing and creating the JavaFX application by extending the Application class and overriding its start method */
public class Main extends Application {

    //Creates the Rectangle Array grid to represent the squares of the tic-tac-toe board
    private Rectangle[][] grid = {{new Rectangle(60, 60), new Rectangle(60, 60), new Rectangle(60, 60)}, 
                {new Rectangle(60, 60), new Rectangle(60, 60), new Rectangle(60, 60)}, 
                {new Rectangle(60, 60), new Rectangle(60, 60), new Rectangle(60, 60)}}; 

    private GridPane gridContainer = new GridPane(); //Creates the GridPane gridContainer to represent the tic-tac-toe board

    private VBox mainContainer = new VBox(10); //Creates the VBox mainContainter as the main container for all of the elements in the tic-tac-toe game
    private Scene scene = new Scene(mainContainer, 800, 500); //Creates the Scene scene as the main scene for the javafx project and sets its content to mainContainer

    private int[][] gridInput = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}; //Creates the Integer Array gridInput to represent the cpu and user input 

    private int turn = 0; //Creates the Integer turn to represent the number of turns
    private boolean isFirst = true; //Creates the boolean isFirst to represent whether the first screen should be shown

    private int numberOfPlayers = 0; //Creates the Integer numberOfPlayers to represent the number of human players 
    private int previousNumOfPlayers = 0; //Creates the Integer previoiusNumOfPlayers to represent the previous number of human players

    private int difficulty = 0; //Creates the Integer difficulty to represent the skill level of the cpu
    private AI computer1 = new AI(gridInput, difficulty, 1); //Creates the AI computer1 to represent AI 1
    private AI computer2 = new AI(gridInput, difficulty, 2); //Creates the AI computer2 to represent AI 2

    private int totalWins1 = 0; //Creates the Integer totalWins1 to represent the total wins of player 1
    private int totalPrevWins1 = 0; //Creates the Integer totalPrevWins1 to represent the total previous wins of player 1
    private int totalWins2 = 0; //Creates the Integer totalWins2 to represent the total wins of player 2
    private int totalPrevWins2 = 0;  //Creates the Integer totalPrevWins1 to represent the total previous wins of player 1

    private int currentScore1 = 0; //Creates the Integer currentScore1 to represent the current score within the game for player 1
    private int currentScore2 = 0; //Creates the Integer currentScore2 to represent the current score within the game for player 2

    private Image[] AIAvatarImage = {new Image("ai_avatar_image/rabbit.jpg"), new Image("ai_avatar_image/lion.jpg"), new Image("ai_avatar_image/dragon.jpg")}; //Creates the Image Array AIAvatarImage to represent the avatar images for the AI
    private int currentAI1AvatarIndex = 0; //Creates the Integer currentAI1AvatarIndex to represent the current avatar image of AI 1
    private int currentAI2AvatarIndex = 0; //Creates the Integer currentAI2AvatarIndex to represent the current avatar image of AI 2

    //Creates the Image Array userAvatarImage to represent the avatar images for the user
    private Image[] userAvatarImage = {new Image("user_avatar_image/bear.jpg"), new Image("user_avatar_image/cat.png"), new Image("user_avatar_image/dog.png"), 
            new Image("user_avatar_image/eagle.png"), new Image("user_avatar_image/elephant.png"), new Image("user_avatar_image/tiger.jpg")
    }; 
    private int currentUser1AvatarIndex = 0; //Creates the Integer currentUser1AvatarIndex to represent the current avatar image of user 1
    private int currentUser2AvatarIndex = 0;  //Creates the Integer currentUser2AvatarIndex to represent the current avatar image of user 2

    Color[] backgroundColors = {Color.WHITE, Color.RED, Color.TURQUOISE, Color.DARKGREEN, Color.PINK, Color.LAVENDER}; //Creates the Colors Array backgroundColors to represent the background colors of the tic-tac-toe board 
    private int currentBackgroundIndex = 0; //Creates the Integer currentBackgroundIndex to represent the current background color of the tic-tac-toe board 

    HBox animationHBox = new HBox(0); //Creates the HBox animationHBox to represent the container for the win, lose, and draw animations

    //Creates the Media Array trashTalkMedia to represent the possible trash talk audio from the AI
    Media[] trashTalkMedia = {
            new Media(new File("ai_audio/audio1.mp3").toURI().toString()), new Media(new File("ai_audio/audio2.mp3").toURI().toString()), 
            new Media(new File("ai_audio/audio3.mp3").toURI().toString()), new Media(new File("ai_audio/audio4.mp3").toURI().toString()), 
            new Media(new File("ai_audio/audio5.mp3").toURI().toString()), new Media(new File("ai_audio/audio6.mp3").toURI().toString())
    }; 

    //Creates the MediaPlayer aiTrashTalk to represent the ability to play the possible trash talk audio from the AI 
    MediaPlayer[] aiTrashTalk = {new MediaPlayer(trashTalkMedia[0]), new MediaPlayer(trashTalkMedia[1]), 
            new MediaPlayer(trashTalkMedia[2]), new MediaPlayer(trashTalkMedia[3]), 
            new MediaPlayer(trashTalkMedia[4]), new MediaPlayer(trashTalkMedia[5])
    }; 
    int currentTrashTalk = 0; //Creates the Integer currentTrashTalk to represent the current trash talk phrase for the AI 

    /** The start method is overridden to create a stage containing a Scene with the Tic-Tac-Toe board, the beginning screen, and the results */
    @Override
    public void start(Stage primaryStage) {

        //Checks if the current screen represents the first screen
        if (isFirst) {

            mainContainer.getChildren().clear();
            mainContainer.setAlignment(Pos.CENTER);

            previousNumOfPlayers = numberOfPlayers;
            totalPrevWins1 = totalWins1;
            totalPrevWins2 = totalWins2;

            //Creates the HBox avatarContainer to represent the container for the avatar images
            HBox avatarContainer = new HBox(20); 
            avatarContainer.setAlignment(Pos.CENTER);
            mainContainer.getChildren().add(avatarContainer);
            setAvatarImage(avatarContainer);

            //Creates the HBox totalWinsContainer to represent the container for the total results of the games
            HBox totalWinsContainer = new HBox(10); 
            totalWinsContainer.setAlignment(Pos.CENTER);
            mainContainer.getChildren().add(totalWinsContainer);

            //Creates the Text labels for the total number of both player wins
            Text totalWins1Label = new Text("Player1 Wins: " + totalWins1); 
            totalWinsContainer.getChildren().add(totalWins1Label);
            Text totalWins2Label = new Text("Player2 Wins: " + totalWins2);
            totalWinsContainer.getChildren().add(totalWins2Label);

            //Creates the Text selectNumOfPlayersLabel to instruct the user to choose the total number of human players
            Text selectNumOfPlayersLabel = new Text("Select Number of Players:"); 
            mainContainer.getChildren().add(selectNumOfPlayersLabel);

            //Creates the selectNumOfPlayersContainer HBox as the container for the number of human players choices
            HBox selectNumOfPlayersContainer = new HBox(10); 
            selectNumOfPlayersContainer.setAlignment(Pos.CENTER);
            mainContainer.getChildren().add(selectNumOfPlayersContainer);

            ToggleGroup selectNumOfPlayers = new ToggleGroup(); //Creates the ToggleGroup selectNumOfPlayers for the number of human players choices

            //Creates the RadioButton selectNumOfPlayers0 that represents only AI players
            RadioButton selectNumOfPlayers0 = new RadioButton("0"); 
            selectNumOfPlayers0.setToggleGroup(selectNumOfPlayers);
            selectNumOfPlayersContainer.getChildren().add(selectNumOfPlayers0);

            //Creates the RadioButton selectNumOfPlayers1 that represents only 1 human and 1 AI player
            RadioButton selectNumOfPlayers1 = new RadioButton("1");
            selectNumOfPlayers1.setToggleGroup(selectNumOfPlayers);
            selectNumOfPlayersContainer.getChildren().add(selectNumOfPlayers1);

            //Creates the RadioButton selectNumOfPlayers2 that represents only human players 
            RadioButton selectNumOfPlayers2 = new RadioButton("2");
            selectNumOfPlayers2.setToggleGroup(selectNumOfPlayers);
            selectNumOfPlayersContainer.getChildren().add(selectNumOfPlayers2);

            selectNumOfPlayers0.setOnAction(e -> {
                selectNumOfPlayers1.setSelected(false);
                selectNumOfPlayers2.setSelected(false);

                numberOfPlayers = 0;
                setAvatarImage(avatarContainer); //Calls the setAvatarImage with the avatarContainer HBox to update the avatar choices

                //Resets win count if the previous number of human players was not zero 
                if (previousNumOfPlayers != 0) {
                    totalWins1 = 0;
                    totalWins2 = 0;
                } else {
                    totalWins1 = totalPrevWins1;
                    totalWins2 = totalPrevWins2;
                }

                totalWins1Label.setText("Player1 Wins: " + totalWins1);
                totalWins2Label.setText("Player2 Wins: " + totalWins2);
            });

            selectNumOfPlayers1.setOnAction(e -> {
                selectNumOfPlayers0.setSelected(false);
                selectNumOfPlayers2.setSelected(false);

                numberOfPlayers = 1;
                setAvatarImage(avatarContainer); //Calls the setAvatarImage with the avatarContainer HBox to update the avatar choices

                //Resets win count if the previous number of human players was not one
                if (previousNumOfPlayers != 1) {
                    totalWins1 = 0;
                    totalWins2 = 0; 
                } else {
                    totalWins1 = totalPrevWins1;
                    totalWins2 = totalPrevWins2;
                }; 

                totalWins1Label.setText("Player1 Wins: " + totalWins1);
                totalWins2Label.setText("Player2 Wins: " + totalWins2);
            });

            selectNumOfPlayers2.setOnAction(e -> {
                selectNumOfPlayers0.setSelected(false);
                selectNumOfPlayers1.setSelected(false);

                numberOfPlayers = 2;
                setAvatarImage(avatarContainer); //Calls the setAvatarImage with the avatarContainer HBox to update the avatar choices

                //Resets win count if the previous number of human players was not two
                if (previousNumOfPlayers != 2) {
                    totalWins1 = 0;
                    totalWins2 = 0;
                } else {
                    totalWins1 = totalPrevWins1;
                    totalWins2 = totalPrevWins2;
                }

                totalWins1Label.setText("Player1 Wins: " + totalWins1); 
                totalWins2Label.setText("Player2 Wins: " + totalWins2);
            });

            Button newGame = new Button("New Game"); //Creates the newGame Button for choosing to begin a new game

            newGame.setOnMouseClicked(e -> {
                //Won't start new game unless one option was selected within the selectNumOfPlayers ToggleGroup
                if (selectNumOfPlayers.getSelectedToggle() != null) {

                    //Won't start new game unless the two avatar images for the two players are different
                    if ((numberOfPlayers == 0 && currentAI1AvatarIndex != currentAI2AvatarIndex) || (numberOfPlayers == 2 && currentUser1AvatarIndex != currentUser2AvatarIndex) || numberOfPlayers == 1) {
                        this.setUpGrid();
                        currentScore1 = 0;
                        currentScore2 = 0;
                        isFirst = false;
                        turn++; //Increases turn by one to continue to the next screen
                        computer1 = new AI(gridInput, difficulty, 1);
                        computer2 = new AI(gridInput, difficulty, 2);
                        this.start(primaryStage); //Restarts the JavaFX application with the new data 
                    }; 
                };
            });

            mainContainer.getChildren().add(newGame);

        } else if (turn == 0) { //Checks if turn is equal to zero, meaning a player has won 

            mainContainer.getChildren().clear();
            mainContainer.setAlignment(Pos.CENTER);
            mainContainer.getChildren().add(animationHBox);
            mainContainer.getChildren().add(gridContainer);

            //Creates the resultDisplay Text for displaying the results of the game
            Text resultDisplay = new Text(); 
            resultDisplay.setFont(new Font(20));

            if (currentScore1 > currentScore2) {

                if (numberOfPlayers == 0) {
                    mainContainer.getChildren().add(new ImageView(AIAvatarImage[currentAI1AvatarIndex])); //If true, adds current avatar of the first AI player to mainContainer
                } else {
                    mainContainer.getChildren().add(new ImageView(userAvatarImage[currentUser1AvatarIndex])); //Else, adds the current avatar of the first user to mainContainer
                }; 

                resultDisplay.setText(numberOfPlayers == 1 ? "CONGRATS! You Won!" : "Player 1 Won!"); //Updates resultDisplay according to whether there was only one human player 
                winAnimation(resultDisplay); //Calls the winAnimation method since the user or a player has won
                totalWins1++;
            } else if (currentScore1 < currentScore2) {

                if (numberOfPlayers == 0) {
                    mainContainer.getChildren().add(new ImageView(AIAvatarImage[currentAI2AvatarIndex])); //If true, adds current avatar of the second AI player to mainContainer
                } else if (numberOfPlayers == 1) {
                    mainContainer.getChildren().add(new ImageView(AIAvatarImage[currentAI1AvatarIndex])); //If true, adds current avatar of the first AI player to mainContainer
                } else {
                    mainContainer.getChildren().add(new ImageView(userAvatarImage[currentUser2AvatarIndex])); //Else, adds current avatar of the second user to mainContainer
                }; 

                resultDisplay.setText(numberOfPlayers == 1 ? "SORRY! You Lost!" : "Player 2 Won!"); //Updates resultDisplay according to whether there was only one human player 

                if (numberOfPlayers == 1) {
                    loseAnimation(resultDisplay); //If true, calls the loseAnimation method since the user has lost
                } else {
                    winAnimation(resultDisplay); //Else, calls the winAnimation method since a player has won
                }

                totalWins2++;
            } else {
                resultDisplay.setText("OOPS! Looks like a tie!"); //Updates resultDisplay to the draw result 
                drawAnimation(resultDisplay); //Calls the drawAnimation method 
            }; 

            mainContainer.getChildren().add(resultDisplay);

            Button continueGame = new Button("Continue Game"); //Creates the continueGame button to continue to the first screen 

            continueGame.setOnMouseClicked(e -> {
                isFirst = true; //Sets isFirst to true to restart the game screen
                this.start(primaryStage); //Restarts the JavaFX application with the new data 
            });

            mainContainer.getChildren().add(continueGame);

        } else {

            mainContainer.getChildren().clear();
            mainContainer.setAlignment(Pos.CENTER);

            //Creates the userContainer to display the information of the current user in the current turn 
            HBox userContainer = new HBox(5); 
            userContainer.setAlignment(Pos.TOP_CENTER);
            Text turnText = new Text("Turn: " + turn); //Creates the turnText Text to display the total number of turns in the game
            userContainer.getChildren().add(turnText);

            mainContainer.getChildren().add(userContainer);
            mainContainer.getChildren().add(gridContainer);

            if (turn % 2 == 1) {

                if (numberOfPlayers == 0) {
                    handleAI(computer1); //If true, calls the handleAI method with the computer1 AI since there is no human player

                    //Checks whether evaluateBoard returns true and a player has won
                    if (evaluateBoard()) {
                        turn = 0;
                    } else {
                        turn++;
                    }

                    this.start(primaryStage); //Restarts the JavaFX application with the new data 
                } else {
                    int random = (int)(Math.random() * 6); //Creates the random Integer as an Integer between 0 and 6

                    if (numberOfPlayers == 1) {
                        aiTrashTalk[random].play(); //If true, plays the ai trash talk since its the human players turn
                    }

                    userContainer.getChildren().add(new ImageView(userAvatarImage[currentUser1AvatarIndex])); //Adds the current user 1 avatar to userContainer HBox
                    
                    //The for loop iterates 3 times through each row in the board
                    for (int row = 0; row < 3; row++) {

                        //The for loop iterates 3 times through each column in the board
                        for (int column = 0; column < 3; column++) {
                            int row2 = row;
                            int column2 = column;

                            //Creates the event object for whenever a square within the board is clicked
                            grid[row][column].setOnMouseClicked(e -> {

                                //Checks if the game isn't finished and the clicked square wasn't already chosen by the opponent 
                                if (turn != 0 && gridInput[row2][column2] != 2) {
                                    grid[row2][column2].setFill(new ImagePattern(userAvatarImage[currentUser1AvatarIndex])); //Changes the background of the clicked square to the user 1 avatar 
                                    gridInput[row2][column2] = 1; //Changes the value of the clicked square to one

                                    //Checks whether evaluateBoard returns true and a player has won
                                    if (evaluateBoard()) {
                                        turn = 0;
                                    } else {
                                        turn++;
                                    }; 

                                    aiTrashTalk[random].stop(); //Stops the trash talk audio
                                    this.start(primaryStage); //Restarts the JavaFX application with the new data 
                                };
                            });
                        };
                    };
                }; 

            } else {

                //Checks if the numberOfPlayers is equal to two
                if (numberOfPlayers == 2) {

                    userContainer.getChildren().add(new ImageView(userAvatarImage[currentUser2AvatarIndex])); //If true, adds the user 2 avatar to userContainer

                    //The for loop iterates 3 times through each row in the board
                    for (int row = 0; row < 3; row++) {

                        //The for loop iterates 3 times through each column in the board
                        for (int column = 0; column < 3; column++) {
                            int row2 = row;
                            int column2 = column;

                            //Creates the event object for whenever a square within the board is clicked
                            grid[row][column].setOnMouseClicked(e -> {

                                //Checks if the game isn't finished and the clicked square wasn't already chosen by the opponent 
                                if (turn != 0 && gridInput[row2][column2] != 1) {
                                    grid[row2][column2].setFill(new ImagePattern(userAvatarImage[currentUser2AvatarIndex])); //Changes the background of the clicked square to the user 2 avatar 
                                    gridInput[row2][column2] = 2; //Changes the value of the clicked square to two

                                    //Checks whether evaluateBoard returns true and a player has won
                                    if (evaluateBoard()) {
                                        turn = 0;
                                    } else {
                                        turn++;
                                    }; 

                                    this.start(primaryStage); //Restarts the JavaFX application with the new data 
                                }; 
                            });
                        }; 
                    };
                } else {
                    handleAI(computer2);  //Else, calls the handleAI method with the computer2 AI

                    //Checks whether evaluateBoard returns true and a player has won
                    if (evaluateBoard()) {
                        turn = 0;
                    } else {
                        turn++;
                    }; 

                    this.start(primaryStage); //Restarts the JavaFX application with the new data 
                }; 
            }; 
        }; 

        primaryStage.setScene(scene); //Places the scene within the primaryStage
        primaryStage.setTitle("Tic-Tac-Toe"); // Sets the title of the primaryStage
        primaryStage.show(); //Displays the primaryStage 
    }

    /** The public method main runs the program when launched within Eclipse, it is not required to run from the command line */
    public static void main(String[] args) {
        launch(args); //Launches the arguments to run the program within Eclipse 
    }

    /** The private method setUpGrid resets the board for the Tic-Tac-Toe game */
    private void setUpGrid() {
        gridContainer.getChildren().clear();
        gridContainer.setAlignment(Pos.CENTER);

        //The for loop iterates 3 times through each row in the board
        for (int row = 0; row < 3; row++) {

            //The for loop iterates 3 times through each column in the board
            for (int column = 0; column < 3; column++) {
                grid[row][column].setStroke(Color.BLACK); //Sets the color of the current square border to black 
                grid[row][column].setStrokeWidth(2); //Sets the width of the current square border to 2 
                grid[row][column].setFill(backgroundColors[currentBackgroundIndex]); //Sets the background of the current square to the currently selected background color
                gridContainer.add(grid[row][column], column, row); //Add the square to the board 
                gridInput[row][column] = 0; //Resets the value of the square to zero 
            };
        };
    }

    /** The private method handleAI manages the AI choices when its the computers turn */
    private void handleAI(AI computer) {
        computer.move(gridInput); //Calls the move method of the current computer AI
        int computerRow = computer.getRow(); //Retrieves the row value of the computer move
        int computerColumn = computer.getColumn(); //Retrieves the column value of the computer move

        //Checks if the computer represents the first AI
        if (computer == computer1) {
            grid[computerRow][computerColumn].setFill(new ImagePattern(AIAvatarImage[currentAI1AvatarIndex])); //If true, the background of the square chosen by the AI is changed to the AI 1 avatar
            gridInput[computerRow][computerColumn] = 1; //Changes the value of the square chosen by the AI to 1
        } else {
            grid[computerRow][computerColumn].setFill(new ImagePattern(AIAvatarImage[numberOfPlayers == 0 ? currentAI2AvatarIndex : currentAI1AvatarIndex])); //Else, the background of the square chosen by the AI is changed to the AI 2 avatar
            gridInput[computerRow][computerColumn] = 2; //Changes the value of the square chosen by the AI to 2
        }; 
    }

    /** The private method evaluateBoard evaluates the Tic-Tac-Toe board and returns if somebody won or it was a draw */
    private boolean evaluateBoard() {

        for (int i = 0; i < 3; i++) {

            //Checks if the current row has the same values
            if (gridInput[i][0] == gridInput[i][1] && gridInput[i][1] == gridInput[i][2] && gridInput[i][0] != 0) {
                turn = 0; //If true, then turn is changed to 0 to move on to the result screen 

                if (gridInput[i][0] == 1) {
                    currentScore1 = 10; //If true, sets currentScore1 to 10 since player 1 won
                    return true; 
                } else if (gridInput[i][0] == 2) {
                    currentScore2 = 10; //If true, sets currentScore2 to 10 since player 2 won
                    return true;
                }; 
            }; 

            //Checks if the current column has the same values
            if (gridInput[0][i] == gridInput[1][i] && gridInput[1][i] == gridInput[2][i] && gridInput[0][i] != 0) {
                turn = 0; //If true, then turn is changed to 0 to move on to the result screen 

                if (gridInput[0][i] == 1) {
                    currentScore1 = 10; //If true, sets currentScore1 to 10 since player 1 won
                    return true;
                } else if (gridInput[0][i] == 2) {
                    currentScore2 = 10; //If true, sets currentScore2 to 10 since player 2 won
                    return true;
                };
            };
        }; 

        //Checks if the diagonal rows and columns from left to right have the same value
        if (gridInput[0][0] == gridInput[1][1] && gridInput[1][1] == gridInput[2][2] && gridInput[0][0] != 0) {
            turn = 0; //If true, then turn is changed to 0 to move on to the result screen 

            if (gridInput[0][0] == 1) {
                currentScore1 = 10; //If true, sets currentScore1 to 10 since player 1 won
                return true;
            } else if (gridInput[0][0] == 2) {
                currentScore2 = 10; //If true, sets currentScore2 to 10 since player 2 won
                return true;
            }; 
        }; 

        //Checks if the diagonal rows and columns from right to left have the same value
        if (gridInput[0][2] == gridInput[1][1] && gridInput[1][1] == gridInput[2][0] && gridInput[0][2] != 0) {
            turn = 0; //If true, then turn is changed to 0 to move on to the result screen 

            if (gridInput[0][2] == 1) {
                currentScore1 = 10;  //If true, sets currentScore1 to 10 since player 1 won
                return true;
            } else if (gridInput[0][2] == 2) {
                currentScore2 = 10; //If true, sets currentScore2 to 10 since player 2 won
                return true;
            }; 
        }; 

        boolean isFilled = true;

        //The for loop iterates three times through each row in the board 
        for (int row = 0; row < 3; row++) {

            //The for loop iterates three times through each column in the board 
            for (int column = 0; column < 3; column++) {
                if (gridInput[row][column] == 0) {
                    isFilled = false; //If true, sets isFilled to false since the board hasn't been filled yet
                }; 
            }; 
        }; 

        return isFilled;
    }

    /** The private method setAvatarImage provides the user with the choice of selecting the avatar images */
    private void setAvatarImage(HBox container) {

        container.getChildren().clear();

        //Checks if there is a human player
        if (numberOfPlayers != 2) {

            VBox avatarAIVBox = new VBox(); //Creates the VBox avatarAIVBox as the container for the avatar images 
            avatarAIVBox.setAlignment(Pos.TOP_CENTER);

            ImageView avatarAIImage = new ImageView(AIAvatarImage[currentAI1AvatarIndex]); //Creates a new ImageView avatarAIImage to represent the AI 1 avatar 

            Label avatarAIText = new Label("Select Avatar for CPU", avatarAIImage); //Creates the Label avatarAITest for avatarAIImage to instruct the user to select an avatar for the AI 1
            avatarAIText.setContentDisplay(ContentDisplay.BOTTOM);

            Text avatarAIDifficulty = new Text(); //Creates the avatarAIDifficulty Text to inform the user of the difficulty represented by the avatar

            if (currentAI1AvatarIndex == 0) {
                avatarAIDifficulty.setText("CPU Level: Beginner"); //If true, updates avatarAIDifficulty to the beginner String 
                difficulty = 0; //Sets the difficulty of the AI to the beginner value 
            } else if (currentAI1AvatarIndex == 1) {
                avatarAIDifficulty.setText("CPU Level: Intermediate"); //If true, updates avatarAIDifficulty to the intermediate String 
                difficulty = 1; //Sets the difficulty of the AI to the intermediate value 
            } else if (currentAI1AvatarIndex == 2) {
                avatarAIDifficulty.setText("CPU Level: Difficult (Impossible)"); //If true, updates avatarAIDifficulty to the difficult String 
                difficulty = 2; //Sets the difficulty of the AI to the difficult value 
            }; 

            avatarAIText.setOnMouseClicked(e -> {
                //Checks if the currentAI1AvatarIndex is equal to two since then it will be the last possible avatar for an AI
                if (currentAI1AvatarIndex == 2) {
                    currentAI1AvatarIndex = 0; //If true, sets currentAI1AvatarIndex to zero to move back to the first possible avatar for an AI
                    difficulty = 0; //Sets the difficulty of the AI to the beginner value 
                } else {
                    currentAI1AvatarIndex++;
                    difficulty++;
                }; 

                avatarAIImage.setImage(AIAvatarImage[currentAI1AvatarIndex]); //Updates the avatar image for AI 1

                if (currentAI1AvatarIndex == 0) {
                    avatarAIDifficulty.setText("CPU Level: Beginner"); //If true, updates avatarAIDifficulty to the beginner String 
                } else if (currentAI1AvatarIndex == 1) {
                    avatarAIDifficulty.setText("CPU Level: Intermediate"); //If true, updates avatarAIDifficulty to the intermediate String
                } else if (currentAI1AvatarIndex == 2) {
                    avatarAIDifficulty.setText("CPU Level: Difficult (Impossible)"); //If true, updates avatarAIDifficulty to the difficult String 
                };
            });

            avatarAIVBox.getChildren().add(avatarAIImage);
            avatarAIVBox.getChildren().add(avatarAIText);
            avatarAIVBox.getChildren().add(avatarAIDifficulty);

            setBackground(container);
            container.getChildren().add(avatarAIVBox);

        } else if (numberOfPlayers == 2) { //Else, checks if all the players are human

            VBox avatarUser2VBox = new VBox(); //Creates the VBox avatarUser2VBox as the container for the user 2 images 
            avatarUser2VBox.setAlignment(Pos.TOP_CENTER);

            Text avatarUser2Text = new Text("Select Avatar for User 2"); //Creates the Text avatarUser2Text to instruct the user to select an avatar for the user 2
            avatarUser2VBox.getChildren().add(avatarUser2Text);

            ImageView avatarUser2Image = new ImageView(userAvatarImage[currentUser2AvatarIndex]); //Creates a new ImageView avatarUser2Image to represent the user 2 avatar 
            avatarUser2VBox.getChildren().add(avatarUser2Image);

            avatarUser2Image.setOnMouseClicked(e -> {
                //Checks if the currentUser2AvatarIndex is equal to five since then it will be the last possible avatar for a user
                if (currentUser2AvatarIndex == 5) {
                    currentUser2AvatarIndex = 0; //If true, sets currentUser2AvatarIndex to zero to move back to the first possible avatar for a user 
                } else {
                    currentUser2AvatarIndex++;
                }; 

                avatarUser2Image.setImage(userAvatarImage[currentUser2AvatarIndex]); //Updates the avatar image for user 2
            });

            setBackground(container);
            container.getChildren().add(avatarUser2VBox);

        }; 

        //Checks if there are no human players
        if (numberOfPlayers == 0) {

            VBox avatarAI2VBox = new VBox(); //Creates the VBox avatarAI2VBox as the container for the AI 2 images 
            avatarAI2VBox.setAlignment(Pos.TOP_CENTER);

            Text avatarAI2Text = new Text("Select Avatar for CPU 2"); //Creates the Text avatarAI2Text to instruct the user to select an avatar for AI 2
            avatarAI2VBox.getChildren().add(avatarAI2Text);

            ImageView avatarAI2Image = new ImageView(AIAvatarImage[currentAI2AvatarIndex]); //Creates a new ImageView avatarUser2Image to represent the AI 2 avatar 
            avatarAI2VBox.getChildren().add(avatarAI2Image);

            avatarAI2Image.setOnMouseClicked(e -> {
                //Checks if the currentAI2AvatarIndex is equal to two since then it will be the last possible avatar for an AI
                if (currentAI2AvatarIndex == 2) {
                    currentAI2AvatarIndex = 0; //If true, sets currentAI2AvatarIndex to zero to move back to the first possible avatar for an AI
                } else {
                    currentAI2AvatarIndex++;
                }; 

                avatarAI2Image.setImage(AIAvatarImage[currentAI2AvatarIndex]); //Updates the avatar image for AI 2
            });

            container.getChildren().add(avatarAI2VBox);

        } else {

            VBox avatarUser1VBox = new VBox(); //Creates the VBox avatarUser1VBox as the container for the user 1 images 
            avatarUser1VBox.setAlignment(Pos.TOP_CENTER);

            Text avatarUser1Text = new Text("Select Avatar for User 1"); //Creates the Text avatarUser1Text to instruct the user to select an avatar for the user 1
            avatarUser1VBox.getChildren().add(avatarUser1Text);

            ImageView avatarUser1Image = new ImageView(userAvatarImage[currentUser1AvatarIndex]); //Creates a new ImageView avatarUser1Image to represent the user 1 avatar 
            avatarUser1VBox.getChildren().add(avatarUser1Image);

            avatarUser1Image.setOnMouseClicked(e -> {
                //Checks if the currentUser1AvatarIndex is equal to five since then it will be the last possible avatar for a user
                if (currentUser1AvatarIndex == 5) {
                    currentUser1AvatarIndex = 0; //If true, sets currentUser1AvatarIndex to zero to move back to the first possible avatar for a user 
                } else {
                    currentUser1AvatarIndex++;
                }; 

                avatarUser1Image.setImage(userAvatarImage[currentUser1AvatarIndex]); //Updates the avatar image for user 1
            });

            container.getChildren().add(avatarUser1VBox); //Adds the avatarUser1VBox VBox to container 
        }; 
    }

    /** The private method setBackground provides the user with the choice of selecting the background color of the Tic-Tac-Toe board */
    private void setBackground(HBox container) {
        Rectangle white = new Rectangle(50, 50);
        white.setFill(Color.WHITE);

        Rectangle red = new Rectangle(50, 50);
        red.setFill(Color.RED);

        Rectangle turquoise = new Rectangle(50, 50);
        turquoise.setFill(Color.TURQUOISE);

        Rectangle darkGreen = new Rectangle(50, 50);
        darkGreen.setFill(Color.DARKGREEN);

        Rectangle pink = new Rectangle(50, 50);
        pink.setFill(Color.PINK);

        Rectangle lavender = new Rectangle(50, 50);
        lavender.setFill(Color.LAVENDER);

        Rectangle[] backgroundRectangles = {white, red, turquoise, darkGreen, pink, lavender};
        Text[] rectangleText = {new Text("White (Default)"), new Text("Red"), new Text("Turquoise"), new Text("Dark Green"), new Text("Pink"), new Text("Lavender")}; //Creates a Text Array rectangleText with all of the labels for the previously created Rectangle objects

        VBox backgroundVBox = new VBox(); //Creates the VBox backgroundVBox as a container for the selected background color
        backgroundVBox.setAlignment(Pos.TOP_CENTER);

        Text backgroundText = new Text("Select Background"); //Creates the Text backgroundText to instruct the user to select a background 

        backgroundVBox.getChildren().add(backgroundText); 
        backgroundVBox.getChildren().add(backgroundRectangles[currentBackgroundIndex]);
        backgroundVBox.getChildren().add(rectangleText[currentBackgroundIndex]);

        backgroundVBox.setOnMouseClicked(e -> {
            //Checks if the currentBackgroundIndex is equal to 5 since then it will be the last possible background for the board
            if (currentBackgroundIndex == 5) {
                currentBackgroundIndex = 0; //If true, sets currentBackgroundIndex to zero to move back to the first possible background
            } else {
                currentBackgroundIndex++;
            }; 

            backgroundVBox.getChildren().clear();
            backgroundVBox.getChildren().add(backgroundText);
            backgroundVBox.getChildren().add(backgroundRectangles[currentBackgroundIndex]);
            backgroundVBox.getChildren().add(rectangleText[currentBackgroundIndex]);
        });

        container.getChildren().add(backgroundVBox);
    }

    /** The private method winAnimation plays the win animation for the user */
    private void winAnimation(Text congratulationString) {
        animationHBox.setAlignment(Pos.CENTER);

        ImageView star1 = new ImageView(new Image("animation_image/star.png"));
        ImageView star2 = new ImageView(new Image("animation_image/star.png"));
        ImageView star3 = new ImageView(new Image("animation_image/star.png"));
        ImageView star4 = new ImageView(new Image("animation_image/star.png"));

        ImageView streamerBlue = new ImageView(new Image("animation_image/streamer_blue.png"));
        ImageView streamerGreen = new ImageView(new Image("animation_image/streamer_green.png"));
        ImageView streamerOrange = new ImageView(new Image("animation_image/streamer_orange.png"));
        ImageView streamerPeach = new ImageView(new Image("animation_image/streamer_peach.png"));
        ImageView streamerPurple = new ImageView(new Image("animation_image/streamer_purple.png"));
        ImageView streamerRed = new ImageView(new Image("animation_image/streamer_red.png"));

        animationHBox.getChildren().add(streamerBlue);
        animationHBox.getChildren().add(streamerGreen); 
        animationHBox.getChildren().add(star1);
        animationHBox.getChildren().add(streamerOrange);
        animationHBox.getChildren().add(star2);
        animationHBox.getChildren().add(star3);
        animationHBox.getChildren().add(streamerPeach); 
        animationHBox.getChildren().add(streamerPurple);
        animationHBox.getChildren().add(star4);
        animationHBox.getChildren().add(streamerRed);

        TranslateTransition translateBlue = new TranslateTransition(Duration.millis(3000), streamerBlue);
        translateBlue.setFromY(0); //Begins the animation at the point where y = 0 within the coordinate graph
        translateBlue.setToY(500); //Moves the specified ImageView to where y = 500 point within the coordinate graph
        translateBlue.setCycleCount(1); //Sets the number of times the animation repeats to one
        translateBlue.play();

        TranslateTransition translateGreen = new TranslateTransition(Duration.millis(4000), streamerGreen);
        translateGreen.setFromY(0); //Begins the animation at the point where y = 0 within the coordinate graph
        translateGreen.setToY(500); //Moves the specified ImageView to where y = 500 point within the coordinate graph
        translateGreen.setCycleCount(1); //Sets the number of times the animation repeats to one
        translateGreen.play();

        TranslateTransition translateOrange = new TranslateTransition(Duration.millis(3500), streamerOrange);
        translateOrange.setFromY(0); //Begins the animation at the point where y = 0 within the coordinate graph
        translateOrange.setToY(500); //Moves the specified ImageView to where y = 500 point within the coordinate graph
        translateOrange.setCycleCount(1); //Sets the number of times the animation repeats to one
        translateOrange.play();

        TranslateTransition translatePeach = new TranslateTransition(Duration.millis(5000), streamerPeach);
        translatePeach.setFromY(0); //Begins the animation at the point where y = 0 within the coordinate graph
        translatePeach.setToY(500); //Moves the specified ImageView to where y = 500 point within the coordinate graph 
        translatePeach.setCycleCount(1); //Sets the number of times the animation repeats to one
        translatePeach.play();

        //Creates an event object for when the TranslateTransition animation ends to quit the entire process
        translatePeach.setOnFinished(e -> {
            animationHBox.getChildren().clear();
        });

        TranslateTransition translatePurple = new TranslateTransition(Duration.millis(3000), streamerPurple); 
        translatePurple.setFromY(0); //Begins the animation at the point where y = 0 within the coordinate graph
        translatePurple.setToY(500); //Moves the specified ImageView to where y = 500 point within the coordinate graph
        translatePurple.setCycleCount(1); //Sets the number of times the animation repeats to one
        translatePurple.play();

        TranslateTransition translateRed = new TranslateTransition(Duration.millis(4000), streamerRed); 
        translateRed.setFromY(0); //Begins the animation at the point where y = 0 within the coordinate graph
        translateRed.setToY(500); //Moves the specified ImageView to where y = 500 point within the coordinate graph
        translateRed.setCycleCount(1); //Sets the number of times the animation repeats to one
        translateRed.play();

        TranslateTransition translateStar1 = new TranslateTransition(Duration.millis(4500), star1);
        translateStar1.setFromY(0); //Begins the animation at the point where y = 0 within the coordinate graph
        translateStar1.setToY(500); //Moves the specified ImageView to where y = 500 point within the coordinate graph
        translateStar1.setCycleCount(1); //Sets the number of times the animation repeats to one
        translateStar1.play();

        TranslateTransition translateStar2 = new TranslateTransition(Duration.millis(3000), star2); 
        translateStar2.setFromY(0); //Begins the animation at the point where y = 0 within the coordinate graph
        translateStar2.setToY(500); //Moves the specified ImageView to where y = 500 point within the coordinate graph 
        translateStar2.setCycleCount(1); //Sets the number of times the animation repeats to one
        translateStar2.play();

        TranslateTransition translateStar3 = new TranslateTransition(Duration.millis(4000), star3); 
        translateStar3.setFromY(0); //Begins the animation at the point where y = 0 within the coordinate graph
        translateStar3.setToY(500); //Moves the specified ImageView to where y = 500 point within the coordinate graph 
        translateStar3.setCycleCount(1); //Sets the number of times the animation repeats to one
        translateStar3.play();

        TranslateTransition translateStar4 = new TranslateTransition(Duration.millis(3000), star4); 
        translateStar4.setFromY(0); //Begins the animation at the point where y = 0 within the coordinate graph
        translateStar4.setToY(500); //Moves the specified ImageView to where y = 500 point within the coordinate graph
        translateStar4.setCycleCount(1); //Sets the number of times the animation repeats to one
        translateStar4.play();

        congratulationString.setFill(Color.DARKORANGE);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(200), congratulationString);
        fadeTransition.setFromValue(0); //Begins the animation where the text color is fully transparent
        fadeTransition.setToValue(1); //Ends the animation where the text color is fully opaque
        fadeTransition.setCycleCount(10); //Sets the number of times the animation repeats to ten
        fadeTransition.play();
    }

    /** The private method loseAnimation plays the lose animation for the user */
    private void loseAnimation (Text loseString) {
        animationHBox.setAlignment(Pos.CENTER);

        ImageView sad1 = new ImageView(new Image("animation_image/sad.jpg"));
        animationHBox.getChildren().add(sad1);
        ImageView sad2 = new ImageView(new Image("animation_image/sad.jpg"));
        animationHBox.getChildren().add(sad2);
        ImageView sad3 = new ImageView(new Image("animation_image/sad.jpg"));
        animationHBox.getChildren().add(sad3);
        ImageView sad4 = new ImageView(new Image("animation_image/sad.jpg"));
        animationHBox.getChildren().add(sad4);
        ImageView sad5 = new ImageView(new Image("animation_image/sad.jpg"));
        animationHBox.getChildren().add(sad5);

        TranslateTransition translateSad1 = new TranslateTransition(Duration.millis(3500), sad1);
        translateSad1.setFromY(0); //Begins the animation at the point where y = 0 within the coordinate graph
        translateSad1.setToY(500); //Moves the specified ImageView to where y = 500 point within the coordinate graph
        translateSad1.setCycleCount(1); //Sets the number of times the animation repeats to one
        translateSad1.play();

        TranslateTransition translateSad2 = new TranslateTransition(Duration.millis(4500), sad2);
        translateSad2.setFromY(0); //Begins the animation at the point where y = 0 within the coordinate graph
        translateSad2.setToY(500); //Moves the specified ImageView to where y = 500 point within the coordinate graph 
        translateSad2.setCycleCount(1); //Sets the number of times the animation repeats to one
        translateSad2.play();

        TranslateTransition translateSad3 = new TranslateTransition(Duration.millis(4000), sad3);
        translateSad3.setFromY(0); //Begins the animation at the point where y = 0 within the coordinate graph
        translateSad3.setToY(500); //Moves the specified ImageView to where y = 500 point within the coordinate graph
        translateSad3.setCycleCount(1); //Sets the number of times the animation repeats to one
        translateSad3.play();

        TranslateTransition translateSad4 = new TranslateTransition(Duration.millis(5500), sad4);
        translateSad4.setFromY(0); //Begins the animation at the point where y = 0 within the coordinate graph
        translateSad4.setToY(500); //Moves the specified ImageView to where y = 500 point within the coordinate graph
        translateSad4.setCycleCount(1); //Sets the number of times the animation repeats to one
        translateSad4.play();

        //Creates an event object for when the TranslateTransition animation ends to quit the entire process
        translateSad4.setOnFinished(e -> {
            animationHBox.getChildren().clear();
        });

        TranslateTransition translateSad5 = new TranslateTransition(Duration.millis(3500), sad5);
        translateSad5.setFromY(0); //Begins the animation at the point where y = 0 within the coordinate graph
        translateSad5.setToY(500); //Moves the specified ImageView to where y = 500 point within the coordinate graph
        translateSad5.setCycleCount(1); //Sets the number of times the animation repeats to one
        translateSad5.play();

        loseString.setFill(Color.BLUE);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.2), loseString);
        fadeTransition.setFromValue(0); //Begins the animation where the text color is fully transparent
        fadeTransition.setToValue(1); //Ends the animation where the text color is fully opaque
        fadeTransition.setCycleCount(10); //Sets the number of times the animation repeats to ten
        fadeTransition.play();
    }

    /** The private method drawAnimation plays the draw animation for the user */
    private void drawAnimation(Text drawString) {
        animationHBox.setAlignment(Pos.CENTER);

        ImageView oopsEmoji1 = new ImageView(new Image("animation_image/oops2.png"));
        ImageView oopsEmoji2 = new ImageView(new Image("animation_image/oops2.png"));
        ImageView oopsEmoji3 = new ImageView(new Image("animation_image/oops2.png"));
        ImageView oopsEmoji4 = new ImageView(new Image("animation_image/oops2.png"));

        ImageView oops1 = new ImageView(new Image("animation_image/oops.png"));
        ImageView oops2 = new ImageView(new Image("animation_image/oops.png"));
        ImageView oops3 = new ImageView(new Image("animation_image/oops.png"));

        animationHBox.getChildren().add(oopsEmoji1);
        animationHBox.getChildren().add(oops1);
        animationHBox.getChildren().add(oopsEmoji2);
        animationHBox.getChildren().add(oopsEmoji3);
        animationHBox.getChildren().add(oops2);
        animationHBox.getChildren().add(oopsEmoji4);
        animationHBox.getChildren().add(oops3);

        TranslateTransition translateDraw1 = new TranslateTransition(Duration.millis(3000), oopsEmoji1);
        translateDraw1.setFromY(0); //Begins the animation at the point where y = 0 within the coordinate graph
        translateDraw1.setToY(500); //Moves the specified ImageView to where y = 500 point within the coordinate graph
        translateDraw1.setCycleCount(1); //Sets the number of times the animation repeats to one
        translateDraw1.play();

        TranslateTransition translateDraw2 = new TranslateTransition(Duration.millis(4000), oops1);
        translateDraw2.setFromY(0); //Begins the animation at the point where y = 0 within the coordinate graph
        translateDraw2.setToY(500); //Moves the specified ImageView to where y = 500 point within the coordinate graph
        translateDraw2.setCycleCount(1); //Sets the number of times the animation repeats to one
        translateDraw2.play();

        TranslateTransition translateDraw3 = new TranslateTransition(Duration.millis(4000), oopsEmoji2);
        translateDraw3.setFromY(0); //Begins the animation at the point where y = 0 within the coordinate graph
        translateDraw3.setToY(500); //Moves the specified ImageView to where y = 500 point within the coordinate graph
        translateDraw3.setCycleCount(1); //Sets the number of times the animation repeats to one
        translateDraw3.play();

        TranslateTransition translateDraw4 = new TranslateTransition(Duration.millis(5000), oopsEmoji3);
        translateDraw4.setFromY(0); //Begins the animation at the point where y = 0 within the coordinate graph
        translateDraw4.setToY(500); //Moves the specified ImageView to where y = 500 point within the coordinate graph
        translateDraw4.setCycleCount(1); //Sets the number of times the animation repeats to one
        translateDraw4.play();

        //Creates an event object for when the TranslateTransition animation ends to quit the entire process
        translateDraw4.setOnFinished(e -> {
            animationHBox.getChildren().clear();
        });

        TranslateTransition translateDraw5 = new TranslateTransition(Duration.millis(3500), oops2);
        translateDraw5.setFromY(0); //Begins the animation at the point where y = 0 within the coordinate graph
        translateDraw5.setToY(500); //Moves the specified ImageView to where y = 500 point within the coordinate graph
        translateDraw5.setCycleCount(1); //Sets the number of times the animation repeats to one
        translateDraw5.play();

        TranslateTransition translateDraw6 = new TranslateTransition(Duration.millis(4000), oopsEmoji4);
        translateDraw6.setFromY(0); //Begins the animation at the point where y = 0 within the coordinate graph
        translateDraw6.setToY(500); //Moves the specified ImageView to where y = 500 point within the coordinate graph
        translateDraw6.setCycleCount(1); //Sets the number of times the animation repeats to one
        translateDraw6.play();

        TranslateTransition translateDraw7 = new TranslateTransition(Duration.millis(4500), oops3);
        translateDraw7.setFromY(0); //Begins the animation at the point where y = 0 within the coordinate graph
        translateDraw7.setToY(500); //Moves the specified ImageView to where y = 500 point within the coordinate graph
        translateDraw7.setCycleCount(1); //Sets the number of times the animation repeats to one
        translateDraw7.play();

        drawString.setFill(Color.RED);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.2), drawString);
        fadeTransition.setFromValue(0); //Begins the animation where the text color is fully transparent
        fadeTransition.setToValue(1); //Ends the animation where the text color is fully opaque
        fadeTransition.setCycleCount(10); //Sets the number of times the animation repeats to ten
        fadeTransition.play();
    }

}