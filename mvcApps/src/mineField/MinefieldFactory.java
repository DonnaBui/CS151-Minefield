package mineField;

import mvc.*;

public class MinefieldFactory implements AppFactory {

    public Model makeModel() { return new Minefield(); }

    public View makeView(Model m) {
        return new MinefieldView((Minefield)m);
    }

    public String[] getEditCommands() { return new String[] {"NW", "N", "NE", "W", "E", "SW", "S", "SE"}; }

    public Command makeEditCommand(Model model, String type, Object source) {
        Minefield mine = (Minefield) model;
        switch (type) {
            case "NW":
                return new MoveCommand(mine, Direction.NW);
            case "N":
                return new MoveCommand(mine, Direction.N);
            case "NE":
                return new MoveCommand(mine, Direction.NE);
            case "W":
                return new MoveCommand(mine, Direction.W);
            case "E":
                return new MoveCommand(mine, Direction.E);
            case "SW":
                return new MoveCommand(mine, Direction.SW);
            case "S":
                return new MoveCommand(mine, Direction.S);
            case "SE":
                return new MoveCommand(mine, Direction.SE);
            default:
                return null; // Invalid command
        }
    }

    public String getTitle() { return "Minefield Game"; }

    public String[] getHelp() {
        return new String[] {
            "Click on the directional buttons to traverse the field." + 
            "Get to the green box without stepping on any mines!"
        };
    }

    public String about() {
        return "Minefield version 1.0. Copyright 2025 by CS151 Minefield Group 1.";
    }

}