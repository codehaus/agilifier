package com.stuffedgiraffe.agilifier.util;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder {
    public static Map<String, Object> make(String key, Object value) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(key, value);
        return map;
    }
}
