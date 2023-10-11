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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import student.TestCase;

/**
 * @author Edison Huang
 * @version 8.29.23
 */
public class CommandProcessorTest extends TestCase {

    // Variable Declaration ----------------------------------------------------
    private CommandProcessor cp;

    // Setup -------------------------------------------------------------------

    /**
     * Setup
     * 
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        cp = new CommandProcessor(512, 4, "P1Sample_input.txt");
    }


    /**
     * Tear down
     * 
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        cp = null;
    }

    // Testing -----------------------------------------------------------------


    /**
     * Tests invalid file name
     */
    @Test
    public void testInvalidFile() {
        System.out.println("Testing invalid file: ");
        
        cp = new CommandProcessor(512, 4, "not a file");
        cp.readLines();
        assertTrue(systemOut().getHistory().contains("File not found"));
        
        // Format Line
        System.out.println();
    }

    /**
     * Tests empty file
     */
    @Test
    public void testEmptyFile() {
        System.out.println("Testing empty file: ");
        
        cp = new CommandProcessor(512, 4, "empty.txt");
        
        // Format Line
        System.out.println();
    }

    /**
     * Tests valid file with invalid commands
     */
    @Test
    public void testInvalidCommands() {
        System.out.println("Testing invalid commands: ");
        
        cp = new CommandProcessor(512, 4, "P1Sample_input2.txt");
        cp.readLines();
        assertTrue(systemOut().getHistory().contains(
            "Print command has bad paramters"));
        assertTrue(systemOut().getHistory().contains(
            "Unrecognized input: command"));
        
        // Format Line
        System.out.println();
    }


    /**
     * Tests valid file name
     */
    @Test
    public void testValidFile() {
        System.out.println("Testing CommandProcessor: ");
        
        cp.readLines();
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
