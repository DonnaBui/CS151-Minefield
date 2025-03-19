package mineField;

import java.util.ArrayList;
import java.util.List;

public class MineFieldModel {
    private static final long serialVersionUID = 1L;

    private List<Subscriber> subscribers = new ArrayList<>();
    private boolean unsavedChanges = false;
    private String fileName = null;


    public int getPlayerCol(){}
    
    public int getPlayerRow(){}

    @Override
    public void subscribe(Subscriber s) {
        if (!subscribers.contains(s)) {
            subscribers.add(s);
        }
    }

    @Override
    public void unsubscribe(Subscriber s) {
        subscribers.remove(s);
    }

    @Override
    public void notifySubscribers() {
        for (Subscriber s : subscribers) {
            s.update();
        }
    }

    @Override
    public void changed() {
        unsavedChanges = true;
        notifySubscribers();
    }

    @Override
    public void setUnsavedChanges(boolean flag) {
        unsavedChanges = flag;
    }

    @Override
    public boolean getUnsavedChanges() {
        return unsavedChanges;
    }

    public void setFileName(String fn) {
        fileName = fn;
    }

    public String getFileName() {
        return fileName;
    }
}