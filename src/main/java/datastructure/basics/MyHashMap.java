package datastructure.basics;

import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

public class MyHashMap<K, V> {

    private final LinkedList<Entry<K, V>>[] entries = new LinkedList[10];

    public V get(K key) {
        final Entry<K, V> entry = getEntry(key);
        if (Objects.isNull(entry))
            throw new IllegalStateException();
        return entry.value;
    }

    public boolean isEmpty() {
        return Arrays.stream(entries).filter(Objects::nonNull).allMatch(AbstractCollection::isEmpty);
    }

    public V put(K key, V value) {
        final Entry<K, V> entry = getEntry(key);
        if (Objects.nonNull(entry)) {
            entry.value = value;
            return value;
        }
        getOrCreateBucket(key).addLast(new Entry<>(key, value));
        return value;
    }

    private LinkedList<Entry<K, V>> getOrCreateBucket(K key) {
        final int index = hash(key);
        if (Objects.isNull(entries[index]))
            entries[index] = new LinkedList<>();
        return entries[index];
    }

    public V remove(K key) {
        final Entry<K, V> entry = getEntry(key);
        if (Objects.isNull(entry))
            throw new IllegalStateException();
        getBucket(key).remove(entry);
        return entry.value;
    }

    private Entry<K, V> getEntry(K key) {
        final LinkedList<Entry<K, V>> bucket = getBucket(key);
        if (Objects.nonNull(bucket)) {
            for (Entry<K, V> entry : bucket) {
                if (entry.key == key)
                    return entry;
            }
        }
        return null;
    }

    private LinkedList<Entry<K, V>> getBucket(K key) {
        return entries[hash(key)];
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % entries.length;
    }

    private class Entry<K, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}

class MyHashMapMain {

    public static void main(String[] args) {
        MyHashMap<Integer, String> myMap = new MyHashMap<>();
        System.out.println(myMap.isEmpty());
        myMap.put(1, "A");
        myMap.put(2, "B");
        myMap.put(2, "C");
        System.out.println(myMap.remove(1));
        System.out.println(myMap);
        System.out.println(myMap.isEmpty());
    }

}