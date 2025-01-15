package strategy;

import model.DatabaseManager;
import model.Task;

public class DeleteTaskStrategy implements TaskOperation {
    @Override
    public void execute(DatabaseManager databaseManager, Task task) {
        databaseManager.deleteTask(task.getId());
    }

}
