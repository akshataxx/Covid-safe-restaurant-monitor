/*
 Class Name: Serve
 Description: Serve class creates serve thread and passes it to restaurant class
 Precondition: Main class and restaurant classes are defined
 Postcondition: Serve thread holding serving data is created
 */
public class Serve extends Thread {
    Restaurent restaurent;

    Serve(Restaurent restaurent) {
        this.restaurent = restaurent;
    }
    //called from P3 class
    @Override
    public void run() {
        try {
            restaurent.work();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.run();
    }
}