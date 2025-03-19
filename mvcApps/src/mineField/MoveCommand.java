package mineField;

import javax.swing.JOptionPane;

import mvc.*;

public class MoveCommand extends Command{
    Direction direction;

    public MoveCommand(Model model, Direction direction) {
        super(model);
        this.direction = direction;
    }

    public void execute() {
        Minefield mine = (Minefield)model;
        try {
            int moveX = mine.getPlayerRow() + direction.getRowDir();
            int moveY = mine.getPlayerCol() + direction.getColDir();
            mine.move(moveX, moveY);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
