package com.example.demo.enumDef;

public enum ContactEnum {
    STT("0"), ID("1"), NAME("2"), AGE("3"), EMAIL("4"), ADDRESS("5");

    private String value;

    private ContactEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
