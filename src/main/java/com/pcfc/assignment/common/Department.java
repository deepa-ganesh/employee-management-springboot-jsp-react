package com.pcfc.assignment.common;

public enum Department {
    HR("Human Resources"),
    ENGINEERING("Engineering"),
    FINANCE("Finance"),
    MARKETING("Marketing");

    private String description;

    Department(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
