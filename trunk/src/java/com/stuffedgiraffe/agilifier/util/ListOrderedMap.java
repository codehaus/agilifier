package com.stuffedgiraffe.agilifier.util;

import java.util.*;

public class ListOrderedMap implements Map {
    private Map map = new HashMap();
    private List list = new ArrayList();

    public int size() {
        return list.size();
    }

    public void clear() {
        map.clear();
        list.clear();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    public Collection values() {
        List values = new ArrayList(list.size());
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            Object key = iterator.next();
            values.add(map.get(key));
        }
        return values;
    }

    public void putAll(Map t) {
        map.putAll(t);
        list.addAll(t.keySet());
    }

    public Set entrySet() {
        final List entryList = new ArrayList(list.size());
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            final Object key = iterator.next();
            final Object value = map.get(key);
            Map.Entry entry = new Entry() {
                public Object getKey() {
                    return key;
                }

                public Object getValue() {
                    return value;
                }

                public Object setValue(Object value) {
                    throw new UnsupportedOperationException();
                }
            };
            entryList.add(entry);
        }
        return makeOrderedSet(entryList);
    }

    private Set makeOrderedSet(final List entryList) {
        AbstractSet entrySet = new AbstractSet() {
            public int size() {
                return entryList.size();
            }

            public Iterator iterator() {
                return entryList.iterator();
            }
        };
        return Collections.unmodifiableSet(entrySet);
    }

    public Set keySet() {
        return makeOrderedSet(list);
    }

    public Object get(Object key) {
        return map.get(key);
    }

    public Object remove(Object key) {
        list.remove(key);
        return map.remove(key);
    }

    public Object put(Object key, Object value) {
        list.add(key);
        return map.put(key, value);
    }
}
