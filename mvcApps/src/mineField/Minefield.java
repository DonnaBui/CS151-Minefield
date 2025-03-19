package mineField;
import java.util.Random;

import mvc.*;

public class Minefield extends Model {
    private int playerRow = 0, playerCol = 0;
    private int gridSize = 10; // By default, it's 10x10, but the player can change it if they want
    private boolean[][] mines, uncovered;
    private boolean gameEnded = false;
    
    public int getPlayerRow() {
        return playerRow;
    }
    public int getPlayerCol() {
        return playerCol;
    }
    public int getSize() {
        return gridSize;
    }

    public Minefield() {
        mines = new boolean[gridSize][gridSize];
        uncovered = new boolean[gridSize][gridSize]; 
        setMines();
    }

    public void changeSize() {} // tbd

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

    public int nearbyMines(int row, int col) {
        int nearbyMines = 0;
        for (int x = -1; x <= 1; x++) { 
            for (int y = -1; y<= 1; y++) {
                int r = row + x;
                int c = col + y;
                // Only check the adjacent squares that are in range of the grid
                if (r >= 0 && r < gridSize && c >= 0 && c < gridSize) {
                    if (mines[r][c]) {
                        nearbyMines++;
                    }
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
        changed(); // from Model, sets changed flag and fires changed event
    }

}
