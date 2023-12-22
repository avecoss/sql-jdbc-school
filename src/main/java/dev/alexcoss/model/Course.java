package dev.alexcoss.model;

public class Course {

    private int id;
    private String name;
    private String description = "default description";

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

    @Override
    public String toString() {
        return String.format("\n%d %s Description: %s", id, name, description);
    }
}
