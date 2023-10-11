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
 * @version 9.8.23
 */
public class MemoryManager {

    // Variable Declaration ----------------------------------------------------

    private int maxMemCapacity;
    private int memUsed;
    private byte[] memory;
    private LinkedList[] freeBlocks;

    // Constructor -------------------------------------------------------------

    /**
     * Default constructor for MemoryManager
     * 
     * @param initialMemSize
     *            initial number of bytes for memory manager
     */
    public MemoryManager(int initialMemSize) {
        maxMemCapacity = initialMemSize;
        memory = new byte[initialMemSize];

        int base2 = log2(initialMemSize);

        freeBlocks = new LinkedList[base2 + 1];
        for (int i = 0; i <= base2; i++) {
            freeBlocks[i] = new LinkedList();
        }
        freeBlocks[base2].add(new Block(0, initialMemSize, null));
    }

    // Method ------------------------------------------------------------------


    /**
     * Given a the serialization of a seminar, creates a new handle and
     * allocates memory for it.
     * 
     * @param serialization
     *            The serialization of the seminar
     * @return returns the handle to the seminar
     */
    public Handle insert(byte[] serialization) {
        int size = serialization.length;

        while (size + memUsed > maxMemCapacity) {
            resize();
        }

        int base2 = log2(size) + 1;

        if (freeBlocks[base2].isEmpty()) {
            split(base2);
        }

        Block freeBlock = freeBlocks[base2].getHead();
        int startPosition = freeBlock.getStartPosition();
        Handle handle = new Handle(startPosition, size);

        for (int i = startPosition; i < startPosition + size; i++) {
            memory[i] = serialization[i - startPosition];
        }

        freeBlocks[base2].removeHead();

        memUsed += Math.pow(2, base2);

        return handle;
    }


    /**
     * Given a handle, find the location and remove it from the memory manager
     * 
     * @param handle
     *            Handle we want to remove
     */
    public void remove(Handle handle) {
        int startPosition = handle.getStartPosition();
        int size = handle.getSize();

        for (int i = startPosition; i < startPosition + size; i++) {
            memory[i] = 0;
        }

        int base2 = log2(size) + 1;

        freeBlocks[base2].add(new Block(startPosition, (int)Math.pow(2, base2),
            null));

        memUsed -= Math.pow(2, base2);

        merge();
    }


    /**
     * Given a handle and it's size, finds it's start location and copies the
     * data from the memory into the byte array provided.
     * 
     * @param space
     *            Array for bytes to be copied to
     * @param handle
     *            Handle to be copied
     * @param size
     *            Size of handle
     * @return Returns the number of bytes copied
     */
    public int get(byte[] space, Handle handle, int size) {
        int startPosition = handle.getStartPosition();
        int sizeCopied = 0;

        for (int i = startPosition; i < startPosition + size; i++) {
            space[i - startPosition] = memory[i];
            sizeCopied++;
        }

        if (checkAllZeros(space)) {
            return 0;
        }

        return sizeCopied;
    }


    /**
     * Prints the list of the freeblock
     */
    public void dump() {
        System.out.println("Freeblock List:");

        int printChecker = 0;

        for (int i = 0; i < freeBlocks.length; i++) {
            LinkedList list = freeBlocks[i];
            Block curr = list.getHead();

            if (curr != null) {
                System.out.printf((int)Math.pow(2, i) + ":");

                while (curr != null) {
                    System.out.printf(" " + curr.getStartPosition());
                    curr = curr.next;
                }

                printChecker++;

                System.out.println();
            }
        }

        if (printChecker == 0) {
            System.out.println("There are no freeblocks in the memory pool");
        }
    }

    // Private Helper Method ---------------------------------------------------


    private void resize() {
        byte[] curr = new byte[maxMemCapacity * 2];

        for (int i = 0; i < memory.length; i++) {
            curr[i] = memory[i];
        }

        memory = curr;

        LinkedList[] currBlocks = new LinkedList[freeBlocks.length + 1];
        for (int i = 0; i < freeBlocks.length; i++) {
            currBlocks[i] = freeBlocks[i];
        }
        freeBlocks = currBlocks;
        freeBlocks[freeBlocks.length - 1] = new LinkedList();
        freeBlocks[log2(maxMemCapacity)].add(new Block(maxMemCapacity,
            maxMemCapacity, null));

        merge();

        maxMemCapacity *= 2;

        System.out.println("Memory pool expanded to " + maxMemCapacity
            + " bytes");
    }


    private int log2(int x) {
        return (int)(Math.log(x) / Math.log(2));
    }


    private void merge() {
        for (int i = 0; i < freeBlocks.length; i++) {
            if (freeBlocks[i].isEmpty()) {
                continue;
            }

            Block curr = freeBlocks[i].getHead();
            while (curr != null) {
                Block buddy = findBuddy(curr);
                if (buddy != null) {
                    // Merged block
                    int firstPosition = (curr.getStartPosition() < buddy
                        .getStartPosition())
                            ? curr.getStartPosition()
                            : buddy.getStartPosition();
                    freeBlocks[i + 1].add(new Block(firstPosition, curr
                        .getSize() * 2, null));

                    freeBlocks[i].remove(curr);
                    freeBlocks[i].remove(buddy);
                }
                curr = curr.next;
            }
        }
    }


    private void split(int targetSize) {
        while (freeBlocks[targetSize].size() == 0) {
            for (int i = targetSize + 1; i < freeBlocks.length; i++) {
                if (freeBlocks[i].size() > 0) {
                    Block block = freeBlocks[i].getHead();

                    freeBlocks[i - 1].add(new Block(block.getStartPosition(),
                        block.getSize() / 2, null));
                    freeBlocks[i - 1].add(new Block(block.getStartPosition()
                        + block.getSize() / 2, block.getSize() / 2, null));

                    freeBlocks[i].removeHead();

                    break;
                }
            }
        }
    }


    private Block findBuddy(Block block) {
        int startPosition = block.getStartPosition();
        int size = block.size;
        Block findBuddy = block.next;
        while (findBuddy != null) {

            if ((startPosition | size) == (findBuddy.getStartPosition()
                | size)) {
                return findBuddy;
            }

            findBuddy = findBuddy.next;
        }
        return null;
    }


    private boolean checkAllZeros(byte[] array) {
        for (byte b : array) {
            if (b != 0) {
                return false;
            }
        }
        return true;
    }

    // Private Class -----------------------------------------------------------

    private class LinkedList {
        // Variable Declaration ------------------------------------------------

        private int size;
        private Block head;

        // Constructor ---------------------------------------------------------

        LinkedList() {
            size = 0;
            head = null;
        }

        // Method --------------------------------------------------------------


        void add(Block block) {

            if (isEmpty()) {
                head = block;
            }
            else {
                Block curr = head;

                while (curr.hasNext()) {
                    curr = curr.next;
                }

                curr.setNext(block);
            }
            size++;
            sort();
        }


        Block getHead() {
            return head;
        }


        void removeHead() {
            head = head.next;
            size--;
        }


        boolean remove(Block block) {
            if (head.equals(block)) {
                head = head.next;
                size--;
                return true;
            }

            Block curr = head;
            while (size() >= 2 && (curr.hasNext())) {
                if (block.equals(curr.next)) {
                    if (curr.next.hasNext()) {
                        curr.setNext(curr.next.next);
                    }
                    else {
                        curr.setNext(null);
                    }
                    size--;
                    return true;
                }
                curr = curr.next;
            }

            return false;
        }


        int size() {
            return size;
        }


        boolean isEmpty() {
            return size == 0;
        }
        
        public void sort() {
            Block curr = head;
            Block next = null;
            int tempStart;
            
            if (head == null) {
                return;
            } 
            else {
                while (curr != null) {
                    next = curr.next;
                    
                    while (next != null) {
                        if (curr.getStartPosition() > next.getStartPosition()) {
                            tempStart = curr.getStartPosition();
                            curr.startPosition = next.startPosition;
                            next.startPosition = tempStart;
                        }
                        
                        next = next.next;
                    }
                    
                    curr = curr.next;
                }
            }
        }

    }


    private class Block {

        // Variable Declaration ------------------------------------------------

        private int startPosition;
        private int size;
        private Block next;

        // Constructor ---------------------------------------------------------

        Block(int startPosition, int size, Block next) {
            this.startPosition = startPosition;
            this.size = size;
            this.next = next;
        }

        // Method --------------------------------------------------------------


        int getStartPosition() {
            return startPosition;
        }


        int getSize() {
            return size;
        }


        boolean hasNext() {
            return next != null;
        }


        void setNext(Block block) {
            next = block;
        }
    }

}
