package com.worldwar.bencoding;

public class BObject {
    private BType type;
    private Object value;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public BType getType() {
        return type;
    }

    public void setType(BType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
