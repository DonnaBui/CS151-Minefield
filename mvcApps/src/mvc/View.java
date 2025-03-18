package mvc;

codespace-minesweeperCS151_updates
import javax.swing.JPanel;

public abstract class View extends JPanel {
    protected Model model;

    public View(Model model) {
        this.model = model;
    }

    public void setModel(Model model) {
        this.model = model;
        repaint();
    }
}