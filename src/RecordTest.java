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

/**
 * @author Edison Huang
 * @version 9.10.23
 */
public class RecordTest {

    // Variable Declaration ----------------------------------------------------
    
    // Setup -------------------------------------------------------------------
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }


    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    // Testing -----------------------------------------------------------------

    /**
     * Tests Record's getter methods
     */
    @Test
    public void testGetters() {
        Handle handle = new Handle();
        Record record = new Record(handle, 1);
        
        assertEquals(handle, record.getHandle());
        assertEquals(1, record.getKey());
    }

}
