package com.worldwar.ui;

import javafx.beans.property.*;

import java.time.LocalDateTime;

public class Torrenta {
    private StringProperty name;
    private StringProperty serialNo;
    private LongProperty size;
    private FloatProperty done;
    private IntegerProperty status;
    private ObjectProperty<LocalDateTime> addedOn;

    public Torrenta() {
        name = new SimpleStringProperty();
        serialNo = new SimpleStringProperty();
        size = new SimpleLongProperty();
        done = new SimpleFloatProperty();
        status = new SimpleIntegerProperty();
        addedOn = new SimpleObjectProperty<>();
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSerialNo() {
        return serialNo.get();
    }

    public StringProperty serialNoProperty() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo.set(serialNo);
    }

    public long getSize() {
        return size.get();
    }

    public LongProperty sizeProperty() {
        return size;
    }

    public void setSize(long size) {
        this.size.set(size);
    }

    public float getDone() {
        return done.get();
    }

    public FloatProperty doneProperty() {
        return done;
    }

    public void setDone(float done) {
        this.done.set(done);
    }

    public int getStatus() {
        return status.get();
    }

    public IntegerProperty statusProperty() {
        return status;
    }

    public void setStatus(int status) {
        this.status.set(status);
    }

    public LocalDateTime getAddedOn() {
        return addedOn.get();
    }

    public ObjectProperty<LocalDateTime> addedOnProperty() {
        return addedOn;
    }

    public void setAddedOn(LocalDateTime addedOn) {
        this.addedOn.set(addedOn);
    }
}
