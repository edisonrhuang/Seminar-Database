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
 * @version 8.31.23
 */
public class Handle {
    
    // Variable Declaration ----------------------------------------------------
    
    private int startPosition;
    private int size;
    
    // Constructor -------------------------------------------------------------
    
    /**
     * Default constructor for Handle
     */
    public Handle() {
        
    }
    
    /**
     * Constructor for handle that takes a start position and size
     * @param startPosition Start position in the memory
     * @param size Size of the data being stored
     */
    public Handle(int startPosition, int size) {
        this.startPosition = startPosition;
        this.size = size;
    }
    
    // Method ------------------------------------------------------------------
    
    /**
     * Default getter for the start position
     * @return Returns the start position
     */
    public int getStartPosition() {
        return startPosition;
    }
    
    /**
     * Default getter for size
     * @return Returns the size of the seminar
     */
    public int getSize() {
        return size;
    }
    
}
