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

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import student.TestCase;

/**
 * @author Edison Huang
 * @version 8.25.23
 */
public class SemManagerTest extends TestCase {

    // Variable Declarations ---------------------------------------------------

    // Setup -------------------------------------------------------------------

    /**
     * Setup
     * 
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }


    /**
     * Tear down
     * 
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    // Testing -----------------------------------------------------------------


    /**
     * Tests null command-line arguments
     */
    @Test
    public void testNullArguments() {
        System.out.println("Testing null arguments: ");

        SemManager.main(null);
        assertTrue(systemOut().getHistory().contains("No arguments provided"));
        // Format Line
        System.out.println();
    }


    /**
     * Tests invalid number of arguments
     */
    @Test
    public void testInvalidNumArguemnts() {
        System.out.println("Testing invalid number of arguments: ");

        String[] arguments = { "512", "4" };
        SemManager.main(arguments);
        assertTrue(systemOut().getHistory().contains("Invalid number of "
            + "arguments"));

        // Format Line
        System.out.println();
    }


    /**
     * Tests invalid initial memory size
     */
    @Test
    public void testInvalidMemSize() {
        System.out.println("Testing invalid memory size: ");

        String[] invalidArguments = { "12", "4", "P1Sample_input.txt" };
        SemManager.main(invalidArguments);
        assertTrue(systemOut().getHistory().contains("Initial memory size is "
            + "not a power of two"));

        // Format Line
        System.out.println();
    }


    /**
     * Tests invalid initial hash size
     */
    @Test
    public void testInvalidHashSize() {
        System.out.println("Testing invalid hash size: ");

        String[] invalidArguments = { "512", "7", "P1Sample.input.txt" };
        SemManager.main(invalidArguments);
        assertTrue(systemOut().getHistory().contains("Initial hash size is not "
            + "a power of two"));

        // Format Line
        System.out.println();
    }


    /**
     * Tests invalid file name
     */
    @Test
    public void testInvalidFile() {
        System.out.println("Testing invalid file name: ");

        String[] invalidArguments = { "512", "4", "NotAFile.txt" };
        SemManager.main(invalidArguments);
        assertTrue(systemOut().getHistory().contains("File not found"));

        // Format Line
        System.out.println();
    }


    /**
     * Tests correct command-line arguments
     * 
     * @throws IOException
     */
    @Test
    public void testSemManager() throws IOException {
        System.out.println("Testing SeminarManager: ");

        String[] arguments = { "512", "4", "P1Sample_input.txt" };
        SemManager.main(arguments);
        assertTrue(systemOut().getHistory().contains("Successfully inserted "
            + "record with ID 1"));
        assertTrue(systemOut().getHistory().contains("Memory pool expanded to "
            + "1024 bytes"));

        assertTrue(systemOut().getHistory().contains("Hash table expanded to 8 "
            + "records"));
        assertTrue(systemOut().getHistory().contains("Insert FAILED - There is "
            + "already a record with ID 3"));
        assertTrue(systemOut().getHistory().contains("\n2: 2\n3: 3\n5: 10"));
        assertTrue(systemOut().getHistory().contains("total records: 4"));

        // Format Line
        System.out.println();
    }

}
