package mvc;

public interface Publisher {
    void subscribe(Subscriber s);
    void unsubscribe(Subscriber s);
    void notifySubscribers();
    void changed();
    void setUnsavedChanges(boolean flag);
    boolean getUnsavedChanges();
}