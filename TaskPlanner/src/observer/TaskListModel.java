package observer;

import java.util.ArrayList;
import java.util.List;
public class TaskListModel {
    private final List<String> tasks = new ArrayList<>();
    private final List<TaskListObserver> observers = new ArrayList<>();

    public void addTask(String task) {
        tasks.add(task);
        notifyObservers();
    }

    public void removeTask(String task) {
        tasks.remove(task);
        notifyObservers();
    }

    public void clear() {
        tasks.clear();
        notifyObservers();
    }

    public void addObserver(TaskListObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (TaskListObserver observer : observers) {
            observer.update(tasks);
        }
    }
}
