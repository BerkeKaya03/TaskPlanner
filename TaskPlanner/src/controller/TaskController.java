package controller;

import model.DatabaseManager;
import model.Task;
import observer.TaskListModel;
import strategy.*;
import view.MainView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import com.toedter.calendar.JDateChooser;


public class TaskController {
    private  final MainView view;
    private final DatabaseManager databaseManager;
    private final TaskListModel taskListModel;

    JDateChooser dateChooser = new JDateChooser();


    public TaskController(MainView view, DatabaseManager databaseManager, TaskListModel taskListModel) {
        this.view = view;
        this.databaseManager = databaseManager;
        this.taskListModel = taskListModel;

        taskListModel.addObserver(view);
        loadTasks();

        view.getAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(5, 2));

                JTextField nameField = new JTextField();
                JTextField descriptionField = new JTextField();
                JTextField categoryField = new JTextField();
                dateChooser.setDateFormatString("dd-MM-yyyy");

                panel.add(new JLabel("Task Name:"));
                panel.add(nameField);
                panel.add(new JLabel("Task Description:"));
                panel.add(descriptionField);
                panel.add(new JLabel("Task Category:"));
                panel.add(categoryField);
                panel.add(new JLabel("Task Deadline:"));
                panel.add(dateChooser);

                boolean validInput = false;

                while (!validInput) {
                    int option = JOptionPane.showConfirmDialog(view, panel, "Enter Task Details", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (option == JOptionPane.OK_OPTION) {
                        String name = nameField.getText();
                        String description = descriptionField.getText();
                        String category = categoryField.getText();
                        Date deadline = dateChooser.getDate();

                        try {
                            if (name.isEmpty()) {
                                throw new Exception("Task Name is required.");
                            }
                            if (description.isEmpty()) {
                                throw new Exception("Task Description is required.");
                            }
                            if (category.isEmpty()) {
                                throw new Exception("Task Category is required.");
                            }
                            if (deadline == null) {
                                throw new Exception("Task Deadline is required.");
                            }
                            Task task = new Task(0, name, description, category, deadline);
                            new AddTaskStrategy().execute(databaseManager, task);
                            loadTasks();
                            validInput = true;
                            JOptionPane.showMessageDialog(view, "Task added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                            break;
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(view, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        validInput = true;
                    }
                }
            }
        });

        view.getDeleteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = view.getTaskList().getSelectedIndex();
                if (selectedIndex != -1) {
                    String taskName = view.getTaskListModel().getElementAt(selectedIndex);
                    List<Task> tasks = databaseManager.getAllTasks();
                    for (Task task : tasks) {
                        if (task.toString().equals(taskName)) {

                            int option = JOptionPane.showConfirmDialog(view,
                                    "Are you sure you want to delete this task?",
                                    "Confirm Deletion",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.WARNING_MESSAGE);

                            if (option == JOptionPane.YES_OPTION) {
                                new DeleteTaskStrategy().execute(databaseManager, task);
                                loadTasks();
                                JOptionPane.showMessageDialog(view, "Task deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                            }
                            break;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "No task selected.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        view.getEditButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = view.getTaskList().getSelectedIndex();
                if (selectedIndex != -1) {
                    String taskName = view.getTaskListModel().getElementAt(selectedIndex);
                    List<Task> tasks = databaseManager.getAllTasks();
                    for (Task task : tasks) {
                        if (task.toString().equals(taskName)) {
                            JPanel panel = new JPanel();
                            panel.setLayout(new GridLayout(5,2));

                            JTextField nameField = new JTextField(task.getName());
                            JTextField descriptionField = new JTextField(task.getDescription());
                            JTextField categoryField = new JTextField(task.getCategory());
                            dateChooser.setDateFormatString("dd-MM-yyyy");
                            dateChooser.setDate(task.getDeadline());

                            panel.add(new JLabel("New Task Name:"));
                            panel.add(nameField);
                            panel.add(new JLabel("New Task Description:"));
                            panel.add(descriptionField);
                            panel.add(new JLabel("New Task Category:"));
                            panel.add(categoryField);
                            panel.add(new JLabel("New Task Deadline:"));
                            panel.add(dateChooser);

                            boolean validInput = false;

                            while (!validInput) {

                                int option = JOptionPane.showConfirmDialog(view, panel, "Edit Task Details", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                                if (option == JOptionPane.OK_OPTION) {
                                    String newName = nameField.getText();
                                    String newDescription = descriptionField.getText();
                                    String newCategory = categoryField.getText();
                                    Date newDeadLine = dateChooser.getDate();

                                    try {
                                        boolean isModified = !newName.equals(task.getName()) && !newName.isEmpty() ||
                                                !newDescription.equals(task.getDescription()) && !newDescription.isEmpty() ||
                                                !newCategory.equals(task.getCategory()) && !newCategory.isEmpty() ||
                                                (newDeadLine != null && !newDeadLine.equals(task.getDeadline()));

                                        if (!isModified) {
                                            throw new Exception("At least one field must be changed.");
                                        }
                                        task.setName(newName);
                                        task.setDescription(newDescription);
                                        task.setCategory(newCategory);
                                        task.setDeadline(newDeadLine);
                                        new EditTaskStrategy().execute(databaseManager, task);
                                        loadTasks();
                                        validInput = true;
                                        JOptionPane.showMessageDialog(view, "Task updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                                        break;
                                    } catch (Exception ex) {
                                        JOptionPane.showMessageDialog(view, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                } else {
                                    validInput = true;
                                }
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "No Task Selected.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void loadTasks() {
        taskListModel.clear();
        List<Task> tasks = databaseManager.getAllTasks();
        for (Task task : tasks) {
            taskListModel.addTask(task.toString());
        }
    }
}
