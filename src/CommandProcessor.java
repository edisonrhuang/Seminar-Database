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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Edison Huang
 * @version 8.29.23
 */
public class CommandProcessor {

    // Variable Declaration ----------------------------------------------------

    private String fileName;
    private SeminarDB seminarDB;

    // Constructor -------------------------------------------------------------

    /**
     * Default constructor for CommandProcessor
     * 
     * @param initialMemSize initial size of memory
     * @param initialHashSize initial size of hash
     * @param fileName File name
     */
    public CommandProcessor(int initialMemSize, int initialHashSize, 
        String fileName) {
        this.fileName = fileName;
        seminarDB = new SeminarDB(initialMemSize, initialHashSize);
    }

    // Method ------------------------------------------------------------------


    /**
     * Reads commands from the file and sends commands to SeminarDB to process
     */
    public void readLines() {
        // Verify file exists
        Scanner scan;
        try {
            File file = new File(fileName);
            scan = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
            return;
        }
        
        // Scans document
        while (scan.hasNext()) {
            String command = scan.next(); // Scans initial command

            // Confirmation Check -- REMOVE WHEN FINISHED
            //System.out.println("Command Read: " + command);

            switch (command) {
                case "insert":
                    int identifier = scan.nextInt(); // Scans ID after command
                    scan.nextLine(); // Finishes the command line

                    // Scans Seminar Information
                    String seminarTitle = scan.nextLine();
                    String logistics = scan.nextLine();
                    String keyWords = scan.nextLine();
                    String description = scan.nextLine();

                    // Normalize Each Line
                    seminarTitle = seminarTitle.trim().replaceAll(" +", " ");
                    logistics = logistics.trim().replaceAll(" +", " ");
                    keyWords = keyWords.trim().replaceAll(" +", " ");
                    description = description.trim(); //.replaceAll(" +", " ");

                    // Array forming
                    String[] logisticArray = logistics.split(" ");
                    String[] keyWordArray = keyWords.split(" ");

                    seminarDB.insert(identifier,
                        seminarTitle,
                        logisticArray[0],
                        Integer.parseInt(logisticArray[1]),
                        (short)Integer.parseInt(logisticArray[2]),
                        (short)Integer.parseInt(logisticArray[3]),
                        Integer.parseInt(logisticArray[4]), 
                        keyWordArray,
                        description);
                    
                    break;

                case "delete":
                    identifier = scan.nextInt(); // Scans ID after command
                    scan.nextLine(); // Finishes the command line
                    seminarDB.delete(identifier);
                    break;

                case "search":
                    identifier = scan.nextInt(); // Scans ID after command
                    scan.nextLine(); // Finishes the command line
                    seminarDB.search(identifier);
                    break;

                case "print":
                    String printType = scan.next(); // Scans the type of print

                    switch (printType) {
                        case "hashtable":
                            seminarDB.printHashTable();
                            break;

                        case "blocks":
                            seminarDB.printBlocks();
                            break;

                        default:
                            System.out.println("Print command has bad "
                                               + "paramters");
                            break;
                    }

                    scan.nextLine(); // Finishes the command line
                    break;

                default:
                    System.out.println("Unrecognized input: " + command);
                    break;

            }
        }
        
        scan.close();
    }
}
