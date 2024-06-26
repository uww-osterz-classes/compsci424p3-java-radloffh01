/* COMPSCI 424 Program 3
 * Name:
 * 
 * This is a template. Program3.java *must* contain the main class
 * for this program. 
 * 
 * You will need to add other classes to complete the program, but
 * there's more than one way to do this. Create a class structure
 * that works for you. Add any classes, methods, and data structures
 * that you need to solve the problem and display your solution in the
 * correct format.
 */

package compsci424.p3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

/**
 * Main class for this program. To help you get started, the major
 * steps for the main program are shown as comments in the main
 * method. Feel free to add more comments to help you understand
 * your code, or for any reason. Also feel free to edit this
 * comment to be more helpful.
 */
public class Program3 {
    // Declare any class/instance variables that you need here.

    /**
     * @param args Command-line arguments. 
     * 
     * args[0] should be a string, either "manual" or "auto". 
     * 
     * args[1] should be another string: the path to the setup file
     * that will be used to initialize your program's data structures. 
     * To avoid having to use full paths, put your setup files in the
     * top-level directory of this repository.
     * - For Test Case 1, use "424-p3-test1.txt".
     * - For Test Case 2, use "424-p3-test2.txt".
     * 
     */
    // int[][] need;
    // int[][] max;
    // int[] available;
    // int[][] allocation;
    public static void main(String[] args) {
        // Code to test command-line argument processing.
        // You can keep, modify, or remove this. It's not required.

        //args[0] = "manual";
        args[0] = "auto";

        args[1] = "424-p3-test1.txt";
        //args[1] = "424-p3-test2.txt";

        if (args.length < 2) {
            System.err.println("Not enough command-line arguments provided, exiting.");
            return;
        }
        System.out.println("Selected mode: " + args[0]);
        System.out.println("Setup file location: " + args[1]);

        // 1. Open the setup file using the path in args[1]
        String currentLine;
        BufferedReader setupFileReader;
         int[] available;
         int[][] max;
         int[][] allocation;
        



        try {
            setupFileReader = new BufferedReader(new FileReader(args[1]));
        } catch (FileNotFoundException e) {
            System.err.println("Cannot find setup file at " + args[1] + ", exiting.");
            return;
       }

        // 2. Get the number of resources and processes from the setup
        // file, and use this info to create the Banker's Algorithm
        // data structures
        int numResources;
        int numProcesses;

        // For simplicity's sake, we'll use one try block to handle
        // possible exceptions for all code that reads the setup file.
        try {
            // Get number of resources
            currentLine = setupFileReader.readLine();
            if (currentLine == null) {
                System.err.println("Cannot find number of resources, exiting.");
                setupFileReader.close();
                return;
            }
            else {
                numResources = Integer.parseInt(currentLine.split(" ")[0]);
                System.out.println(numResources + " resources");
            }
 
            // Get number of processes
            currentLine = setupFileReader.readLine();
            if (currentLine == null) {
                System.err.println("Cannot find number of processes, exiting.");
                setupFileReader.close();
                return;
            }
            else {
                numProcesses = Integer.parseInt(currentLine.split(" ")[0]);
                System.out.println(numProcesses + " processes");
            }

            // Create the Banker's Algorithm data structures, in any
            // way you like as long as they have the correct size

            max = new int[numProcesses][numResources];
            available = new int[numResources];
            allocation = new int[numProcesses][numResources];
            
            currentLine = setupFileReader.readLine();
            System.out.println(currentLine);

            currentLine = setupFileReader.readLine();
            //System.out.println(currentLine);
            if (currentLine == null) {
                System.err.println("Cannot find number of processes, exiting.");
                setupFileReader.close();
                return;
            }
            else {
                for(int i = 0; i < currentLine.split(" ").length; i++){
                    int num = Integer.parseInt(currentLine.split(" ")[i]);
                    available[i] = num;
                }
                
            }
            for(int i = 0; i < available.length; i++){
                System.out.print(available[i] + " ");
            }

            currentLine = setupFileReader.readLine();
            System.out.println("\n" +currentLine);

            for(int i = 0; i < max.length; i++){
                currentLine = setupFileReader.readLine();
                //System.out.println(currentLine);
                for(int j = 0; j < currentLine.split(" ").length; j++){
                    int num = Integer.parseInt(currentLine.split(" ")[j]);
                    max[i][j] = num;
                }
            }
            

            currentLine = setupFileReader.readLine();
            System.out.println("\n" +currentLine);

            for(int i = 0; i < allocation.length; i++){
                currentLine = setupFileReader.readLine();
                //System.out.println(currentLine);
                for(int j = 0; j < currentLine.split(" ").length; j++){
                    int num = Integer.parseInt(currentLine.split(" ")[j]);
                    allocation[i][j] = num;
                }
            }
            



            // 3. Use the rest of the setup file to initialize the
            // data structures

            setupFileReader.close(); // done reading the file, so close it
        }
        catch (IOException e) {
            System.err.println("Something went wrong while reading setup file "
            + args[1] + ". Stack trace follows. Exiting.");
            e.printStackTrace(System.err);
            System.err.println("Exiting.");
            return;
        }

        // 4. Check initial conditions to ensure that the system is 
        // beginning in a safe state: see "Check initial conditions"
        // in the Program 3 instructions
        
        //print available
        System.out.println("Printing available data structure");
        for(int i = 0; i < available.length; i++){
            System.out.print(available[i] + " ");
        }

        //print max
        System.out.println("\nprinting max data structure");
        for(int i = 0; i < max.length; i++){
            for(int j = 0; j < max[i].length; j++){
                System.out.print(max[i][j] + " ");
            }
            System.out.println("");
        }

        //print allocation
        System.out.println("printing allocation data structure");
        for(int i = 0; i < allocation.length; i++){
            for(int j = 0; j < allocation[i].length; j++){
                System.out.print(allocation[i][j] + " ");
            }
            System.out.println("");
        }

        //print need
        System.out.println("Printing need data structure");
        int[][] need = new int[numProcesses][numResources];
            for(int i = 0; i < need.length; i++){
                for(int j = 0; j < need[i].length; j++){
                    need[i][j] = max[i][j] - allocation[i][j];
                }
            }
            for(int i = 0; i < need.length; i++){
                for(int j = 0; j < need[i].length; j++){
                    System.out.print(need[i][j] + " ");
                }
                System.out.println("");
            }

            // int[] work = available;
            // System.out.println("printing work data structure");
            // for(int i = 0; i < work.length; i++){
            //     System.out.print(work[i] + " ");
            // }

            // boolean[] finish = new boolean[numProcesses];
            // for(int i = 0; i < finish.length; i++){
            //     finish[i] = false;
            // }

            // System.out.println("\nPrinting finish data structure");
            // for(int i = 0; i < finish.length; i++){
            //     System.out.print(finish[i] + " ");
            // }

            if(args[0].equals("manual")){
                playManual(max, allocation, available);
            }else{
                playAutomatic(max, allocation, available);
            }


    }
        

        // 5. Go into either manual or automatic mode, depending on
        // the value of args[0]; you could implement these two modes
        // as separate methods within this class, as separate classes
        // with their own main methods, or as additional code within
        // this main method.

        
    public static void playManual(int[][] m, int[][] allo, int[] avail){

        System.out.println("\nEnter commands and exit by typing end");
        Scanner sc = new Scanner(System.in);
        String userIn = sc.nextLine();
        while(!(userIn.equals("end"))){
            String command = userIn.split(" ")[0];
            int ele = Integer.parseInt(userIn.split(" ")[1]);
            int resource = Integer.parseInt(userIn.split(" ")[3]);
            int process = Integer.parseInt(userIn.split(" ")[5]);
            //calculateNeed();
            System.out.println(command + " " + ele + " " + resource + " " + process);
            boolean result = isSafe(command, ele, resource, process, m, avail, allo, m.length, m[0].length);
            if(result){
                avail[resource] -= ele;
                allo[process][resource] += ele;
            }
            userIn = sc.nextLine();
        }
        sc.close();
    }

    public static void playAutomatic(int[][] m, int[][] allo, int[] avail){
        int threads = allo.length;
        Semaphore sem = new Semaphore(1);
        for(int i = 0; i < threads; i++){
            int resource = i +1;
            //AutoThread at = new AutoThread();
            Thread obj = new Thread(new AutoThread(sem, m, allo, avail, resource));
            obj.start();
        }

    }



    public static boolean isSafe(String com, int ele, int res, int pro, int[][] m, int[] a, int[][] allo, int numP, int numR){
        int count=0;
        // if(com.equals("request")){
        //     allo[pro][res] += ele;
        // }else{
        //     allo[pro][res] -= ele;
        // }
     
    //visited array to find the already allocated process
    boolean finish[] = new boolean[numP]; 
    for (int i = 0;i < numP; i++)
    {
        finish[i] = false;
    }
         
    //work array to store the copy of available resources
    int work[] = new int[numR];    
    for (int i = 0;i < numR; i++)
    {
        work[i] = a[i];
    }
    
    int[][] need = new int[numP][numR];
            for(int i = 0; i < need.length; i++){
                for(int j = 0; j < need[i].length; j++){
                    need[i][j] = m[i][j] - allo[i][j];
                }
            }
            if(com.equals("request")){
                allo[pro][res] += ele;
            }else{
                allo[pro][res] -= ele;
            }

            for(int i = 0; i < allo.length; i++){
                for(int j = 0; j < allo[i].length; j++){
                    System.out.print(allo[i][j] + " ");
                }
                System.out.println("");
            }

            // if(a[res] - ele < 0){
            //     System.out.println("System is Unsafe");
            // }else
            // if(allo[pro][res] > m[pro][res]){
            //     System.out.println("System is Unsafe");
            // }else{
            //     System.out.println("System is Safe");
            // }

            while (count<numP)
    {
        boolean flag = false;
        for (int i = 0;i < numP; i++)
        {
            if (finish[i] == false)
             {
            int j;
            for (j = 0;j < numR; j++)
            {        
                if (need[i][j] > work[j]){
                //System.out.println("failed first break");
                break;
                }
            }
            //System.out.println(j);
            if (j == numR)
            {
                //System.out.println("Entered j if");
               count++;
               finish[i]=true;
               flag=true;
                         
                for (j = 0;j < numR; j++)
                {
                work[j] = work[j]+allo[i][j];
                }
            }
             }
        }
        if (flag == false)
        {
            break;
        }
    }
    if (count < numP || allo[pro][res] < need[pro][res])
    {
        //System.out.println("The System is UnSafe!");
        System.out.println("Process " + pro + " " + com + "s " + ele + " units of resource " + res + ": denied");
        return false;
    }
    else
    {
        //System.out.println("The given System is Safe");   
        System.out.println("Process " + pro + " " + com + "s " + ele + " units of resource " + res + ": granted");   
        return true;   
        
    }
   
    }

    }



