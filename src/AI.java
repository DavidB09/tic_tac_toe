/**
 * Developer: David Brunner, David Brunner
 * Description: Creates a Main class that extends Application and implements JavaFX features to create a representation of a Tic-Tac-Toe game 
 */

/** The public class AI contains several methods and data fields for receiving a Tic-Tac-Toe board and determining the move for an AI */
public class AI {
    int[][] grid; //Creates the Integer Array grid to represent the board 
    int level; //Creates the Integer level to represent the skill level of the AI

    int row = -1; //Creates the Integer row to represent the row value of the next AI move
    int column = -1; //Creates the Integer column to represent the column value of next AI move

    int numOfPlayer; //Creates the Integer numOfPlayer to represent which player the current AI represents

    /** The constructor for the AI object with receives the Tic-Tac-Toe board, the skill level, and the player number */
    AI(int[][] grid, int level, int numOfPlayer) {
        this.grid = grid;
        this.level = level;
        this.numOfPlayer = numOfPlayer;

        //Checks whether the current AI represents the first player
        if (numOfPlayer == 1) {
            switchGrid();
        }; 
    }

    /** The public method getRow is the getter method for the row data field */
    public int getRow() {
        return this.row; //Returns the row value of the next AI move
    }

    /** The public method getColumn is the getter method for the column data field */
    public int getColumn() {
        return this.column; //Returns the column value of the next AI move
    }

    /** The public method move determines the skill level of the AI and calls the evaluation methods accordingly */
    public void move(int[][] updatedGrid) {
        this.grid = updatedGrid; //Sets the grid value to the newest updated version of the board

        //Checks whether the AI represents the first player
        if (numOfPlayer == 1) {
            switchGrid();
        };

        //Checks whether the AI skill level is difficult
        if (level == 2) {
            findBestHardMove();
        } else if (level == 1) { //Else, checks whether the AI skill level is intermediate
            findBestIntermediateMove();
        } else {
            findBestEasyMove();
        };
    }

    /** The private method findBestHardMove evaluates the Tic-Tac-Toe board and decided on the best possible move */
    private void findBestHardMove() {
        int bestValue = -1000; //Creates the Integer bestValue and sets it to very low number

        //The for loop iterates three times through each row in the board
        for (int row = 0; row < 3; row++) {

            //The for loop iterates three times through each column in the board
            for (int column = 0; column < 3; column++) {

                //Checks whether the current square in the board is unselected
                if (grid[row][column] == 0) {
                    grid[row][column] = 2; //If true, then the board is updated with the AI choosing the current square
                    
                    int moveValue = findMinMax(grid, 0, false); //Calls the findMinMax method to find the value of the current AI choice

                    //Checks whether the value of the current AI choice is higher than the previous AI choice
                    if (moveValue > bestValue) {
                        bestValue = moveValue; //If true, then bestValue is updated to the current moveValue
                        this.row = row; //The row is updated to the current row position
                        this.column = column; //The column is updated to the current column position
                    }; 

                    grid[row][column] = 0; //The current AI choice is reversed to continue looking for the best possible move
                }; 
            }; 
        }; 
    }

    /** The private method findMinMax evaluates the Tic-Tac-Toe board and calculates the score of the current AI choice */
    private int findMinMax(int[][] board, int depth, boolean isMax) {
        int score = evaluateBoard(); //Creates the Integer score and initializes it to the result of calling the evaluateBoard method

        //Checks whether the score represents a win for the AI
        if (score == 10) {
            return score - depth; //If true, then the score minus the number of turns is returned
        } else if (score == -10) { //Else, checks whether the score represents a loss for the AI
            return score + depth; //If true, then the score plus the number of turns is returned
        } else if (!movesLeft()) { //Else, calls the movesLeft function and checks whether its return value is equal to false
            return 0; //If true, then zero is returned since the board is full
        }; 

        //Checks whether the current recursion call represents the AI
        if (isMax) {
            int bestValue = -1000; //Creates the Integer bestValue and initializes it to a very low number

            //The for loop iterates three times through each row in the board
            for (int row = 0; row < 3; row++) {

                //The for loop iterates three times through each column in the board
                for (int column = 0; column < 3; column++) {

                    //Checks whether the current square in the board is unselected
                    if (grid[row][column] == 0) {
                        grid[row][column] = 2; //If true, then the board is updated with the AI choosing the current square

                        bestValue = Math.max(bestValue, findMinMax(grid, depth + 1, !isMax)); //Finds the higher value between bestValue or the result of a recursive call to findMinMax

                        grid[row][column] = 0; //The current AI choice is reversed to continue looking for the best possible move
                    }; 
                }; 
            };

            return bestValue; //The bestValue is returned
        } else {
            int bestValue = 1000; //Creates the Integer bestValue and initializes it to a very high number

            //The for loop iterates three times through each row in the board
            for (int row = 0; row < 3; row++) {

                //The for loop iterates three times through each column in the board
                for (int column = 0; column < 3; column++) {

                    //Checks whether the current square in the board is unselected
                    if (grid[row][column] == 0) {
                        grid[row][column] = 1; //If true, then the board is updated with the opponent choosing the current square

                        bestValue = Math.min(bestValue, findMinMax(grid, depth + 1, !isMax)); //Finds the lower value between bestValue or the result of a recursive call to findMinMax

                        grid[row][column] = 0; //The current opponent choice is reversed to continue looking for the best possible move
                    }; 
                }; 
            };

            return bestValue; //The bestValue is returned
        }
    }

    /** The private method evalueBoard evaluates the Tic-Tac-Toe board and returns who won or if it was a draw */
    private int evaluateBoard() {

        for (int i = 0; i < 3; i++) {

            //Checks whether the current row has the same values
            if (grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2]) {

                //If true, then the first value of the current row is checked whether it was the AI 
                if (grid[i][0] == 2) {
                    return 10; //If true, then 10 is returned since the AI won
                } else if (grid[i][0] == 1){ //Else, the first value of the current row is checked whether it was the opponent
                    return -10; //If true, then -10 is returned since the AI lost
                }; 
            }; 

            //Checks whether the current column has the same values
            if (grid[0][i] == grid[1][i] && grid[1][i] == grid[2][i]) {

                //If true, then the first value of the current column is checked whether it was the AI 
                if (grid[0][i] == 2) {
                    return 10; //If true, then 10 is returned since the AI won
                } else if (grid[0][i] == 1){ //Else, the first value of the current column is checked whether it was the opponent
                    return -10; //If true, then -10 is returned since the AI lost
                }; 
            }; 
        }; 

        //Checks if the diagonal rows and columns from left to right have the same value
        if (grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2]) {

            //If true, then the first value of the first row is checked whether it was the AI 
            if (grid[0][0] == 2) {
                return 10; //If true, then 10 is returned since the AI won
            } else if (grid[0][0] == 1){ //Else, the first value of the first column and row is checked whether it was the opponent
                return -10; //If true, then -10 is returned since the AI lost
            }; 
        }; 

        //Checks if the diagonal rows and columns from right to left have the same value
        if (grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0]) {

            //If true, then the last value of the first row is checked whether it was the AI 
            if (grid[0][2] == 2) {
                return 10; //If true, then 10 is returned since the AI won
            } else if (grid[0][2] == 1){ //Else, the last value of the first column and row is checked whether it was the opponent
                return -10; //If true, then -10 is returned since the AI lost
            }; 
        }; 

        return 0; //Zero is returned since nobody won
    }

    /** The private method movesLeft evaluates the Tic-Tac-Toe board and returns whether a move is left */
    private boolean movesLeft() {

        //The for loop iterates three times through each row in the board
        for (int row = 0; row < 3; row++) {

            //The for loop iterates three times through each column in the board
            for (int column = 0; column < 3; column++) {

                //Checks whether the current square is unselected
                if (grid[row][column] == 0) {
                    return true;
                }; 
            }; 
        }; 

        return false; //False is returned since every square is selected
    }

    /** The private method findBestIntermediateMove evaluates the Tic-Tac-Toe board and selects a random square unless there are 2 in a row, where it chooses to block */
    private void findBestIntermediateMove() {
        boolean done = false; //Creates the boolean done to represent whether the AI is finished making the choice

        for (int i = 0; i < 3; i++) {

            //Checks whether the current row only has the first two squares filled with the same value
            if (grid[i][0] == 1 && grid[i][1] == 1 && grid[i][2] == 0) {
                this.row = i; this.column = 2; //If true, then row is set to the current row and column is set to 2
                done = true;
                break;
            } else if (grid[i][0] == 0 && grid[i][1] == 1 && grid[i][2] == 1) { //Else, checks whether the current row only has the last two squares filled with the same value
                this.row = i; this.column = 0; //If true, then row is set to the current row and column is set to 0
                done = true;
                break;
            } else if (grid[i][0] == 1 && grid[i][1] == 0 && grid[i][2] == 1) { //Else, checks whether the current row only has the first and last square filled with the same value
                this.row = i; this.column = 1; //If true, then row is set to the current row and column is set to 1
                done = true;
                break; 
            } else if (grid[0][i] == 1 && grid[1][i] == 1 && grid[2][i] == 0) { //Else checks whether the current column only has the first two squares filled with the same value
                this.row = 2; this.column = i; //If true, then row is set to 2 and column is set to the current column
                done = true;
                break; 
            } else if (grid[0][i] == 0 && grid[1][i] == 1 && grid[2][i] == 1) { //Else, checks whether the current column only has the last two squares filled with the same value
                this.row = 0; this.column = i; //If true, then row is set to 0 and column is set to the current column
                done = true;
                break; 
            } else if (grid[0][i] == 1 && grid[1][i] == 0 && grid[2][i] == 1) { //Else, checks whether the current column only has the first and last square filled with the same value
                this.row = 1; this.column = i; //If true, then row is set to 1 and column is set to the current column
                done = true;
                break; 
            }; 
        }; 

        //Checks whether the AI hasn't made the choice
        if (!done) {

            //Checks whether the diagonal rows and column from left to right only have the first two squares filled with the same value
            if (grid[0][0] == 1 && grid[1][1] == 1 && grid[2][2] == 0) {
                this.row = 2; this.column = 2; //If true, then row is set to 2 and column is set to 2
                done = true;
            } else if (grid[0][0] == 0 && grid[1][1] == 1 && grid[2][2] == 1) { //Else, checks whether the diagonal rows and column from left to right only have the last two squares filled with the same value
                this.row = 0; this.column = 0; //If true, then row is set to 0 and column is set to 0
                done = true;
            } else if (grid[0][0] == 1 && grid[1][1] == 0 && grid[2][2] == 1) { //Else, checks whether the diagonal rows and column from left to right only have the first and last squares filled with the same value
                this.row = 1; this.column = 1; //If true, then row is set to 1 and column is set to 1
                done = true;
            } else if (grid[0][2] == 1 && grid[1][1] == 1 && grid[2][0] == 0) { //Else, checks whether the diagonal rows and column from right to left only have the first two squares filled with the same value
                this.row = 2; this.column = 0; //If true, then row is set to 2 and column is set to 0
                done = true;
            } else if (grid[0][2] == 0 && grid[1][1] == 1 && grid[2][0] == 1) { //Else, checks whether the diagonal rows and column from right to left only have the last two squares filled with the same value
                this.row = 0; this.column = 2; //If true, then row is set to 0 and column is set to 2
                done = true;
            } else if (grid[0][2] == 1 && grid[1][1] == 0 && grid[2][0] == 1) { //Else, checks whether the diagonal rows and column from right to left only have the first and last squares filled with the same value
                this.row = 1; this.column = 1;  //If true, then row is set to 1 and column is set to 1
                done = true;
            }; 
        }; 

        //Checks whether the AI hasn't made the choice
        if (!done) {
            findBestEasyMove();
        }; 
    }

    /** The private method findBestEasyMove evaluates the Tic-Tac-Toe board and selects a random square */
    private void findBestEasyMove() {
        int randomRow = (int)(Math.random() * 3); //Creates the Integer randomRow and sets it to a random Integer between 0 and 3
        int randomColumn = (int)(Math.random() * 3);  //Creates the Integer randomRow and sets it to a random Integer between 0 and 3

        //The while loop continues until the square designated by the randomRow and randomColumn is empty
        while (grid[randomRow][randomColumn] != 0) {
            randomRow = (int)(Math.random() * 3); //randomRow is set to a random Integer between 0 and 3
            randomColumn = (int)(Math.random() * 3); //randomColumn is set to a random Integer between 0 and 3
        }; 

        this.row = randomRow;
        this.column = randomColumn;
    }

    /** The private method switchGrid switches each 1 and 2 value in the Tic-Tac-Toe board */
    private void switchGrid() {
        int[][] newGrid = new int[3][3]; //Creates a Integer Array newGrid with three rows and three column

        //The for loop iterates three times through each row in the board
        for (int row = 0; row < 3; row++) {

            //The for loop iterates three times through each column in the board
            for (int column = 0; column < 3; column++) {

                if (grid[row][column] == 1) {
                    newGrid[row][column] = 2;
                } else if (grid[row][column] == 2) {
                    newGrid[row][column] = 1;
                } else {
                    newGrid[row][column] = 0;
                }; 
            }; 
        }; 

        this.grid = newGrid; //Sets the board to the switched board 
    }
}
