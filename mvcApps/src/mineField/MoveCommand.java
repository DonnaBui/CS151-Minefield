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
            // Pass the row/column modifiers as parameters to the move() function in Minefield.java
            mine.move(direction);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
