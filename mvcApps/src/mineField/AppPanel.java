package mineField;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import mvc.Subscriber;

public class AppPanel extends JPanel implements Subscriber, ActionListener  {

    protected Model model;
    protected AppFactory factory;
    protected View view;
    protected JPanel controlPanel;
    private JFrame frame;

    public static int FRAME_WIDTH = 500;
    public static int FRAME_HEIGHT = 300;

    public AppPanel(AppFactory factory) {
        this.factory = factory;
        this.model = factory.makeModel();
        this.view = factory.makeView(model);

        model.subscribe(this);

        setLayout(new BorderLayout());
        controlPanel = new JPanel();
        add(controlPanel, BorderLayout.SOUTH);
        add(view, BorderLayout.CENTER);

        frame = new SafeFrame();
        Container cp = frame.getContentPane();
        cp.add(this);
        frame.setJMenuBar(createMenuBar());
        frame.setTitle(factory.getTitle());
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

    }
//added set board - adam
    public void setBoard(){
        for (int i = 0; i < 10;i++){
            for (int j = 0; i < 10; i++){
                board[i][j] = new Cell();
            }
        }
        MineSet();
        nearbyMineSet();
    }
//addded MineSet adam
    private void MineSet(){
        Random rand = new Random();
        int placedMines = 0;
        while (placed < 10) {
            int i = rand.nextInt(10);
            int j = rand.nextInt(10);
            if (!mines[i][j]){
                mines[i][j] = true;
                placedMines++;
            }
        }
    }

    private void winGame(){
        JOptionPane.showMessageDialog(this, "Congratulations, You win!!!");
        System.exit(0);
    }

    private void revealCell(int i, int j){
        if(mines[i][j]){
            loseGame();
        }else{
            buttons[i][j].setText(toString.toString(surroundingMines[i][j]));
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

    public void display() {
        frame.setVisible(true);
    }

    @Override
    public void update() {
        repaint();
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model newModel) {
        this.model.unsubscribe(this);
        this.model = newModel;
        this.model.subscribe(this);
        view.setModel(this.model);
        model.changed();
    }

    protected JMenuBar createMenuBar() {
        JMenuBar result = new JMenuBar();
        JMenu fileMenu =
                Utilities.makeMenu("File", new String[] {"New", "Save", "SaveAs", "Open", "Quit"}, this);
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

            if (cmmd.equals("Save")) {
                Utilities.save(model, false);
            } else if (cmmd.equals("SaveAs")) {
                Utilities.save(model, true);
            } else if (cmmd.equals("Open")) {
                Model newModel = Utilities.open(model);
                if (newModel != null) setModel(newModel);
            } else if (cmmd.equals("New")) {
                Utilities.saveChanges(model);
                setModel(factory.makeModel());
                model.setUnsavedChanges(false);
            } else if (cmmd.equals("Quit")) {
                Utilities.saveChanges(model);
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

    protected void handleException(Exception e) {
        Utilities.error(e);
    }
}