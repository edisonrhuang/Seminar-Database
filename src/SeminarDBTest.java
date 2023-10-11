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
 * @version 8.29.23
 */
public class SeminarDBTest extends TestCase {

    // Variable Declarations ---------------------------------------------------

    private SeminarDB seminarDB;

    // Setup -------------------------------------------------------------------

    /**
     * Setup
     * 
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        seminarDB = new SeminarDB(512, 4);
    }


    /**
     * Tear Down
     * 
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        seminarDB = null;
    }

    // Testing -----------------------------------------------------------------


    /**
     * Tests SeminarDB's insert method
     */
    @Test
    public void testInsert() {
        System.out.println("Testing SeminarDB's insert: ");

        String[] keyWordArray = { "key", "words", "array" };

        seminarDB.insert(1, "Title", "Date", 2, (short)3, (short)4, 5,
            keyWordArray, "Description");
        assertTrue(systemOut().getHistory().contains("Successfully inserted "
            + "record with ID 1"));

        // Duplicate insert
        seminarDB.insert(1, "Title", "Date", 2, (short)3, (short)4, 5,
            keyWordArray, "Description");
        assertTrue(systemOut().getHistory().contains("Insert FAILED - There is "
            + "already a record with ID 1"));

        // Format Line
        System.out.println();
    }


    /**
     * Tests SeminarDB's delete method
     */
    @Test
    public void testDelete() {
        System.out.println("Testing SemianrDB's delete: ");

        String[] keyWordArray = { "Key", "Words" };
        seminarDB.insert(1, "Title", "Date", 2, (short)3, (short)4, 5,
            keyWordArray, "Description");
        assertTrue(systemOut().getHistory().contains("Successfully inserted "
            + "record with ID 1"));

        seminarDB.delete(1);
        assertTrue(systemOut().getHistory().contains("Record with ID 1 "
            + "successfully deleted from the database"));
        seminarDB.delete(1);
        assertTrue(systemOut().getHistory().contains("Delete FAILED -- There "
            + "is no record with ID 1"));

        // Format Line
        System.out.println();
    }


    /**
     * Tests SeminarDB's search method
     */
    @Test
    public void testSearch() {
        System.out.println("Testing SeminarDB's search: ");

        String[] keyWordArray = { "Key", "Words" };
        seminarDB.insert(1, "Title", "Date", 2, (short)3, (short)4, 5,
            keyWordArray, "Description");
        assertTrue(systemOut().getHistory().contains("Successfully inserted "
            + "record with ID 1"));

        seminarDB.search(1);
        assertTrue(systemOut().getHistory().contains(
            "Found record with ID 1:"));
        seminarDB.search(2);
        assertTrue(systemOut().getHistory().contains("Search FAILED -- There "
            + "is no record with ID 2"));

        // Format Line
        System.out.println();
    }
    
    /**
     * Tests SemianrDB's print method
     */
    @Test
    public void testPrint() {
        System.out.println("Testing SeminarDB's print: ");
        
        String[] keyWordArray = { "Key", "Words" };
        seminarDB.insert(1, "Title", "Date", 2, (short)3, (short)4, 5,
            keyWordArray, "Description");
        assertTrue(systemOut().getHistory().contains("Successfully inserted "
            + "record with ID 1"));
        
        seminarDB.printHashTable();
        assertTrue(systemOut().getHistory().contains("Hashtable:\n"
            + "1: 1\n"
            + "total records: 1"));

        seminarDB.printBlocks();
        assertTrue(systemOut().getHistory().contains("256: 256"));
        
        // Format Line
        System.out.println();
    }

}
