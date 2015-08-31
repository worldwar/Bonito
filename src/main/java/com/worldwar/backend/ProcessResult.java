package com.worldwar.backend;

public class ProcessResult {
    public static final ProcessResult DROP_CONNECTION = new ProcessResult(ProcessResultType.DROP_CONNECTION, null);
    public static final ProcessResult IGNORE = new ProcessResult(ProcessResultType.IGNORE, null);

    private ProcessResultType type;
    private byte[] value;

    public ProcessResult(ProcessResultType type, byte[] value) {
        this.type = type;
        this.value = value;
    }

    public ProcessResultType getType() {
        return type;
    }

    public void setType(ProcessResultType type) {
        this.type = type;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }
}
