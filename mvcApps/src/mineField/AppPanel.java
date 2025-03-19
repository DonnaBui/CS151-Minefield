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

    JButton north = new JButton("North");
    JButton south = new JButton("South");
    JButton west = new JButton("West");
    JButton east = new JButton("East");
    JButton southwest = new JButton("southwest");
    JButton southeast = new JButton("southeast");
    JButton northeast = new JButton("northeast");
    JButton northwest = new JButton("northwest");

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

    private void ButtonLayout(JPanel p, JPanel controls){
       
        p.add(north);
        p.add(south);
        p.add(west);
        p.add(east);
        p.add(clear);
        p.add(pen);
        p.add(colour);
        controls.add(p);
        controls.setLayout((new GridLayout(2,3)));
    }
    
    public void setListeners() {
        north.addActionListener(this::actionPerformed);
        south.addActionListener(this::actionPerformed);
        west.addActionListener(this::actionPerformed);
        east.addActionListener(this::actionPerformed);
        clear.addActionListener(this::actionPerformed);
        pen.addActionListener(this::actionPerformed);
        colour.addActionListener(this::actionPerformed);
    }

//added set board - adam
    public void setBoard(){
        for (int i = 0; i < 10;i++){
            for (int j = 0; i < 10; i++){
                board[i][j] = new Cell();
            }
        }
        MineSet();
        setAdjacentMines();
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
            buttons[i][j].setenabled(false);
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
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0) continue; // Skip the cell itself
                int ni = i + x;
                int nj = j + y;
                if (ni >= 0 && ni < SIZE && nj >= 0 && nj < SIZE && buttons[ni][nj].isEnabled()) {
                    uncoverCell(ni, nj);
                }
            }
        }
    }

    private void loseGame(){
        for (int i = 0;  i < 10; i++){
            for (int j = 0; j < 10; j++){
                if (mines[i][j]){
                    buttons[i][j].setText("*");
                }
                buttons[i][j].setEnabled(false);
            }

        }
        JOptionPane.showMessageDialog(this, "Sorry, You lose...");
        System.exit(0);
    }

    private void setAdjacentMines() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (mines[i][j]) {
                    continue; // Skip mines
                }
                int count = 0;
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        if (x == 0 && y == 0) continue; // Skip the cell itself
                        int ni = i + x;
                        int nj = j + y;
                        if (ni >= 0 && ni < SIZE && nj >= 0 && nj < SIZE && mines[ni][nj]) {
                            count++;
                        }
                    }
                }
                surroundingMines[i][j] = count; // Set the count of surrounding mines
            }
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