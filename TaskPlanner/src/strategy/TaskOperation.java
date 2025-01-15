package strategy;

import model.DatabaseManager;
import model.Task;
public interface TaskOperation {

    void execute(DatabaseManager databaseManager, Task task);
}
