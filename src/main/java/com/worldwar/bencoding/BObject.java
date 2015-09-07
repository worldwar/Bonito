package com.worldwar.bencoding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BObject implements Comparable<BObject> {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BObject bObject = (BObject) o;

        if (type != bObject.type) return false;
        if (value != null ? !value.equals(bObject.value) : bObject.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    public Object normalize() {
        switch (type) {
            case STRING: return value;
            case INTEGER: return value;
            case LIST: return list();
            case DICTIONARY: return dictionary();
        }
        return null;
    }

    private Object list() {
        List<Object> result = new ArrayList<>();
        for (BObject element : (List<BObject>) value) {
            result.add(element.normalize());
        }
        return result;
    }

    private Object dictionary() {
        Map<Object, Object> result = new HashMap<>();
        for (Map.Entry<BObject, BObject> element : ((Map<BObject, BObject>)value).entrySet()) {
            result.put(element.getKey().normalize(), element.getValue().normalize());
        }
        return result;
    }

    @Override
    public int compareTo(BObject o) {
        switch (type) {
            case INTEGER: return ((Integer)this.value) - (Integer)o.value;
            case STRING: return ((String)this.value).compareTo(o.value.toString());
        }
        return 0;
    }
}
