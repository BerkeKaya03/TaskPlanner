package view;

import model.Task;
import model.DatabaseManager;
import observer.TaskListObserver;
import decorator.Message;
import decorator.BasicMessage;
import decorator.BirthdayDecorator;
import decorator.NotificationDecorator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainView extends JFrame implements TaskListObserver {
    private final JList<String> taskList;
    private final DefaultListModel<String> taskListModel;
    private final JButton addButton;
    private final JButton deleteButton;
    private final JButton editButton;

    public MainView() {
        setTitle("Task Planner");
        setSize(600, 400);
        setLocation(400,250);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());

        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add Task");
        deleteButton = new JButton("Delete Task");
        editButton = new JButton("Edit Task");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);

        add(buttonPanel, BorderLayout.NORTH);
        add(new JScrollPane(taskList), BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(MainView.this, "Are you sure you want to quit?",
                         "Exit Confirmation", JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        displayDecoratedMessage();
    }

    public JList<String> getTaskList() {
        return taskList;
    }

    public DefaultListModel<String> getTaskListModel() {
        return taskListModel;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    @Override
    public void update(List<String> tasks) {
        taskListModel.clear();
        for (String task : tasks) {
            taskListModel.addElement(task);
        }
    }

    private void displayDecoratedMessage() {
        String today = new SimpleDateFormat("EEEE").format(new Date());
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        List<Task> tasks = DatabaseManager.getTasksWithDeadlineApproaching(date);


        if (!tasks.isEmpty()) {
            Message basicMessage = new BasicMessage(today, date);

            Message birthdayMessage = new BirthdayDecorator(basicMessage, "today");

            String taskNames = "";
            for (Task task : tasks) {
                taskNames += task.getName() + ", ";
            }
            if (!taskNames.isEmpty()) {
                taskNames = taskNames.substring(0, taskNames.length() - 2);
            }

            Message notificationMessage = new NotificationDecorator(birthdayMessage, "Task deadline approaching! Tasks: " + taskNames);

            JOptionPane.showMessageDialog(this, notificationMessage.getMessage(), "Notification", JOptionPane.INFORMATION_MESSAGE);
        } else {
            Message basicMessage = new BasicMessage(today, date);
            Message birthdayMessage = new BirthdayDecorator(basicMessage, "today");
            Message notificationMessage = new NotificationDecorator(birthdayMessage, "No tasks with approaching deadlines.");
            JOptionPane.showMessageDialog(this, notificationMessage.getMessage(), "Notification", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
