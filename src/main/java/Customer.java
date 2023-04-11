/*
 Class Name: Customer
 Description: Customer class creates customer thread and passes customer data
 Precondition: Main class and restaurant classes are defined
 Postcondition: Customer thread holding customer data is created
 */

public class Customer extends Thread {
    Restaurent restaurent;                      //object of restaurant class
    int arrivalTime, eatingTime,seats,leaves;   //variables to hold customer details
    String custNum;

    //constructor sets the customer data
    Customer(Restaurent restaurent,int _arrivalTime, int _eatingTime, String _custNum) {
        this.restaurent = restaurent;
        this.arrivalTime=_arrivalTime;
        this.seats=_arrivalTime;
        this.eatingTime=_eatingTime;
        this.leaves=_arrivalTime+_eatingTime;
        this.custNum=_custNum;
    }

    /* called from P3 class */
    @Override
    public void run() {
        try {
            Thread.sleep(200);
            restaurent.enterShop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.run();

    }

    // this is called by the serving thread to continue execution after
    // this thread was waiting in the queue
    void sitInRestaurentChair() throws InterruptedException {
        restaurent.getServed(this);
    }
}