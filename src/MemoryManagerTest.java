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
 * @version 9.10.23
 */
public class MemoryManagerTest extends TestCase {

    // Variable Declaration ----------------------------------------------------

    private MemoryManager manager;
    private Seminar seminar1;
    private Seminar seminar2;
    private Seminar seminar3;
    private Seminar seminar4;
    private Seminar seminar5;
    private Seminar seminar6;

    // Setup -------------------------------------------------------------------

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        manager = new MemoryManager(512);

        String[] keyWordArray = { "key", "word", "array" };
        seminar1 = new Seminar(35, "Title", "Date", 2, (short)3, (short)4, 5,
            keyWordArray, "Description");

        String[] keyWordArray1 = { "HCI", "Computer_Science", "VT",
            "Virginia_Tech" };
        seminar2 = new Seminar(1, "Overview of HCI Research at VT",
            "0610051600", 90, (short)10, (short)10, 45, keyWordArray1,
            "This seminar will present an overview of HCI research at VT");

        String[] keyWordArray2 = { "Bioinformatics", "computation_biology",
            "Biology", "Computer_Science", "VT", "Virginia_Tech" };
        seminar3 = new Seminar(2,
            "Computational Biology and Bioinformatics in CS at Virginia Tech",
            "0610071600", 60, (short)20, (short)10, 30, keyWordArray2,
            "Introduction to bioinformatics and computation biology");

        String[] keyWordArray3 = { "high_performance_computing", "grids", "VT",
            "computer", "science" };
        seminar4 = new Seminar(3, "Computing Systems Research at VT",
            "0701250830", 30, (short)30, (short)10, 17, keyWordArray3,
            "Seminar about the Computing systems research at VT");

        String[] keyWordArray4 = { "HPC", "CSE", "computer_science", };
        seminar5 = new Seminar(10, "Overview of HPC and CSE Research at VT",
            "0703301125", 35, (short)0, (short)0, 25, keyWordArray4,
            "Learn what kind of research is done on HPC and CSE at VT");

        String[] keyWordArray5 = { "Bioinformatics", "computation_biology",
            "Biology", "Computer_Science", "VT", "Virginia_Tech" };
        seminar6 = new Seminar(2,
            "Much More Computational Biology and Bioinformatics in CS at "
                + "Virginia Tech", "0610071600", 60, (short)20, (short)10, 30,
            keyWordArray5,
            "Introduction to bioinformatics and lots of computation biology");
    }


    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        manager = null;
        seminar1 = null;
        seminar2 = null;
        seminar3 = null;
        seminar4 = null;
        seminar5 = null;
        seminar6 = null;
    }

    // Testing -----------------------------------------------------------------


    /**
     * Tests MemoryManager's remove method
     */
    @Test
    public void testInsert() {
        System.out.println("Testing insert: ");

        byte[] serialization = new byte[0];

        try {
            serialization = seminar1.serialize();
        }
        catch (Exception e) {
            System.out.println("Error serializing seminar1");
        }

        manager.dump();
        assertTrue(systemOut().getHistory().contains("512: 0"));

        manager.insert(serialization);
        manager.dump();
        assertTrue(systemOut().getHistory().contains("128: 128"));

        manager.insert(serialization);
        manager.dump();
        assertTrue(systemOut().getHistory().contains("256: 256"));

        manager.insert(serialization);
        manager.dump();
        assertTrue(systemOut().getHistory().contains("128: 384"));

        manager.insert(serialization);
        manager.dump();
        assertTrue(systemOut().getHistory().contains(
            "There are no freeblocks in the memory pool"));

        manager.insert(serialization);
        assertTrue(systemOut().getHistory().contains(
            "Memory pool expanded to 1024 bytes"));

        manager.dump();
        manager.dump();
        assertTrue(systemOut().getHistory().contains("256: 768"));

        // Format Line
        System.out.println();
    }


    /**
     * Tests insert with small initial memory size
     */
    @Test
    public void testSmallMemInsert() {
        System.out.println("Testing insert with small initial memory: ");

        manager = new MemoryManager(1);

        byte[] serialization = new byte[0];

        try {
            serialization = seminar1.serialize();
        }
        catch (Exception e) {
            System.out.println("Error serializing seminar1");
        }

        manager.dump();
        assertTrue(systemOut().getHistory().contains("1: 0"));

        manager.insert(serialization);
        assertTrue(systemOut().getHistory().contains(
            "Memory pool expanded to 2 bytes"));
        assertTrue(systemOut().getHistory().contains(
            "Memory pool expanded to 4 bytes"));
        assertTrue(systemOut().getHistory().contains(
            "Memory pool expanded to 8 bytes"));
        assertTrue(systemOut().getHistory().contains(
            "Memory pool expanded to 16 bytes"));
        assertTrue(systemOut().getHistory().contains(
            "Memory pool expanded to 32 bytes"));
        assertTrue(systemOut().getHistory().contains(
            "Memory pool expanded to 64 bytes"));
        assertTrue(systemOut().getHistory().contains(
            "Memory pool expanded to 128 bytes"));

        manager.dump();
        assertTrue(systemOut().getHistory().contains(
            "There are no freeblocks in the memory pool"));
        // assertTrue(systemOut().getHistory().contains("256: 256"));

        manager.insert(serialization);
        assertTrue(systemOut().getHistory().contains(
            "Memory pool expanded to 256 bytes"));

        manager.dump();
        assertTrue(systemOut().getHistory().contains(
            "There are no freeblocks in the memory pool"));

        manager.insert(serialization);
        assertTrue(systemOut().getHistory().contains(
            "Memory pool expanded to 512 bytes"));

        manager.dump();
        assertTrue(systemOut().getHistory().contains("128: 384"));

        manager.insert(serialization);
        manager.dump();
        assertTrue(systemOut().getHistory().contains(
            "There are no freeblocks in the memory pool"));

        manager.insert(serialization);
        assertTrue(systemOut().getHistory().contains(
            "Memory pool expanded to 1024 bytes"));

        manager.dump();
        assertTrue(systemOut().getHistory().contains("128: 640"));

        // Format Line
        System.out.println();
    }


    /**
     * Tests MemoryManager's remove method
     */
    @Test
    public void testRemove() {
        System.out.println("Testing remove: ");

        byte[] serialization = new byte[0];

        try {
            serialization = seminar1.serialize();
        }
        catch (Exception e) {
            System.out.println("Error serializing seminar1");
        }

        manager.dump();
        assertTrue(systemOut().getHistory().contains("512: 0"));

        Handle handle = manager.insert(serialization);
        manager.dump();
        assertTrue(systemOut().getHistory().contains("128: 128"));

        manager.remove(handle);
        manager.dump();
        assertTrue(systemOut().getHistory().contains("512: 0"));

        byte[] serialization2 = new byte[0];
        byte[] serialization3 = new byte[0];
        byte[] serialization4 = new byte[0];
        byte[] serialization5 = new byte[0];

        try {
            serialization2 = seminar2.serialize();
            serialization3 = seminar3.serialize();
            serialization4 = seminar4.serialize();
            serialization5 = seminar5.serialize();
        }
        catch (Exception e) {
            System.out.println("Error serializing seminar1");
        }

        Handle handle2 = manager.insert(serialization2);
        manager.dump();
        assertTrue(systemOut().getHistory().contains("256: 256"));

        Handle handle3 = manager.insert(serialization3);
        manager.dump();
        assertTrue(systemOut().getHistory().contains(
            "There are no freeblocks in the memory pool"));

        Handle handle4 = manager.insert(serialization4);
        manager.dump();
        assertTrue(systemOut().getHistory().contains("256: 768"));

        Handle handle5 = manager.insert(serialization5);
        manager.dump();
        assertTrue(systemOut().getHistory().contains(
            "There are no freeblocks in the memory pool"));

        manager.remove(handle2);
        manager.dump();
        assertTrue(systemOut().getHistory().contains("256: 0"));

        manager.remove(handle3);
        manager.dump();
        assertTrue(systemOut().getHistory().contains("512: 0"));

        handle2 = manager.insert(serialization2);
        handle3 = manager.insert(serialization3);
        manager.dump();
        assertTrue(systemOut().getHistory().contains(
            "There are no freeblocks in the memory pool"));

        manager.remove(handle3);
        manager.dump();
        assertTrue(systemOut().getHistory().contains("256: 256"));
        manager.remove(handle2);
        manager.dump();
        assertTrue(systemOut().getHistory().contains("512: 0"));

        handle2 = manager.insert(serialization2);
        handle3 = manager.insert(serialization3);
        manager.dump();
        assertTrue(systemOut().getHistory().contains(
            "There are no freeblocks in the memory pool"));

        manager.remove(handle4);
        manager.dump();
        assertTrue(systemOut().getHistory().contains("256: 512"));

        manager.remove(handle3);
        manager.dump();
        assertTrue(systemOut().getHistory().contains("256: 256 512"));

        manager.remove(handle5);
        manager.dump();
        assertTrue(systemOut().getHistory().contains("512: 512"));

        manager.remove(handle2);
        manager.dump();
        assertTrue(systemOut().getHistory().contains("1024: 0"));

        // Format Line
        System.out.println();
    }


    /**
     * Tests MemoryManager's get method
     */
    @Test
    public void testGet() {
        System.out.println("Testing get: ");

        byte[] serialization1 = new byte[0];
        byte[] serialization2 = new byte[0];
        byte[] serialization3 = new byte[0];
        byte[] serialization4 = new byte[0];
        byte[] serialization5 = new byte[0];

        try {
            serialization1 = seminar1.serialize();
            serialization2 = seminar2.serialize();
            serialization3 = seminar3.serialize();
            serialization4 = seminar4.serialize();
            serialization5 = seminar5.serialize();
        }
        catch (Exception e) {
            System.out.println("Error serializing seminar1");
        }

        Handle handle1 = manager.insert(serialization1);
        manager.dump();
        Handle handle2 = manager.insert(serialization2);
        manager.dump();
        Handle handle3 = manager.insert(serialization3);
        manager.dump();
        Handle handle4 = manager.insert(serialization4);
        manager.dump();
        Handle handle5 = manager.insert(serialization5);
        manager.dump();

        byte[] copied1 = new byte[serialization1.length];
        byte[] copied2 = new byte[serialization2.length];
        byte[] copied3 = new byte[serialization3.length];
        byte[] copied4 = new byte[serialization4.length];
        byte[] copied5 = new byte[serialization5.length];

        int size1 = manager.get(copied1, handle1, handle1.getSize());
        assertEquals(size1, serialization1.length);
        for (int i = 0; i < size1; i++) {
            assertEquals(copied1[i], serialization1[i]);
        }
        int size2 = manager.get(copied2, handle2, handle2.getSize());
        assertEquals(size2, serialization2.length);
        for (int i = 0; i < size2; i++) {
            assertEquals(copied2[i], serialization2[i]);
        }
        int size3 = manager.get(copied3, handle3, handle3.getSize());
        assertEquals(size3, serialization3.length);
        for (int i = 0; i < size3; i++) {
            assertEquals(copied3[i], serialization3[i]);
        }
        int size4 = manager.get(copied4, handle4, handle4.getSize());
        assertEquals(size4, serialization4.length);
        for (int i = 0; i < size4; i++) {
            assertEquals(copied4[i], serialization4[i]);
        }
        int size5 = manager.get(copied5, handle5, handle5.getSize());
        assertEquals(size5, serialization5.length);
        for (int i = 0; i < size5; i++) {
            assertEquals(copied5[i], serialization5[i]);
        }

        manager.remove(handle1);
        size1 = manager.get(copied1, handle1, handle1.getSize());
        manager.dump();
        assertEquals(size1, 0);

        manager.remove(handle2);
        size2 = manager.get(copied2, handle2, handle2.getSize());
        manager.dump();
        assertEquals(size2, 0);

        // Format Line
        System.out.println();
    }


    /**
     * More Complex Testing
     */
    @Test
    public void testComplex() {
        System.out.println("Complex Testing: ");

        byte[] serialization2 = new byte[0];
        byte[] serialization3 = new byte[0];
        byte[] serialization4 = new byte[0];
        byte[] serialization5 = new byte[0];

        try {
            serialization2 = seminar2.serialize();
            serialization3 = seminar3.serialize();
            serialization4 = seminar4.serialize();
            serialization5 = seminar5.serialize();
        }
        catch (Exception e) {
            System.out.println("Error serializing seminar1");
        }

        manager.dump();
        assertTrue(systemOut().getHistory().contains("512: 0"));

        Handle handle2 = manager.insert(serialization2);
        manager.dump();
        assertTrue(systemOut().getHistory().contains("256: 256"));

        manager.remove(handle2);
        manager.dump();
        assertTrue(systemOut().getHistory().contains("512: 0"));

        Handle handle3 = manager.insert(serialization3);
        manager.dump();
        assertTrue(systemOut().getHistory().contains("256: 256"));

        handle2 = manager.insert(serialization2);
        manager.dump();
        assertTrue(systemOut().getHistory().contains(
            "There are no freeblocks in the memory pool"));

        // Format Line
        System.out.println();
    }

}
