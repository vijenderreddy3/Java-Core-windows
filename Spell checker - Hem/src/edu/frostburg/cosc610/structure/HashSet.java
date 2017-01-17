package edu.frostburg.cosc610.structure;

/**
 * A HashSet implementation Design decisions (To be used in SpellCheck Module):
 * 1. It is sufficient to store strings (Words) only. No generic implementation
 * is prefered for the sake of simplicity. 2. It is designed as read-only: no
 * 'remove' operation is included. In SpellCheck module, it will initially be
 * loaded from the lexicon file once and from then on it will only be used for
 * search operations. 3. Lexicon file can be changed (currently used has 58K
 * words), thus the initial capacity of the hashset is not known exactly. It
 * should support rehashing if the capacity is getting close to full.
 */
public class HashSet {

    /* Initial buckets size: Choosen big since lexicon will be big too (58K words for instance) */
    private final static int INITIAL_CAPACITY = 1024; 
    
    /* How full is our HashSet so that we need to increase the capacity */
    private final static float LOAD_FACTOR = 0.75f;

    /* Number of buckets */
    private int capacity;

    /* Total number of entries in the set*/
    private int size = 0;

    private LinkedList[] buckets;

    /* Contructor */
    public HashSet() {
        /* initalize the buckets */
        capacity = INITIAL_CAPACITY;
        buckets = new LinkedList[capacity];
    }

    /* Add an item to the HashSet */
    public void add(String word) {
        /*
        * Rehash if size of the entries exceed Threshold value which is given as (capacity * LOAD_FACTOR)
        */
        if(size > capacity * LOAD_FACTOR) {
            rehash();
        }
        
        // Add word to the bucket
        addWord(word);
        
        // Increase the total size of entries
        size++;
    }

    private void addWord(String word) {
        // Find the hash to determine the correct bucket
        int hash = hash(word);

        if (buckets[hash] == null) {
            // This will be the first entry in the bucket with this hash
            buckets[hash] = new LinkedList();
        }
        // Add word to this bucket
        buckets[hash].add(word);
    }

    /* Resize the buckets array and rehash the content */
    private void rehash() {
        // The hashcodes of each word will change with the new capacity value
        LinkedList[] temp = buckets;
        
        // Double the buckets size
        capacity *= 2;
        buckets = new LinkedList[capacity];

        for (int i = 0; i < temp.length; i++) {
          
            if (temp[i] != null) {
                LinkedList.Entry current = temp[i].first;
                // Add them all to the new LinkedList array
                while (current != null) {
                    addWord(current.value);
                    current = current.next;
                }
            }
        }
    }
    
    /* Returns true if this word exists in the set */
    public boolean contains(String word) {
       // Find the hash to determine the correct bucket
        int hash = hash(word);

        if (buckets[hash] == null) {
           return false;
        }
        else {
            return buckets[hash].contains(word);
        }
    }

    /* Returns total number of entries in this set */
    public int size() {
        return size;
    }
    
    /* Hashing function, which finds the appropriate bucket to store our value. */
    private int hash(String value) {
        // hashCode can be negative so take the absolute value and fit for array size by mod operation.
        return Math.abs(value.hashCode()) % capacity;
    }

    /* Represents each bucket in the hashset */
    private static class LinkedList {

        /* Hold reference to the first entry */
        private Entry first;

        /* Add value to the list if it does not already exist */
        public void add(String value) {
            if (!contains(value)) {
                /* Add to the head of the linked list */
                first = new Entry(value, first);
            }
        }

        /* Return true if list contains value */
        public boolean contains(String value) {
            Entry current = first;
            while (current != null) {
                if (current.value.equals(value)) {
                    return true;
                }
                current = current.next;
            }
            return false;
        }

        /* Holds an entry object which stores the value and has a reference to the next entry */
        private static class Entry {

            String value; // value of the entry
            Entry next; // reference to the next entry

            public Entry(String value, Entry next) {
                this.value = value;
                this.next = next;
            }

        }

    }

}
