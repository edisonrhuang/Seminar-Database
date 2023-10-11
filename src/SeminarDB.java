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

import java.util.Scanner;

/**
 * @author Edison Huang
 * @version 8.29.23
 */
public class SeminarDB {

    // Variable Declaration ----------------------------------------------------

    private HashTable table;
    private MemoryManager memManager;

    // Constructor -------------------------------------------------------------

    /**
     * Default constructor with initial memory and hash size given.
     * 
     * @param initialMemSize
     *            Initial memory size
     * @param initialHashSize
     *            Initial hash size
     */
    public SeminarDB(int initialMemSize, int initialHashSize) {
        table = new HashTable(initialHashSize);
        memManager = new MemoryManager(initialMemSize);
    }

    // Method ------------------------------------------------------------------


    /**
     * Takes in the parameters of a seminar and creates a new Seminar object and
     * creates Record object that stores the Seminar's information. Record is
     * then passed to the HashTable to be inserted.
     * 
     * @param identifier
     *            Unique ID
     * @param title
     *            Seminar Title
     * @param date
     *            Seminar Date
     * @param length
     *            Seminar Length
     * @param x
     *            Seminar x-coord
     * @param y
     *            Seminar y-coord
     * @param cost
     *            Seminar cost
     * @param keyWordArray
     *            Seminar key words
     * @param description
     *            Seminar description
     */
    public void insert(int identifier, String title, String date, int length,
        short x, short y, int cost, String[] keyWordArray, String description) {
        if (table.search(identifier) != null) {
            System.out.println("Insert FAILED - There is already a record "
                + "with ID " + identifier);
            return;
        }

        // Creates new Seminar object
        Seminar sem = new Seminar(identifier, title, date, length, x, y, cost,
            keyWordArray, description);

        // Attempts to serialize seminar
        byte[] serialization = new byte[0];
        try {
            serialization = sem.serialize();
        }
        catch (Exception e) {
            System.out.println("Error serializing seminar");
        }

        // Creates a Handle from the inserted bytes into the Memory Manager
        Handle handle = memManager.insert(serialization);

        // Creates new Record object
        Record record = new Record(handle, identifier);

        // Inserts record into the table
        table.insert(record);
        System.out.println("Successfully inserted record with ID " + record
            .getKey());

        // Prints seminar information
        System.out.println(sem.toString());

        // Prints the size of the seminar
        System.out.println("Size: " + serialization.length);
    }


    /**
     * Takes in the key as a parameter and passes the key to the HashTable to be
     * deleted.
     * 
     * @param key
     *            Key to be deleted
     */
    public void delete(int key) {
        Record record = table.delete(key);

        if (record != null) {
            System.out.println("Record with ID " + key + " successfully "
                + "deleted from the database");

            memManager.remove(record.getHandle());
        }
        else {
            System.out.println("Delete FAILED -- There is no record with ID "
                + key);
        }
    }


    /**
     * Takes in the key as a parameter and passes the key to the HashTable to be
     * searched.
     * 
     * @param key
     *            Key to be searched for
     */
    public void search(int key) {
        Record record = table.search(key);

        if (record != null) {
            System.out.println("Found record with ID " + key + ":");

            byte[] serialized = new byte[record.getHandle().getSize()];
            memManager.get(serialized, record.getHandle(),
                record.getHandle().getSize());

            try {
                Seminar seminar = Seminar.deserialize(serialized);
                System.out.println(seminar.toString());
            }
            catch (Exception e) {
                System.out.println("Error deserializing seminar");
            }
        }
        else {
            System.out.println("Search FAILED -- There is no record with ID "
                + key);
        }
    }


    /**
     * Prints the hashtable from HashTable
     */
    public void printHashTable() {
        table.print();
    }


    /**
     * Prints the free blocks of MemoryManager
     */
    public void printBlocks() {
        memManager.dump();
    }
}
