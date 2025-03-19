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

    public static void main(String[] args) {
        AppFactory factory = new MinefieldFactory();
        MinefieldPanel panel = new MinefieldPanel(factory);
        panel.display(); 
    }



/* added set board - adam
    public void setBoard(){
        for (int i = 0; i < 10;i++){
            for (int j = 0; i < 10; i++){
                board[i][j] = new Cell();
            }
        }
        MineSet();
        nearbyMineSet();
    }

    private void winGame(){
        JOptionPane.showMessageDialog(this, "Congratulations, You win!!!");
        System.exit(0);
    }

    private void revealCell(int i, int j){
        if(mines[i][j]){
            loseGame();
        }else{
            buttons[i][j].setText(toString(surroundingMines[i][j]));
            buttons[][].setenabled(false);
            uncoverCell++;
            if (uncoveredCells == 90) {
                winGame();
            }
            if (surroundingCells[i][j] == 0) {
                uncoverSurroundingCells(i,j);
            }
        }


    }

      private void uncoverSurroundingCells(int i, int j) {
    if (i > 0 && buttons[i - 1][j].isEnabled()) uncoverCell(i - 1, j);
    if (i < 9 && buttons[i + 1][j].isEnabled()) uncoverCell(i + 1, j);
    if (j > 0 && buttons[i][j - 1].isEnabled()) uncoverCell(i, j - 1);
    if (j < 9 && buttons[i][j + 1].isEnabled()) uncoverCell(i, j + 1);
    if (i > 0 && j > 0 && buttons[i - 1][j - 1].isEnabled()) uncoverCell(
      i - 1,
      j - 1
    );
    if (i < 9 && j < 9 && buttons[i + 1][j + 1].isEnabled()) uncoverCell(
      i + 1,
      j + 1
    );
    if (i > 0 && j < 9 && buttons[i - 1][j + 1].isEnabled()) uncoverCell(
      i - 1,
      j + 1
    );
    if (i < 9 && j > 0 && buttons[i + 1][j - 1].isEnabled()) uncoverCell(
      i + 1,
      j - 1
    );
  }

    private void loseGame(){
        for (int i = 0;  i < 10; i++){
            for (int j = 0; j < 10; j++){
                if (mines[i][j]){
                    buttons[i][j].setText("*")
                }
                buttons[i][j].setEnabled(false);
            }

        }
        JOptionPane.showMessageDialog(this, "Sorry, You lose...");
        System.exit(0);
    }

    private void setAdjacentMines(){
        for(){
            for(){}
        }

    }

    private class CellClickListener implements ActionListener {
        private int i;
        private int j;

    public CellClickListener(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public void actionPerformed(ActionEvent e) {
        uncoverCell(i, j);
        }
    }

*/




}