// On my honor:
// - I have not used source code obtained from another current or
// former student, or any other unauthorized source, either
// modified or unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

/**
 * @author Edison Huang
 * @version 8.30.23
 */
public class HashTable {

    // Variable Declarations ---------------------------------------------------

    private int maxHashCapacity;
    private int currHashCapacity;
    private Record[] hashTable;

    // Constructor -------------------------------------------------------------

    /**
     * Default constructor for HashTable
     * 
     * @param initialHashSize
     *            Initial hash size
     */
    public HashTable(int initialHashSize) {
        hashTable = new Record[initialHashSize];

        maxHashCapacity = initialHashSize;
    }

    // Methods -----------------------------------------------------------------


    /**
     * Given a record, takes the key and uses the hash function to find an index
     * for the record. If collisions occur, a second hashing function will be
     * used repeatedly until a free space is open. If a duplicate entry occurs,
     * the record being inserted is instead discarded.
     * 
     * @param record
     *            Record to be inserted
     */
    public void insert(Record record) {
        // Checks if hash table has reached 50% capacity
        while (currHashCapacity >= (maxHashCapacity / 2)) {
            this.resize();
        }

        // Initial hash function
        int index = firstHash(record.getKey());

        // If slot is taken, use second hash function until open spot
        while (hashTable[index] instanceof Record) {
            // Checks if the index is a tomb stone
            if (hashTable[index].getKey() == -1) {
                hashTable[index] = record;
                currHashCapacity++;
                return;
            }

            int newIndex = secondHash(record.getKey());
            index = (index + newIndex) % maxHashCapacity;
        }

        // Insert into hash table
        hashTable[index] = record;

        // Increases current capacity
        currHashCapacity++;
    }


    /**
     * Given a key to search for, searches the hash table using the hash
     * functions until either the a record is found or an empty record is found.
     * If an empty record is found, the key was never inserted. If a record was
     * found, checks if the keys are the same. If they differ or the key is -1
     * (tomb stone), then the search continues. If the keys are the same,
     * deletes the record and replaces it with a tomb stone.
     * @param key Key to be deleted
     * @return Returns the deleted record, null otherwise
     */
    public Record delete(int key) {
        int index = firstHash(key);

        while (hashTable[index] instanceof Record) {
            // If IDs match, delete the record and replace with a tomb stone
            if (hashTable[index].getKey() == key) {
                Record temp = hashTable[index];
                hashTable[index] = new Record(new Handle(), -1);
                
                currHashCapacity--;
                
                return temp;
            }

            int newIndex = secondHash(key);
            index = (index + newIndex) % maxHashCapacity;
        }
        
        return null;
    }


    /**
     * Given a key to search for, searches the hash table using the hash
     * functions until either the a record is found or an empty record is found.
     * If an empty record is found, the key was never inserted. If a record was
     * found, checks if the keys are the same. If they differ or the key is -1
     * (tomb stone), then the search continues. If the keys are the same, the
     * record is found and the seminar info is printed.
     * @param key Key to be searched
     * @return Returns the record if found, null otherwise
     */
    public Record search(int key) {
        int index = firstHash(key);
        
        while (hashTable[index] instanceof Record) {
            // If IDs match, record is found
            if (hashTable[index].getKey() == key) {
                return hashTable[index];
            }
            
            int newIndex = secondHash(key);
            index = (index + newIndex) % maxHashCapacity;
        }
        
        return null;
    }


    /**
     * Given a print type, prints a message. If hashtable is provided as the
     * command, prints a copy of the hash table. If blocks is provided as the
     * command, prints out information about the memory.
     */
    public void print() {
        int recordCount = 0;
        System.out.println("Hashtable:");
        for (int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] instanceof Record) {
                
                if (hashTable[i].getKey() == -1) {
                    System.out.println(i + ": TOMBSTONE");
                    continue;
                }
                
                System.out.println(i + ": " + hashTable[i].getKey());
                recordCount++;
            }
        }
        System.out.println("total records: " + recordCount);
    }
    
    // Private Helper Method ---------------------------------------------------


    private int firstHash(int key) {
        return key % maxHashCapacity;
    }


    private int secondHash(int key) {
        return (((key / maxHashCapacity) % (maxHashCapacity / 2)) * 2) + 1;
    }


    private void resize() {
        maxHashCapacity *= 2;
        Record[] newTable = new Record[maxHashCapacity];
        rehash(newTable);

        System.out.println("Hash table expanded to " + maxHashCapacity
            + " records");
    }


    private void rehash(Record[] newTable) {
        for (int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] instanceof Record) {
                int index = firstHash(hashTable[i].getKey());
                
                if (index == -1) {
                    continue;
                }

                while (newTable[index] instanceof Record) {
                    int newIndex = secondHash(hashTable[i].getKey());
                    index = (index + newIndex) % maxHashCapacity;
                }

                newTable[index] = hashTable[i];
            }
        }

        hashTable = newTable;
    }

}
