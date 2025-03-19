package mineField;
import java.util.Random;

import mvc.*;

public class Minefield extends Model {
    private int playerRow = 0, playerCol = 0;
    private int gridSize = 10; // By default, it's 10x10, but the player can change it if they want
    private boolean[][] mines, uncovered;
    private boolean gameEnded = false;
    
    // Some useful constructors that might come in handy
    public int getPlayerRow() {
        return playerRow;
    }
    public int getPlayerCol() {
        return playerCol;
    }
    public int getSize() {
        return gridSize;
    }

    public void changeSize() {} // tbd
    
    public Minefield() {
        mines = new boolean[gridSize][gridSize];
        uncovered = new boolean[gridSize][gridSize]; 
        setMines();
    }

    private void setMines() {
        Random rand = new Random();  // in a 10x10 grid, there will be 10 mines
        int mineCount = gridSize; // 15x15, 15 mines. 20x20, 20 mines.
        int placedMines = 0;
        while (placedMines < mineCount) {
            int i = rand.nextInt(gridSize);
            int j = rand.nextInt(gridSize);
            if (!mines[i][j]){
                mines[i][j] = true;
                placedMines++;
            }
        }
    }

    public boolean inBounds(int row, int col) {
        if (row >= 0 && row < gridSize && col >= 0 && col < gridSize) {
            return true;
        }
        return false;
    }

    public int nearbyMines(int row, int col) {
        int nearbyMines = 0;
        for (int x = -1; x <= 1; x++) { 
            for (int y = -1; y<= 1; y++) {
                int r = row + x;
                int c = col + y;
                // Only check the adjacent squares that are in range of the grid
                if (inBounds(r,c) && mines[r][c]) {
                    nearbyMines++;
                }
            }
        }
        return nearbyMines;
    }

    public boolean[][] uncoveredCells() {
        return uncovered;
    }

    public boolean isMine(int row, int col) {
        return mines[row][col];
    }

    public void uncover(int row, int col) {
        if (!uncovered[row][col]) { 
            uncovered[row][col] = true;
            changed();
        }
    }
    public void move(int rowModifier, int colModifier) {
        int newRow = getPlayerRow() + rowModifier;
        int newCol = getPlayerCol() + colModifier;
        
        if (gameEnded == true) throw new IllegalArgumentException("The game has ended. Please start a new game to continue.");
        else if (!inBounds(newRow, newCol)) throw new IllegalArgumentException("Out of bounds! Please choose a different direction.");
        else if (mines[newRow][newCol]) {
            gameEnded = true;
            throw new IllegalArgumentException("Oh no! You stepped on a mine. Game over :(");
        }
        else if (newRow == gridSize - 1 && newCol = gridSize - 1) {
            gameEnded = true;
            throw new IllegalArgumentException("You successfully reached the goal! You win :)");
        }
        else {
            playerRow = newRow;
            playerCol = newCol;
            uncovered[playerRow][playerCol] = true;
            changed(); // from Model, sets changed flag and fires changed event
        }
        
    }

}
