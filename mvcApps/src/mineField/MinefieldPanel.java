package mineField;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import mvc.*;

public class MinefieldPanel extends AppPanel  {
    private JPanel moveControls;
    public MinefieldPanel(AppFactory factory) {
        super(factory);
        moveControls = createControls();
        controlPanel.add(moveControls);
    }
    private JPanel createControls() {
        JPanel panel = new JPanel(new GridLayout(3,3));
        String[] dirSymbols = {"↖","↑", "↗", "←", "", "→", "↙", "↓", "↘"};
        String[] directions = {"NW", "N", "NE", "W", "", "E", "SW", "S", "SE"};
        for (int i = 0; i < 9; i++) {
            if (i == 4) { 
                // make a blank spot in the middle so the grid is a nice and even 3x3
                panel.add(new JLabel());
            } else {
                JButton button = new JButton(dirSymbols[i]);
                button.setActionCommand(directions[i]);
                button.addActionListener(this);
                panel.add(button);
            }
        }
        return panel;
    }

    @Override
    protected JMenuBar createMenuBar() {
        JMenuBar result = new JMenuBar();
        // add file, edit, and help menus
        JMenu fileMenu =
                Utilities.makeMenu("File", new String[] {"New Game", "Quit"}, this);
        result.add(fileMenu);

        JMenu editMenu =
                Utilities.makeMenu("Edit", factory.getEditCommands(), this);
        result.add(editMenu);

        JMenu helpMenu =
                Utilities.makeMenu("Help", new String[] {"About", "Help"}, this);
        result.add(helpMenu);

        return result;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            String cmmd = ae.getActionCommand();

            if (cmmd.equals("New Game")) {
                setModel(factory.makeModel());
                model.setUnsavedChanges(false);
            } else if (cmmd.equals("Quit")) {
                System.exit(0);
            } else if (cmmd.equals("About")) {
                Utilities.inform(factory.about());
            } else if (cmmd.equals("Help")) {
                Utilities.inform(factory.getHelp());
            } else {
                Command command = factory.makeEditCommand(model, cmmd, this);
                if (command != null) command.execute();
            }
        } catch (Exception e) {
            handleException(e);
        }
    }

    public static void main(String[] args) {
        AppFactory factory = new MinefieldFactory();
        MinefieldPanel panel = new MinefieldPanel(factory);
        panel.display(); 
    }
}