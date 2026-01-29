package com.example.application.entity;

public enum Role {
    ADMIN("Kwatermistrz"),
    USER("Drużynowy"),
    PRZYBOCZNY("Przyboczny");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

