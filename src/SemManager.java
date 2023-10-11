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
 * The class containing the main method.
 *
 * @author Edison Huang
 * @version 8.25.23
 */
public class SemManager {

    /**
     * Main project runner that reads command-line arguments.
     * 
     * @param args
     *            Command line parameters
     */
    public static void main(String[] args) {
        // This is the main file for the program.
        Seminar dum = new Seminar();

        // Checks number of arguments
        if (args == null) {
            System.out.println("No arguments provided");
            return;
        }

        if (args.length != 3) {
            System.out.println("Invalid number of arguments");
            return;
        }

        // Checks if proper sizes are given
        int initialMemSize = Integer.parseInt(args[0]);
        if (!powerOfTwo(initialMemSize)) {
            System.out.println("Initial memory size is not a power of two");
            return;
        }

        int initialHashSize = Integer.parseInt(args[1]);
        if (!powerOfTwo(initialHashSize)) {
            System.out.println("Initial hash size is not a power of two");
            return;
        }

        // Checks if file exists
        String fileName = args[2];
        try {
            File file = new File(fileName);
            Scanner scan = new Scanner(file);
            scan.close();
        }
        catch (Exception e) {
            System.out.println("File not found");
            return;
        }

        // Initializes a new Command Processor
        CommandProcessor cp = new CommandProcessor(initialMemSize,
            initialHashSize, fileName);
        
        cp.readLines();

        return;
    }

    // Private Helper Method ---------------------------------------------------

    private static boolean powerOfTwo(int x) {
        return (Math.ceil((Math.log(x) / Math.log(2)))) == 
               (Math.floor(((Math.log(x) / Math.log(2)))));
    }
}
