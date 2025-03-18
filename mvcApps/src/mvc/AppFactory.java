package mvc;

public interface AppFactory {
    Model makeModel();

    View makeView(Model model);

    String[] getEditCommands();

    Command makeEditCommand(Model model, String commandType, Object source);

    String getTitle();

    String[] getHelp();

    String about();
}