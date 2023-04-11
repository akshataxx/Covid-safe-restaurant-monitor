/*
 Class Name: P3
 Description: P3 class creates a restaurant and serves the customer as per the restaurant rules. It takes the customer data  from the input file provided by the user into a queue. Based on the number of records in the queue. Arrays are created to read/store
 the value of arrival time, eating time and customer number
 Precondition: Input file with customer data is provided
 Postcondition: Calls instance of restaurant class, instance of customer class and instance of serve class
 */
import java.util.*;
import java.io.*;
public class P3 {
    private static final int CHAIRS = 5;		   //max number of customers allowed to be seated inside the restaurent

    public static void main(String[] args) throws InterruptedException {
        Queue<String> queue = new LinkedList<>(); //queue to store the data read from the input file
        String myStr;                             //variable to store the line of data
        String inFile=" " ;
        int i=0;
        System.out.print('\u000C');
        if (args.length==0) {                     // Checks if a filename has been provided
            System.out.println("no arguments were given");
        }
        else {
            inFile=args[0];
        }

        try{FileReader file= new FileReader(inFile);
            BufferedReader br = new BufferedReader(file);  //read the file

            while((myStr = br.readLine()) != null){        //read the file line by line
                queue.add(myStr); i++;                     //transfer the data from the file into the queue
            }
        } catch (IOException e){
            System.out.println(" Error opening the file " + inFile);//file exception handling
            System.exit(0);}

        Restaurent restaurent = new Restaurent(CHAIRS);  //Creates a new restaurent

        Serve serve = new Serve(restaurent);       		 //Creates an instance of serve class
        serve.start();                              	 //runs serve thread

        Customer customer;                          	//Creates an instance of Customer class
        int at[]=new int[i];                        	//arrival time array of the size of the records in the queue or input file
        int et[]=new int[i];                        	//eating time array of the size of the records in the queue or input file
        String cs[]=new String[i];                  	//customer number array of the size of the records in the queue or input file
        for(int n=0;n<(i-1);n++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String head = queue.peek();           	 //transfers the first record form the queue in the string head
            String[] myData= head.split("\\s+");  	 //extracts values of arrival time, eating time and custnum from the string
            at[n]= (Integer.valueOf(myData[0]));
            cs[n]= myData[1];
            et[n]= (Integer.valueOf(myData[2]));
            queue.remove();
            customer = new Customer(restaurent,at[n],et[n],cs[n]);//creates customer with details
            customer.start();                      				  //starts the customer thread
        }

    }
}
