import model.DatabaseManager;
import observer.TaskListModel;
import view.MainView;
import controller.TaskController;

import javax.swing.*;

public class Main {

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DatabaseManager databaseManager = new DatabaseManager();
                MainView mainView = new MainView();
                TaskListModel taskListModel = new TaskListModel();
                new TaskController(mainView, databaseManager, taskListModel);

                mainView.setVisible(true);
            }
        });
    }
}
