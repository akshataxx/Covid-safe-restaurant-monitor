/*
 Class: Restaurant
 Description: uses monitors to pass the customer thread and serve thread to cater to restaurant business rules
 Precondition: main class is defined
 Postcondition: calls the customer and serve thread based on the restaurant rules
 */
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

class Restaurent {
    private ArrayBlockingQueue<Customer> numOfFreeChair;
    private int custAllowed;                            //number of customers allowed
    private int cleaningTime,custBatch;                 //cleaning time and customer batch number
    private boolean nextBatch;                          //checks if next batch of customers can be allowed to come in
    private static int iD=1;                            //counts the number of customers entering the restaurent
    private static int temp=0;                          //temp variable to store the leaving time
    private static int lastLeavingTime=0;               //variable to store the exit time of the customer leaving at the last in a batch

    //constructor
    Restaurent(int freeChair) {
        numOfFreeChair = new ArrayBlockingQueue<Customer>(freeChair);//tracks number of free chairs
        custAllowed = freeChair;    //number of customers allowed in a batch
        cleaningTime=0;             //time to clean the restaurant after the last customer from the batch has left
        custBatch=0;                //number of customer batch
    }

    // as customers enter the restaurant, first 5 seat themselves rest will queue up until cleaning is done
    // customers can only enter one by one in this implementation in FIFO based on the arrival time
    void enterShop() throws InterruptedException {
        Customer customer = (Customer) Thread.currentThread();
        if (numOfFreeChair.remainingCapacity() > 0) {
            if(nextBatch){                                      //checks if the next batch is in
                lastLeavingTime=temp;
                nextBatch=false;
                custBatch=custBatch+5;
            }
            if(iD>custBatch){                                   //checks if the customer is from this batch
                if(customer.arrivalTime < lastLeavingTime){   //checks if the customer was waiting in the queue
                    customer.seats=lastLeavingTime+cleaningTime;
                    customer.leaves= customer.eatingTime + customer.seats;
                }
            }
            if (temp < customer.leaves)
                temp=customer.leaves;
            numOfFreeChair.offer(customer);
            System.out.println(customer.custNum + "\t\t"+"  " + customer.arrivalTime +"\t\t"+"  "  + customer.seats+"       " + customer.leaves);
        } else {
            System.out.println("no more free seats in the restaurent, " + customer.custNum + " waits.");
        }
        // when the customer arrives he waits for restaurant to allow him in
        // if the restaurant is serving the first batch customer waits in line - nothing happens here
        synchronized (this) {
            notifyAll();
        }
    }

    // this method is called by the serve thread, to serve customers as they arrive
    // it keeps checking for the arriving customers, if there are none it waits
    synchronized void work() throws InterruptedException {
        System.out.println("Customer" + "\t" + "Arrival Time" + "\t" + "Seats" + "\t" + " Leaves");
        // this loop keeps checking if there are new customers arriving, so that they can be served asap as per the restaurent rules
        // if there are no customers the restaurant waits for the new customers.

        while (true) {
            while (numOfFreeChair.size() == 0) {
                //System.out.println("new customer is waiting outside restaurant for..."); debug code
                wait();
            }
            serveCustomers();
        }
    }

    // customers waiting in the queue
    private void serveCustomers() throws InterruptedException {
        while (!numOfFreeChair.isEmpty()) {
            // Restaurant allows customer in and serves him
            // - removes him from the waiting queue
            numOfFreeChair.peek().sitInRestaurentChair();
        }
    }
    // this method is called by a customer that was 'called' by the serve
    synchronized void getServed(Customer customer) throws InterruptedException {
        // System.out.println("The restaurant is serving " + customer.custnum + "..."); //--Debug code
        iD++;               //customer counter
        if (iD>custAllowed){//if the number of customer > number of customer allowed in the restaurant then restaurant rules are trigger
            for(int i=0;i<5;i++)
                numOfFreeChair.poll();  //restaurant is being cleaned up - removes the record of previous customer batch
            cleaningTime=5;              //cleaning time after the previous batch of customers have left
            nextBatch=true;              //variable to confirm nextbatch is allowed in the restaurant post clean-up
            custAllowed=custAllowed+5;   //variable to check the number of customer inside the restaurant is not more than 5 at any time
        }
        eating();
        // System.out.println(customer.custnum + "is done");//--Debug code
    }

    // Customer is enjoying the restaurant food
    private void eating() throws InterruptedException {
        Thread.sleep(1000);
    }
}