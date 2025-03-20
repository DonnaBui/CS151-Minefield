package mineField;
import java.util.Random;

import mvc.*;

public class Minefield extends Model {
    private int playerRow = 0, playerCol = 0; // Starts at the top left corner
    private int gridSize = 10; // By default, it's 10x10, but this can be changed if needed. 
    // However, window size needs to be increased for text to be visible with higher size numbers.
    private int[] goalPosition; // Allows for the position of the goal to be customized. 
    private int[][] adjMines;
    private boolean[][] mines, uncovered;
    private boolean gameEnded = false;
    
    public Minefield() {
        mines = new boolean[gridSize][gridSize];
        uncovered = new boolean[gridSize][gridSize]; 
        adjMines = new int[gridSize][gridSize];
        goalPosition = new int[] {gridSize - 1, gridSize - 1}; // By default, the bottom right corner 
        uncovered[0][0] = true; // Starting point is automatically uncovered
        setMines();
        setAdjMines();
    }

    // Some useful constructors 
    public int getPlayerRow() {
        return playerRow;
    }
    public int getPlayerCol() {
        return playerCol;
    }
    public int getSize() {
        return gridSize;
    }
    public boolean gameEnded() {
        return gameEnded;
    }
    public int[] goalPos() {
        return goalPosition;
    }
    public boolean inBounds(int row, int col) {
        if (row >= 0 && row < gridSize && col >= 0 && col < gridSize) {
            return true;
        }
        return false;
    }

    public boolean isUncovered(int row, int col) {
        return uncovered[row][col];
    }

    public boolean isMine(int row, int col) {
        return mines[row][col];
    }

    public int getAdjMines(int row, int col) {
        return adjMines[row][col];
    }

    public void uncover(int row, int col) {
        if (!uncovered[row][col]) { 
            uncovered[row][col] = true;
        }
    }

    private void setMines() { // Generate the mines
        Random rand = new Random();  // in a 10x10 grid, there will be 10 mines
        int mineCount = gridSize; // 15x15, 15 mines. 20x20, 20 mines.
        int placedMines = 0;
        while (placedMines < mineCount) {
            int i = rand.nextInt(gridSize);
            int j = rand.nextInt(gridSize);
            // Ensure that mines aren't duplicated, the starting position isn't mined, and the end goal isn't mined
            if (!mines[i][j] && !uncovered[i][j] && i != goalPosition[0] && j != goalPosition[1]){
                mines[i][j] = true;
                placedMines++;
            }
        }
    }

    // Set the # of adjacent mines for every cell in the grid (allows for easy access later on)
    private void setAdjMines() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (!isMine(i,j)) {
                    int mines = 0;
                    for (int x = -1; x <= 1; x++) { 
                        for (int y = -1; y<= 1; y++) {
                            int r = i + x;
                            int c = j + y;
                            // Only check the adjacent squares that are in range of the grid
                            if (inBounds(r,c) && isMine(r,c)) {
                                mines++;
                            }
                        }
                    }
                    adjMines[i][j] = mines;
                }
            }
        }
    }

    public void move(Direction direction) {
        int newRow = getPlayerRow() + direction.getRowDir();
        int newCol = getPlayerCol() + direction.getColDir();
        
        if (gameEnded == true) throw new IllegalArgumentException("The game has ended. Please start a new game to continue.");
        else if (!inBounds(newRow, newCol)) throw new IllegalArgumentException("Out of bounds! Please choose a different direction.");
        else if (isMine(newRow,newCol)) {
            gameEnded = true;
            changed(); // Reveals all mines once game has ended
            throw new IllegalArgumentException("Oh no! You stepped on a mine. Game over :(");
        }
        else if (newRow == goalPos()[0] && newCol == goalPos()[1]) {
            gameEnded = true;
            changed(); 
            throw new IllegalArgumentException("You successfully reached the goal! You win :)");
        }
        else {
            playerRow = newRow;
            playerCol = newCol;
            uncover(playerRow,playerCol);
            changed(); // from Model, sets changed flag and fires changed event
        }
        
    }

}
