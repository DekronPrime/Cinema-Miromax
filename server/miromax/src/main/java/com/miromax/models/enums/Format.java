package com.miromax.models.enums;

public enum Format {
    TWO_DIMENSIONAL("2D"),
    THREE_DIMENSIONAL("3D");
    private String label;

    Format(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}