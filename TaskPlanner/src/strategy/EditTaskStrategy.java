package strategy;

import model.DatabaseManager;
import model.Task;

public class EditTaskStrategy implements TaskOperation {
    @Override
    public void execute(DatabaseManager databaseManager, Task task) {
        databaseManager.updateTask(task);
    }
}
