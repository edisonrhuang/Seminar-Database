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

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import student.TestCase;

/**
 * @author Edison Huang
 * @version 8.1110.11111
 */
public class HashTableTest extends TestCase {

    // Variable Declaration ----------------------------------------------------

    private HashTable hashTable;
    private Record record5;
    private Record record15;
    private Record record115;
    private Record record1115;
    private Record record11115;
    private Record record65;
    private Record record85;

    // Setup -------------------------------------------------------------------

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        hashTable = new HashTable(10);
        
        String[] keyWordArray = { "key", "word", "array" };
        
        record5 = new Record(new Handle(), 5);
        record15 = new Record(new Handle(), 15);
        record115 = new Record(new Handle(), 115);
        record1115 = new Record(new Handle(), 1115);
        record11115 = new Record(new Handle(), 11115);
        record65 = new Record(new Handle(), 65);
        record85 = new Record(new Handle(), 85);
    }


    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        hashTable = null;
    }

    // Testing -----------------------------------------------------------------


    /**
     * Tests HashTable's insert function with many collisions
     */
    @Test
    public void testInsert() {
        System.out.println("Testing insert: ");
        
        hashTable.insert(record5);
        hashTable.insert(record15);
        hashTable.insert(record115);
        hashTable.insert(record1115);
        hashTable.insert(record11115);
        hashTable.insert(record65);
        hashTable.insert(record65);
        hashTable.print();

        // Format Line
        System.out.println();
    }


    /**
     * Tests HashTable's delete function with many collisions
     */
    public void testManyCollisionsDelete() {
        System.out.println("Testing delete: ");

        hashTable.insert(record5);
        hashTable.insert(record15);
        hashTable.insert(record115);
        hashTable.insert(record1115);
        hashTable.insert(record11115);

        Record record = hashTable.search(1115);
        assertNotNull(record);
        
        record = hashTable.delete(1115);
        assertNotNull(record);

        record = hashTable.search(1115);
        assertNull(record);
        
        hashTable.print();
        assertTrue(systemOut().getHistory().contains("4: TOMBSTONE"));

        hashTable.insert(record65);
        hashTable.insert(record85);

        hashTable.print();

        record = hashTable.delete(15);
        assertNotNull(record);

        record = hashTable.delete(15);
        assertNull(record);

        hashTable.print();

        hashTable.insert(new Record(new Handle(), 15));
        
        hashTable.print();

        // Format Line
        System.out.println();
    }
    
    /**
     * Mutation Testing 1
     */
    @Test
    public void testMutations1() {
        System.out.println("Testing mutations: ");
        
        hashTable = new HashTable(100);
        Record record1 = new Record(new Handle(), 1);
        Record record11 = new Record(new Handle(), 11);
        Record record111 = new Record(new Handle(), 111);
        Record record1111 = new Record(new Handle(), 1111);
        Record record11111 = new Record(new Handle(), 11111);
        Record record111111 = new Record(new Handle(), 111111);
        Record record1111111 = new Record(new Handle(), 1111111);
        Record record11111111 = new Record(new Handle(), 11111111);
        Record record111111111 = new Record(new Handle(), 111111111);
        Record record1111111111 = new Record(new Handle(), 1111111111);
        
        hashTable.insert(record1);
        hashTable.insert(record11);
        hashTable.insert(record111);
        hashTable.insert(record1111);
        hashTable.insert(record11111);
        hashTable.insert(record111111);
        hashTable.insert(record1111111);
        hashTable.insert(record11111111);
        hashTable.insert(record111111111);
        hashTable.insert(record1111111111);
        
        hashTable.delete(1);
        hashTable.delete(11);
        hashTable.delete(111);
        hashTable.delete(1111);
        hashTable.delete(11111);
        hashTable.delete(111111);
        hashTable.delete(1111111);
        hashTable.delete(11111111);
        hashTable.delete(111111111);
        hashTable.delete(1111111111);
        
        hashTable.insert(record1);
        hashTable.insert(record11);
        hashTable.insert(record111);
        hashTable.insert(record1111);
        hashTable.insert(record11111);
        hashTable.insert(record111111);
        hashTable.insert(record1111111);
        hashTable.insert(record11111111);
        hashTable.insert(record111111111);
        hashTable.insert(record1111111111);
        
        // Format Line
        System.out.println();
    }
    
    /**
     * Mutation Testing 11
     */
    public void testMutation11() {
        System.out.println("Testing more mutations: ");
        
        hashTable = new HashTable(1);
        Record record1 = new Record(new Handle(), 1);
        Record record11 = new Record(new Handle(), 11);
        Record record111 = new Record(new Handle(), 111);
        Record record1111 = new Record(new Handle(), 1111);
        Record record11111 = new Record(new Handle(), 11111);
        Record record111111 = new Record(new Handle(), 111111);
        Record record1111111 = new Record(new Handle(), 1111111);
        Record record11111111 = new Record(new Handle(), 11111111);
        Record record111111111 = new Record(new Handle(), 111111111);
        Record record1111111111 = new Record(new Handle(), 1111111111);
        
        hashTable.insert(record1);
        hashTable.delete(1);
        
        hashTable.insert(record1);
        hashTable.insert(record11);
        hashTable.delete(1);
        hashTable.delete(11);
        
        hashTable.insert(record1);
        hashTable.insert(record11);
        hashTable.insert(record111);
        hashTable.delete(1);
        hashTable.delete(11);
        hashTable.delete(111);
        
        hashTable.insert(record1);
        hashTable.insert(record11);
        hashTable.insert(record111);
        hashTable.insert(record1111);
        hashTable.delete(1);
        hashTable.delete(11);
        hashTable.delete(111);
        hashTable.delete(1111);
        
        hashTable.insert(record1);
        hashTable.insert(record11);
        hashTable.insert(record111);
        hashTable.insert(record1111);
        hashTable.insert(record11111);
        hashTable.delete(1);
        hashTable.delete(11);
        hashTable.delete(111);
        hashTable.delete(1111);
        hashTable.delete(11111);
        
        hashTable.insert(record1);
        hashTable.insert(record11);
        hashTable.insert(record111);
        hashTable.insert(record1111);
        hashTable.insert(record11111);
        hashTable.insert(record111111);
        hashTable.delete(1);
        hashTable.delete(11);
        hashTable.delete(111);
        hashTable.delete(1111);
        hashTable.delete(11111);
        hashTable.delete(111111);
        
        
        hashTable.insert(record1);
        hashTable.insert(record11);
        hashTable.insert(record111);
        hashTable.insert(record1111);
        hashTable.insert(record11111);
        hashTable.insert(record111111);
        hashTable.insert(record1111111);
        hashTable.delete(1);
        hashTable.delete(11);
        hashTable.delete(111);
        hashTable.delete(1111);
        hashTable.delete(11111);
        hashTable.delete(111111);
        hashTable.delete(1111111);
        
        hashTable.insert(record1);
        hashTable.insert(record11);
        hashTable.insert(record111);
        hashTable.insert(record1111);
        hashTable.insert(record11111);
        hashTable.insert(record111111);
        hashTable.insert(record1111111);
        hashTable.insert(record11111111);
        hashTable.delete(1);
        hashTable.delete(11);
        hashTable.delete(111);
        hashTable.delete(1111);
        hashTable.delete(11111);
        hashTable.delete(111111);
        hashTable.delete(1111111);
        hashTable.delete(11111111);
        
        hashTable.insert(record1);
        hashTable.insert(record11);
        hashTable.insert(record111);
        hashTable.insert(record1111);
        hashTable.insert(record11111);
        hashTable.insert(record111111);
        hashTable.insert(record1111111);
        hashTable.insert(record11111111);
        hashTable.insert(record111111111);
        hashTable.delete(1);
        hashTable.delete(11);
        hashTable.delete(111);
        hashTable.delete(1111);
        hashTable.delete(11111);
        hashTable.delete(111111);
        hashTable.delete(1111111);
        hashTable.delete(11111111);
        hashTable.delete(111111111);
        
        hashTable.insert(record1);
        hashTable.insert(record11);
        hashTable.insert(record111);
        hashTable.insert(record1111);
        hashTable.insert(record11111);
        hashTable.insert(record111111);
        hashTable.insert(record1111111);
        hashTable.insert(record11111111);
        hashTable.insert(record111111111);
        hashTable.insert(record1111111111);
        hashTable.delete(1);
        hashTable.delete(11);
        hashTable.delete(111);
        hashTable.delete(1111);
        hashTable.delete(11111);
        hashTable.delete(111111);
        hashTable.delete(1111111);
        hashTable.delete(11111111);
        hashTable.delete(111111111);
        hashTable.delete(1111111111);
        
        hashTable.insert(record1);
        hashTable.insert(record11);
        hashTable.insert(record111);
        hashTable.insert(record1111);
        hashTable.insert(record11111);
        hashTable.insert(record111111);
        hashTable.insert(record1111111);
        hashTable.insert(record11111111);
        hashTable.insert(record111111111);
        hashTable.insert(record1111111111);
        
        // Format Line
        System.out.println();
    }
    
}
