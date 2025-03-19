package mineField;

import mvc.*;

import java.awt.*;

import javax.swing.JButton;

public class MinefieldView extends View {
    private JButton[][] cells;

    public MinefieldView(Minefield mine) {
        super(mine);
        initView();
    }

    private void initView() {
        Minefield mine = (Minefield) model;
        int gridSize = mine.getSize();
        // Initialize the view with the grid
        this.removeAll();
        this.setLayout(new GridLayout(gridSize, gridSize)); // row/column size are the same
        
        cells = new JButton[gridSize][gridSize];
        // Populate grid with buttons
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                JButton cell = new JButton();
                cell.setEnabled(false);
                cells[i][j] = cell;
                this.add(cell);
            }
        }
    }

    @Override
    public void setModel(Model newModel) {
        super.setModel(newModel);
        initView();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) { 
        super.paintComponent(g);
        
        // Draw the player's position
        Minefield mine = (Minefield) model;
        int gridSize = mine.getSize();
        int playerRow = mine.getPlayerRow();
        int playerCol = mine.getPlayerCol();

        // Handles all coloring and visual effects
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (mine.isUncovered(i,j)) { // If the cell has already been uncovered
                    cells[i][j].setBackground(new Color(0, 100, 0)); // Set color to dark green
                    cells[i][j].setText(String.valueOf(mine.getAdjMines(i,j))); // Display # of adjacent mines
                } else {
                    cells[i][j].setBackground(Color.GRAY);
                }
                if (mine.gameEnded() && mine.isMine(i,j)) { // Reveal all mines if game has ended
                    cells[i][j].setBackground(Color.RED);
                    cells[i][j].setText("💣");
                }
            }
        }
        // Visually update player location
        cells[playerRow][playerCol].setBackground(Color.BLUE);
        
        // Set color of the goal cell
        cells[mine.goalPos()[0]][mine.goalPos()[1]].setBackground(Color.GREEN);
    }

}