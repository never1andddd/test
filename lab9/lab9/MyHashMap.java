package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Siling Li
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16; // init capacity and m
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size; //n

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap(){
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    public MyHashMap(int m) {
        buckets = new ArrayMap[m];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < buckets.length; i += 1) {
            buckets[i] = new ArrayMap<K, V>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }
        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        int i = hash(key);
        return buckets[i].get(key);
    }


    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        // double table size if load factor >= MAX_LF
        if (loadFactor()>=MAX_LF) resize(2*buckets.length);

        int i = hash(key);
        if (!buckets[i].containsKey(key)) size++;
        buckets[i].put(key, value);
    }

    private void resize(int chains) {
        MyHashMap<K, V> temp = new MyHashMap<>(chains);
        for (int i = 0; i < buckets.length; i++) {
            for (K key : buckets[i].keySet()) {
                temp.put(key, buckets[i].get(key));
            }
        }
        this.size = temp.size;
        this.buckets = temp.buckets;
    }
    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");

        int i = hash(key);
        V returnValue = get(key);
        if (buckets[i].containsKey(key)) size--;
        buckets[i].remove(key);

        // halve table size if average length of list <= 2
        if (loadFactor()<MAX_LF) resize(buckets.length/2);
        return returnValue;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");

        int i = hash(key);
        V returnValue = get(key);
        if (returnValue != value) return null;
        if (buckets[i].get(key) == value) size--;
        buckets[i].remove(key);

        // halve table size if average length of list <= 2
        if (loadFactor()<MAX_LF / 2) resize(buckets.length/2);
        return returnValue;
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    // return keys in symbol table as an Iterable
    public Iterable<K> keys() {
        Set<K> queue = new HashSet<K>();
        for (int i = 0; i < buckets.length; i++) {
            for (K key : buckets[i])
                queue.add(key);
        }
        return queue;
    }

}
