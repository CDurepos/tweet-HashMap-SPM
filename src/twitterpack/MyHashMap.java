package twitterpack;

import java.util.Iterator;
import java.util.LinkedList;

public class MyHashMap<K, V> implements Iterable<K> {
    private static final int DEFAULT_INITIAL_CAPACITY = 32;
    private static final double LOAD_FACTOR = 0.75;

    private LinkedList<MyEntry<K, V>>[] table;
    public int size;
    private int threshold;
    public int resizeCount;

    @SuppressWarnings("unchecked")
    public MyHashMap() {
        // Initialize table
        table = new LinkedList[DEFAULT_INITIAL_CAPACITY];
        threshold = (int) (table.length * LOAD_FACTOR);
        resizeCount = 0;
        size = 0;
    }

    private int indexFor(K key, int length) {
        return key.hashCode() & (length - 1);
    }

    public void put(K key, V value) {
        int index = indexFor(key, table.length);
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }

        for(MyEntry<K, V> entry : table[index]) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }

        table[index].add(new MyEntry<>(key, value));

        size++;
        if (size > threshold) {
            resize();
        }
    }

    public V get(K key) {
       int index = indexFor(key, table.length);

       if (table[index] == null) {
           return null;
       }

       for(MyEntry<K, V> entry : table[index]) {
           if (entry.key.equals(key)) {
               return entry.value;
           }
       }

       return null;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        LinkedList<MyEntry<K, V>>[] newTable = new LinkedList[table.length * 2];

        for (LinkedList<MyEntry<K, V>> myEntries : table) {
            if (myEntries == null) {
                continue;
            }
            newTable[indexFor(myEntries.getFirst().key, newTable.length)] = myEntries;
        }

        table = newTable;

        threshold = (int) (table.length * LOAD_FACTOR);
        resizeCount++;

    }

    @Override
    public Iterator<K> iterator() {
        return new MyHashMapIterator();
    }

    private class MyHashMapIterator implements Iterator<K> {
        private int index; // Tracks the current bucket in the table
        private Iterator<MyEntry<K, V>> cellIterator; // Iterator for the current bucket

        public MyHashMapIterator() {
            index = 0;
            cellIterator = getNextCellIterator();
        }

        private Iterator<MyEntry<K, V>> getNextCellIterator() {
            while (index < table.length) {
                if (table[index] != null && !table[index].isEmpty()) {
                    return table[index++].iterator();
                }
                index++;
            }
            return null;
        }

        @Override
        public boolean hasNext() {
            if (cellIterator == null) {
                return false;
            }
            if (cellIterator.hasNext()) {
                return true;
            }
            cellIterator = getNextCellIterator(); // Move to the next bucket
            return cellIterator != null && cellIterator.hasNext();
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new IllegalStateException("No more elements in the hash map.");
            }
            return cellIterator.next().key;
        }
    }


    protected static class MyEntry<K, V> {
        private K key;
        private V value;

        public MyEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
