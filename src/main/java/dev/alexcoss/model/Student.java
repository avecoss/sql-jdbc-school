package dev.alexcoss.model;

import java.util.Objects;

public class Student {

    private final Integer defaultInteger = -1;

    private int id;
    private String firstName;
    private String lastName;
    private Integer groupId = defaultInteger;

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = Objects.requireNonNullElse(groupId, defaultInteger);
    }

    public Integer getDefaultInteger() {
        return defaultInteger;
    }

    @Override
    public String toString() {
        return String.format("\n%d %s %s groupId:%d", id, firstName, lastName, groupId);
    }
}
