package model;


import java.util.Date;
public class Task {

    private int id;
    private String name;
    private String description;
    private String category;
    private Date deadline;

    public Task(int id, String name, String description, String category, Date deadline) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "id=" + id + ", name='" + name + '\'' + ", description='" + description + '\''
                + ", category='" + category + '\'' + ", deadline=" + deadline;
    }
}
