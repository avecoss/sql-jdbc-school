package dev.alexcoss.config;

public enum DatabaseConfig {
    URL, USERNAME, PASSWORD;

    private String value;

    public String getValue() {
        if (value == null) {
            DatabaseConfigInitializer.initialize(this);
        }
        return value;
    }

    void setValue(String value) {
        this.value = value;
    }
}
