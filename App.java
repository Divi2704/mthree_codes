class HashDemo<K, V> {
    private Entry<K, V>[] table; // Array to store key-value pairs
    private int size; // Current number of elements in the table
    private static final double LOAD_FACTOR_THRESHOLD = 0.75; // Load factor threshold for resizing

    // Constructor to initialize the hash table with a specified capacity
    @SuppressWarnings("unchecked")
    public HashDemo(int capacity) {
        table = (Entry<K, V>[]) new Entry[capacity]; // Create a table of specified capacity
        size = 0; // Initialize size to 0
    }

    // Method to insert a key-value pair into the hash table
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Null keys are not allowed"); // Null keys are not allowed
        }
        // Resize if load factor threshold is reached
        if ((double) size / table.length >= LOAD_FACTOR_THRESHOLD) {
            resize(); // Resize table if needed
        }
        int index = hash1(key); // Compute hash1 for initial index
        int step = hash2(key); // Compute hash2 for step in case of collision
        int i = 0;
        // Linear probing with double hashing to resolve collisions
        while (table[index] != null && !table[index].getKey().equals(key)) {
            index = (index + step * i) % table.length; // Probe for next index
            i++;
        }
        table[index] = new Entry<>(key, value); // Insert the key-value pair
        size++; // Increment size
    }

    // Method to retrieve a value by key
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Null keys are not allowed");
        }
        int index = hash1(key); // Compute hash1 for initial index
        int step = hash2(key); // Compute hash2 for probing step
        int i = 0;
        // Linear probing with double hashing to find the key
        while (table[index] != null) {
            if (table[index].getKey().equals(key)) {
                return table[index].getValue(); // Return value if key is found
            }
            index = (index + step * i) % table.length; // Probe next index
            i++;
        }
        return null; // Return null if key is not found
    }

    // Method to remove a key-value pair by key
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Null keys are not allowed");
        }
        int index = hash1(key); // Compute hash1 for initial index
        int step = hash2(key); // Compute hash2 for probing step
        int i = 0;
        // Linear probing with double hashing to find the key
        while (table[index] != null) {
            if (table[index].getKey().equals(key)) {
                V value = table[index].getValue();
                table[index] = null; // Remove the entry
                size--; // Decrement size
                return value; // Return the removed value
            }
            index = (index + step * i) % table.length; // Probe next index
            i++;
        }
        return null; // Return null if key is not found
    }

    // Iterate over each entry in the hash table
    public void forEach(java.util.function.BiConsumer<K, V> action) {
        for (Entry<K, V> entry : table) {
            if (entry != null) {
                action.accept(entry.getKey(), entry.getValue()); // Apply the action to each entry
            }
        }
    }

    // Method to resize the hash table when load factor is exceeded
    private void resize() {
        Entry<K, V>[] oldTable = table; // Store the current table
        table = (Entry<K, V>[]) new Entry[oldTable.length * 2]; // Double the table size
        size = 0; // Reset size
        // Rehash all entries from the old table
        for (Entry<K, V> entry : oldTable) {
            if (entry != null) {
                put(entry.getKey(), entry.getValue()); // Reinsert into the new table
            }
        }
    }

    // First hash function (hash1)
    private int hash1(K key) {
        return Math.abs(key.hashCode()) % table.length; // Modulo by table length
    }

    // Second hash function (hash2) for probing step
    private int hash2(K key) {
        return 1 + (Math.abs(key.hashCode()) % (table.length - 1)); // Ensures non-zero step
    }

    // Entry class to store key-value pairs
    private static class Entry<K, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}

public class App {
    public static void main(String[] args) {
        // Create a new hash table with a capacity of 10
        HashDemo<String, Integer> hashTable = new HashDemo<>(10);
        hashTable.put("one", 1); // Insert key-value pairs
        hashTable.put("two", 2);
        hashTable.put("three", 3);

        // Retrieve value for key "one"
        System.out.println("Value for 'one': " + hashTable.get("one")); // Output: 1

        // Remove key "two"
        System.out.println("Removing 'two': " + hashTable.remove("two")); // Output: 2

        // Try to get value for "two" after removal
        System.out.println("Value for 'two' after removal: " + hashTable.get("two")); // Output: null

        // Use forEach method to print all key-value pairs
        hashTable.forEach((key, value) -> {
            System.out.println(key + ": " + value); // Print each key-value pair
        });
    }
}
