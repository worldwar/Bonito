package com.worldwar.bencoding;

import java.util.Map;

public class BObjects {
    public static BObject dictionary(Map map) {
        BObject object = new BObject();
        object.setType(BType.DICTIONARY);
        object.setValue(map);
        return object;
    }
}
