# Covid-safe-restaurant-monitor
Covid safe restaurant using monitor
Class Name: P3 
 Description: P3 class creates a restaurent and serves the customer as per the restaurent rules. It takes the customer data  from the input file provided by the user into a queue. Based on the number of records in the queue. Arrays are created to read/store
 the value of arrival time, eating time and customer number
 Precondition: Input file with customer data is provided
 Postcondition: Calls instance of restaurent class, instance of customer class and instance of serve class
 
 Class Name: Customer 
 Description: Customer class creats customer thread and passes customer data
 Precondition: Main class and restaurent classes are defined
 Postcondition: Customer thread holding customer data is created 
 
 Class: Restaurent 
 Description: uses monitors to pass the customer thread and serve thread to cater to restaurent business rules
 Precondition: main class is defined
 Postcondition: calls the customer and serve thread based on the restaurent rules 
 
 Class Name: Serve 
 Description: Serve class creats serve thread and passes it to restaurent class
 Precondition: Main class and restaurent classes are defined
 Postcondition: Serve thread holding serving data is created 
