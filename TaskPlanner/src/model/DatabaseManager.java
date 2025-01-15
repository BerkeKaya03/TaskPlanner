package model;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class DatabaseManager {

    private static Connection connection;

    public DatabaseManager () {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/task_planner?user=root&password=BerkeKaya03");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM tasks";

        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Task task = new Task(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("category"),
                        resultSet.getDate("deadline")
                );
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public void addTask(Task task) {
        String query = "INSERT INTO tasks (name, description, category, deadline) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setString(3, task.getCategory());
            preparedStatement.setDate(4, new java.sql.Date(task.getDeadline().getTime()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(int taskId) {
        String query = "DELETE FROM tasks WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, taskId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTask(Task task) {
        String query = "UPDATE tasks SET name = ?, description = ?, category = ?, deadline = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setString(3, task.getCategory());
            preparedStatement.setDate(4, new java.sql.Date(task.getDeadline().getTime()));
            preparedStatement.setInt(5, task.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Task> getTasksWithDeadlineApproaching(String currentDate) {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM tasks WHERE deadline >= ? AND deadline <= ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, currentDate);
            stmt.setString(2, getNextDay(currentDate));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Task task = new Task(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getString("category"),
                            rs.getDate("deadline")
                    );
                    tasks.add(task);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    private static String getNextDay(String currentDate) {
        LocalDate date = LocalDate.parse(currentDate);
        LocalDate nextDay = date.plusDays(1);
        return nextDay.toString();
    }


    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
