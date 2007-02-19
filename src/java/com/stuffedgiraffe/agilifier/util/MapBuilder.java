package com.stuffedgiraffe.agilifier.util;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder {
    public static Map make(String key, Object value) {
        Map map = new HashMap();
        map.put(key, value);
        return map;
    }
}
