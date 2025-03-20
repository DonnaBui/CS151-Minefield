package mineField;

import java.awt.*;
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
        String[] directions = {"↖","↑", "↗", "←", "", "→", "↙", "↓", "↘"};
        for (int i = 0; i < 9; i++) {
            if (i == 4) { 
                // make a blank spot in the middle so the grid is a nice and even 3x3
                panel.add(new JLabel());
            } else {
                JButton button = new JButton(directions[i]);
                button.setActionCommand(directions[i]);
                button.addActionListener(this);
                panel.add(button);
            }
        }
        return panel;
    }

    public static void main(String[] args) {
        AppFactory factory = new MinefieldFactory();
        MinefieldPanel panel = new MinefieldPanel(factory);
        panel.display(); 
    }
}