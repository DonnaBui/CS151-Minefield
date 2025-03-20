package mineField;

import java.io.Serializable;

import mvc.*;

public class MinefieldFactory implements AppFactory, Serializable {

    public Model makeModel() { 
        Minefield game = new Minefield();
        game.setFileName("minefield_game"); // Default file name if player clicks Save
        return game; 
    }

    public View makeView(Model m) {
        return new MinefieldView((Minefield)m);
    }

    public String[] getEditCommands() { 
        return new String[] {"↖", "↑", "↗", "←", "→", "↙", "↓", "↘"}; 
    }

    public Command makeEditCommand(Model model, String type, Object source) {
        Minefield mine = (Minefield) model;
        switch (type) {
            case "↖":
                return new MoveCommand(mine, Direction.NW);
            case "↑":
                return new MoveCommand(mine, Direction.N);
            case "↗":
                return new MoveCommand(mine, Direction.NE);
            case "←":
                return new MoveCommand(mine, Direction.W);
            case "→":
                return new MoveCommand(mine, Direction.E);
            case "↙":
                return new MoveCommand(mine, Direction.SW);
            case "↓":
                return new MoveCommand(mine, Direction.S);
            case "↘":
                return new MoveCommand(mine, Direction.SE);
            default:
                return null; // Nonexistent command
        }
    }

    public String getTitle() { return "Minefield Game"; }

    public String[] getHelp() {
        return new String[] {
            "Click on the directional buttons to traverse the field.\n" + 
            "Get to the green box without stepping on any mines!"
        };
    }

    public String about() {
        return "Minefield version 1.0. Copyright 2025 by CS151 Minefield Group 1.";
    }

}