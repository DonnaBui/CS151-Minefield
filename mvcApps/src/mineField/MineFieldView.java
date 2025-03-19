package mineField;

import javax.swing.*;

import mvc.Model;

import java.awt.*;

public class MineFieldView extends JPanel {
    private MineFieldModel model;

    public void setModel(MineFieldModel newModel) {
        this.model = newModel;
        initView();
        repaint();
    }

    private void initView() {
        // Initialize the view with the grid
        this.removeAll();
        this.setLayout(new GridLayout(model.getGrid().length, model.getGrid()[0].length));
        for (int i = 0; i < model.getGrid().length; i++) {
            for (int j = 0; j < model.getGrid()[0].length; j++) {
                JButton cellButton = new JButton();
                if (model.getGrid()[i][j].isMined()) {
                    cellButton.setBackground(Color.RED);
                } else {
                    cellButton.setBackground(Color.GREEN);
                }
                this.add(cellButton);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the player's position
        int playerRow = model.getPlayerRow();
        int playerCol = model.getPlayerCol();
        g.setColor(Color.BLUE);
               // Draw the player's position
        int cellWidth = getWidth() / model.getGrid()[0].length;
        int cellHeight = getHeight() / model.getGrid().length;
        g.fillRect(playerCol * cellWidth, playerRow * cellHeight, cellWidth, cellHeight);
    }


    public void setModel(Model model) {
        this.model = model;
        repaint();
    }
}