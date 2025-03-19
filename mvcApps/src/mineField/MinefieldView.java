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

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                JButton cell = new JButton();
                cell.setEnabled(false);
                cell.setBackground(Color.GRAY); // Set the cell colors to gray
                cells[i][j] = cell;
                this.add(cell);
            }
        }
        // Set the color of the goal to bright green
        cells[gridSize - 1][gridSize - 1].setBackground(Color.GREEN);
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

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (mine.uncoveredCells()[i][j]) { // If the cell has already been uncovered
                    cells[i][j].setBackground(new Color(0, 100, 0));
                } else {
                    cells[i][j].setBackground(Color.GRAY);
                }
            }
        }
        // Visually update player location
        cells[playerRow][playerCol].setBackground(Color.BLUE);
        // Set color of the goal cell
        cells[gridSize - 1][gridSize - 1].setBackground(Color.GREEN);
    }

}