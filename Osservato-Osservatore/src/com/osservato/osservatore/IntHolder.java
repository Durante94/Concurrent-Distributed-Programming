package com.osservato.osservatore;

public class IntHolder {
    private int value;

    public IntHolder(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int incValue() {
        this.value += 1;
        return this.value;
    }
}
