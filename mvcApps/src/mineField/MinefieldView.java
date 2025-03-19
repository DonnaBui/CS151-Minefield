package mineField;

import mvc.*;

import java.awt.*;

import javax.swing.JButton;

public class MinefieldView extends View {
     
    public MinefieldView(Minefield mine) {
        super(mine);
        initView();
    }

    private void initView() {
        Minefield mine = (Minefield) model;
        // Initialize the view with the grid
        this.removeAll();
        this.setLayout(new GridLayout(mine.getSize(), mine.getSize())); // row/column size are the same
        for (int i = 0; i < mine.getSize(); i++) {
            for (int j = 0; j < mine.getSize(); j++) {
                JButton cellButton = new JButton();
                if (mine.uncoveredCells()[i][j]) { // If the cell has already been uncovered
                    cellButton.setBackground(Color.GREEN);
                } else {
                    cellButton.setBackground(Color.GRAY);
                }
                this.add(cellButton);
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
        int playerRow = mine.getPlayerRow();
        int playerCol = mine.getPlayerCol();
        g.setColor(Color.BLUE);
        int cellWidth = getWidth() / mine.getSize();
        int cellHeight = getHeight() / mine.getSize();
        g.fillRect(playerCol * cellWidth, playerRow * cellHeight, cellWidth, cellHeight);
    }
}