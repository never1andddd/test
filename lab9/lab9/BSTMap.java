package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Siling Li
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        // Return value associated with key in the subtree rooted at p;
        // return null if key not present in subtree rooted at p.
        if (p == null) return null;
        int cmp = key.compareTo(p.key);
        if (cmp < 0) return getHelper(key, p.left);
        else if (cmp > 0) return getHelper(key, p.right);
        else return p.value;
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        // Change key’s value to value if key in subtree rooted at p.
        // Otherwise, add new node to subtree associating key with value.
        if (p == null) return new Node(key, value);
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            p.left = putHelper(key, value, p.left);
        } else if (cmp > 0) {
            p.right = putHelper(key, value, p.right);
        } else {
            p.value = value;
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        // Search for key. Update value if found; grow table if new.
        root = putHelper(key, value, root);
        size += 1;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////
    private void keySetHelper(Set<K> keySet, Node node) {
        if (node == null) {
            return;
        }
        keySet.add(node.key);
        keySetHelper(keySet, node.left);
        keySetHelper(keySet, node.right);
    }

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        keySetHelper(keySet, root);
        return keySet;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    // min Key in the tree
    public K min() {
        return minHelper(root).key;
    }
    // min key rooted at node p
    private Node minHelper(Node p) {
        if (p.left == null) return p;
        return minHelper(p.left);
    }

    private Node deleteMin(Node x)
    {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        return x;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        V returnValue = get(key);
        if (returnValue == null) return null;
        root = removeHelper(root, key);
        size -= 1;
        return returnValue;
    }

    private Node removeHelper(Node p, K key){
        if (p == null) return null;
        int cmp = key.compareTo(p.key);
        if (cmp < 0) p.left = removeHelper(p.left, key);
        else if (cmp > 0) p.right = removeHelper(p.right, key);
        else
        {
            if (p.right == null) return p.left;
            if (p.left == null) return p.right;
            Node t = p;
            p = minHelper(t.right); // See page 407.
            p.right = deleteMin(t.right);
            p.left = t.left;
        }
        return p;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        V returnValue = get(key);
        if (returnValue == null | !returnValue.equals(value)) return null;
        root = removeHelper(root, key);
        size -= 1;
        return returnValue;
    }

}
