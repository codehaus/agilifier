package com.stuffedgiraffe.agilifier.util;

import java.util.*;

public class ListOrderedMap<K, V> implements Map<K, V> {
    private Map<K, V> map = new HashMap<K, V>();
    private List<K> list = new ArrayList<K>();

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

    public Collection<V> values() {
        List<V> values = new ArrayList<V>(list.size());
        for (K key : list) {
            values.add(map.get(key));
        }
        return values;
    }

    public void putAll(Map<? extends K, ? extends V> t) {
        map.putAll(t);
        list.addAll(t.keySet());
    }

    public Set<Entry<K, V>> entrySet() {
        final List<Map.Entry<K, V>> entryList = new ArrayList<Map.Entry<K, V>>(list.size());
        for (final K key : list) {
            final V value = map.get(key);
            Entry<K, V> entry = new Entry<K, V>() {
                public K getKey() {
                    return key;
                }

                public V getValue() {
                    return value;
                }

                public V setValue(Object value) {
                    throw new UnsupportedOperationException();
                }
            };
            entryList.add(entry);
        }
        return new ListOrderedSet<Entry<K, V>>(entryList).wrapAsUnmodifiable();
    }

    public Set<K> keySet() {
        return new ListOrderedSet<K>(list).wrapAsUnmodifiable();
    }

    public V get(Object key) {
        return map.get(key);
    }

    public V remove(Object key) {
        list.remove(key);
        return map.remove(key);
    }

    public V put(K key, V value) {
        list.add(key);
        return map.put(key, value);
    }


    private static class ListOrderedSet<T> extends AbstractSet<T> {
        private final List<T> entryList;

        public ListOrderedSet(List<T> entryList) {
            this.entryList = entryList;
        }

        public Iterator<T> iterator() {
            return entryList.iterator();
        }

        public int size() {
            return entryList.size();
        }

        public Set<T> wrapAsUnmodifiable() {
            return Collections.unmodifiableSet(this);
        }

    }
}
