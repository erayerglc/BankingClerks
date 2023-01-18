import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class BankingClerks {
    //shifts  minheap
    static CustomersMinHeap shift1;
    static CustomersMinHeap shift2;
    static CustomersMinHeap shift3;
    // units minheap
    static CustomersMinHeap shift1Commercial;
    static CustomersMinHeap shift1casual;
    static CustomersMinHeap shift1loans;
    static CustomersMinHeap shift2Commercial;
    static CustomersMinHeap shift2casual;
    static CustomersMinHeap shift2loans;
    static CustomersMinHeap shift3Commercial;
    static CustomersMinHeap shift3casual;
    static CustomersMinHeap shift3loans;
    //maximum waiting time for commercial
    static int commercialMaxWaitingTime;
    //maximum waiting time for loans
    static int loansMaxWaitingTime;
    //maximum waiting time for casual
    static int casualMaxWaitingTime;
    public static void main(String[] args) throws CloneNotSupportedException {
        System.out.println("Welcome!\n");

        System.out.println("shift 1 starts at: 09:00 - 12:00");
        System.out.println("shift 2 starts at: 13:00 - 17:00");
        System.out.println("shift 3 starts at: 18:00 - 22:00");
        System.out.println("==================================================\n");
        int choice = getUserChoice();
        if (choice == 1) {
            // generate random data
            generateRandomCustomers();
            System.out.println("Generating 20 random customers for each shift...");
        } else if (choice == 2) {
            // get data from user
            getInputFromUser();
        }
        //displaying max waiting times
        System.out.println("\nMaximum waiting times for each customer type");
        System.out.println("==========================================================================\n");
        System.out.println("Maximum waiting time for commercial is: "+commercialMaxWaitingTime+" minutes");
        System.out.println("Maximum waiting time for loans is: "+loansMaxWaitingTime +" minutes");
        System.out.println("Maximum waiting time for casual is: "+casualMaxWaitingTime+" minutes");

        System.out.println("\nMinimum clerks needed for each shift and unit");
        System.out.println("==========================================================================\n");
        //displaying minimum clerks needed in each unit

        System.out.println("minimum clerks for shift 1 commercial is "+ minimumNumberOfClerks(shift1Commercial,commercialMaxWaitingTime,1) );
        System.out.println("minimum clerks for shift 1 loans is "+ minimumNumberOfClerks(shift1loans,loansMaxWaitingTime,1) );
        System.out.println("minimum clerks for shift 1 casual is "+ minimumNumberOfClerks(shift1casual,casualMaxWaitingTime,1) );
        System.out.println("minimum clerks for shift 2 commercial is "+ minimumNumberOfClerks(shift2Commercial,commercialMaxWaitingTime,2) );
        System.out.println("minimum clerks for shift 2 loans is "+ minimumNumberOfClerks(shift2loans,loansMaxWaitingTime,2) );
        System.out.println("minimum clerks for shift 2 casual is "+ minimumNumberOfClerks(shift2casual,casualMaxWaitingTime,2) );
        System.out.println("minimum clerks for shift 3 commercial is "+ minimumNumberOfClerks(shift3Commercial,commercialMaxWaitingTime,3) );
        System.out.println("minimum clerks for shift 3 loans is "+ minimumNumberOfClerks(shift3loans,loansMaxWaitingTime,3) );
        System.out.println("minimum clerks for shift 3 casual is "+ minimumNumberOfClerks(shift3casual,casualMaxWaitingTime,3) );
        System.out.println("==========================================================================\n");
        System.out.println("displaying customers");
        System.out.println("==========================================================================\n");
        //method to display customer que
        System.out.println("Arrival time (hh:mm)     Processing time for Customer (minutes)      unit (1-3)");
        display();
    }
    //method to find minimum clerks
    //it uses binary search to guess the clerks and minheap to simulate customers and clerks
    static int minimumNumberOfClerks(CustomersMinHeap minHeap, int maxWaitingTime, int shift){
        //if minheap is already empty  return 0;
        if(minHeap.isEmpty()){
            return 0;
        }

        int low = 1;
        int high = 100000;

        // holds the  answer
        int minClerks = 0;

        while(low <= high){

            //guessing clerks
            int clerks =  low + (high - low) / 2;
            //initialize clerks minheap according to shifts. eg if its shift 2, initialize all clerks at 13:00
            ClerksMinHeap clerksMinheap =initialise(clerks,shift);
            //copies minheap
            CustomersMinHeap customersMinHeap = copy(minHeap);
            //simulate method will return true if  clerks can serve the customers without exceeding time limit
            Boolean isThereEnoughClerks = simulate(clerks,clerksMinheap,customersMinHeap,maxWaitingTime);
            if(isThereEnoughClerks == true){
                // if its possible, it means we have enough clerks so we binary search low - clerks-1 interval;
                //each time save the minimum clerks to minClerks variable
                minClerks = clerks;
                high = clerks-1;

            }
            else{
                // if it's not possible, it means we have don't hava enough clerks so we binary search clerks+1 - high interval;
                low = clerks+1;
            }
        }
        // return minimum clerks we got
        return minClerks;
    }
    //Method to simulate customer and clerks
    static boolean simulate(int clerks, ClerksMinHeap clerksMinheap, CustomersMinHeap customersMinHeap, int maxWaitingTime){
        //run loop while customersMinHeap is not empty
        while (customersMinHeap.isEmpty() == false){
//            System.out.println(clerksMinheap.top().available);


            //get the top customer according to arrival time
            Customer customer = customersMinHeap.top();
            // remove that customer from minheap
            customersMinHeap.removeMin();

            //if clerks availability time is less than or equal to customr arrival time, it means customer shouldn't wait.
            if(clerksMinheap.top().availableTime.isBefore(customer.arrival) || clerksMinheap.top().availableTime.equals(customer.arrival)){
                //modify the time when clerk will become available
                LocalTime time =  customer.arrival.plusMinutes(customer.processingTime);
                Clerk clerk = new Clerk(time.toString());
                clerksMinheap.removeTop();
                clerksMinheap.add(clerk);

            }
            else{


                // if customer has to wait calculate the duration in minutes
                long minutes = Duration.between(customer.arrival, clerksMinheap.top().availableTime).toMinutes();
//                System.out.println(customer.arrival+" "+clerksMinheap.top().available+" "+minutes);

                //modify the time when clerk will become available
                LocalTime time = clerksMinheap.top().availableTime.plusMinutes(customer.processingTime);
                Clerk clerk = new Clerk(time.toString());
                clerksMinheap.removeTop();
                clerksMinheap.add(clerk);


                // if the wait time is greater than the maximum wait time, its is not possible to server all customers without exceeding limit, and we return false
                if(minutes>maxWaitingTime){
                    return false;
                }
            }
        }
        // if its possible return true;
        return true;
    }
    //initialize clerks minheap according to shifts
    static ClerksMinHeap initialise(int clerks, int shift){
        ClerksMinHeap temp = new ClerksMinHeap(clerks);

        String time = "";
        //shift 1 starts at 9
        if(shift == 1){
            time = "09:00";
        }
        //shift 2 starts at 13
        else if(shift == 2){
            time = "13:00";
        }
        //shift 3 starts at 18
        else {
            time = "18:00";
        }

        //initializing
        for (int i = 0; i < clerks; i++){
            Clerk c = new Clerk("09:00");
            temp.add(c);

        }
        return temp;
    }

    //method to generate random time
    public static String generateRandomTime(String startTime, String endTime) {
        //format
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date start = null, end = null;
        try {
            start = dateFormat.parse(startTime);
            end = dateFormat.parse(endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //generating random time

        long startTimeInMillis = start.getTime();
        long endTimeInMillis = end.getTime();
        long randomTime = startTimeInMillis + (long) (Math.random() * (endTimeInMillis - startTimeInMillis));
//        return time as string
        return dateFormat.format(new Date(randomTime));
    }
    //method to display customer ques to the console
    public static void display(){
        System.out.println("shift 1 Commercial customers queue");

        while (shift1Commercial.isEmpty() == false){
            System.out.println(shift1Commercial.top().arrival+" "+shift1Commercial.top().processingTime +" "+shift1Commercial.top().unit);
            shift1Commercial.removeMin();
        }
        System.out.println("shift 1 loan customers queue");
        while (shift1loans.isEmpty() == false){
            System.out.println(shift1loans.top().arrival+" "+shift1loans.top().processingTime +" "+shift1loans.top().unit);
            shift1loans.removeMin();
        }
        System.out.println("shift 1 casual customers queue" );
        while (shift1casual.isEmpty() == false){
            System.out.println(shift1casual.top().arrival+" "+shift1casual.top().processingTime +" "+shift1casual.top().unit);
            shift1casual.removeMin();
        }

        System.out.println("shift 2 Commercial customers queue");
        while (shift2Commercial.isEmpty() == false){
            System.out.println(shift2Commercial.top().arrival+" "+shift2Commercial.top().processingTime +" "+shift2Commercial.top().unit);
            shift2Commercial.removeMin();
        }
        System.out.println("shift 2 loan customers queue");
        while (shift2loans.isEmpty() == false){
            System.out.println(shift2loans.top().arrival+" "+shift2loans.top().processingTime +" "+shift2loans.top().unit);
            shift2loans.removeMin();
        }
        System.out.println("shift 2 casual customers queue");
        while (shift2casual.isEmpty() == false){
            System.out.println(shift2casual.top().arrival+" "+shift2casual.top().processingTime +" "+shift2casual.top().unit);
            shift2casual.removeMin();
        }

        System.out.println("shift 3 Commercial customers queue");
        while (shift3Commercial.isEmpty() == false){
            System.out.println(shift3Commercial.top().arrival+" "+shift3Commercial.top().processingTime +" "+shift3Commercial.top().unit);
            shift3Commercial.removeMin();
        }
        System.out.println("shift 3 loan customers queue");
        while (shift3loans.isEmpty() == false){
            System.out.println(shift3loans.top().arrival+" "+shift3loans.top().processingTime +" "+shift3loans.top().unit);
            shift3loans.removeMin();
        }
        System.out.println("shift 3 casual customers queue");
        while (shift3casual.isEmpty() == false){
            System.out.println(shift3casual.top().arrival+" "+shift3casual.top().processingTime +" "+shift3casual.top().unit);
            shift3casual.removeMin();
        }
    }
    // method to return user choice
    private static int getUserChoice() {
        System.out.println("1. Generate random customers");
        System.out.println("2. Enter data manually");
        System.out.print("Please select an option: ");

        Scanner input = new Scanner(System.in);
        return input.nextInt();
    }

    //method to get input from the user
    private static void getInputFromUser() {
        Scanner input = new Scanner(System.in);

        System.out.print("Please enter Maximum waiting time for commercial, casual and loans separated by space: ");
        commercialMaxWaitingTime = input.nextInt();
        loansMaxWaitingTime = input.nextInt();
        casualMaxWaitingTime = input.nextInt();

        // Shift 1
        System.out.println("\nEnter total customers for shift 1:");
        int total = input.nextInt();
        System.out.println("In each line, enter arrival time (hh:mm), processing time for customers (minutes), and unit (1 for commercial, 2 for casual, 3 for loans) separated by space:");
        shift1 = new CustomersMinHeap(total);
        for (int i = 0; i < total; i++) {
            //taking inputs
            String arrival = input.next();
            int timeNeeded = input.nextInt();
            int unit = input.nextInt();
            Customer c = new Customer(arrival, timeNeeded, unit);
            shift1.insert(c);
        }

        // Shift 2
        System.out.println("\nEnter total customers for shift 2:");
        total = input.nextInt();
        System.out.println("In each line, enter arrival time (hh:mm), processing time for customers (minutes), and unit (1 for commercial, 2 for casual, 3 for loans) separated by space:");
        shift2 = new CustomersMinHeap(total);
        for (int i = 0; i < total; i++) {
            //taking inputs
            String arrival = input.next();
            int timeNeeded = input.nextInt();
            int unit = input.nextInt();
            Customer c = new Customer(arrival, timeNeeded, unit);
            shift2.insert(c);
        }

        // Shift 3
        System.out.println("\nEnter total customers for shift 3:");
        total = input.nextInt();
        System.out.println("In each line, enter arrival time (hh:mm), processing time for customers(minutes), and unit (1 for commercial, 2 for casual, 3 for loans) separated by space:");
        shift3 = new CustomersMinHeap(total);
        for (int i = 0; i < total; i++) {
            //taking inputs
            String arrival = input.next();
            int timeNeeded = input.nextInt();
            int unit = input.nextInt();
            Customer c = new Customer(arrival, timeNeeded, unit);
            shift3.insert(c);
        }


        // Initialize minheaps for each unit and shift
        initializeMinHeaps();
    }
    //method to generate random customers
    public static void generateRandomCustomers(){
        shift1 = new CustomersMinHeap(20);
        shift2 = new CustomersMinHeap(20);
        shift3 = new CustomersMinHeap(20);
        commercialMaxWaitingTime = (int)Math.floor(Math.random() * (6 - 3 + 1) + 3);
        loansMaxWaitingTime = (int)Math.floor(Math.random() * (11 - 7 + 1) + 7);
        casualMaxWaitingTime = (int)Math.floor(Math.random() * (15 - 12 + 1) + 12);


        //generating random customers for shift 1
        for(int i = 0; i < 20; i++){
            String arrival = generateRandomTime("09:00","12:00");
            int timeNeeded = (int)Math.floor(Math.random() * (20 - 3 + 1) + 3);
            int unit = (int)Math.floor(Math.random() * (3 - 1 + 1) + 1);
            Customer c = new Customer(arrival,timeNeeded,unit);
            shift1.insert(c);
        }
        //generating random customers for shift 2
        for(int i = 0; i < 20; i++){
            String arrival = generateRandomTime("13:00","17:00");
            int timeNeeded = (int)Math.floor(Math.random() * (30 - 3 + 1) + 3);
            int unit = (int)Math.floor(Math.random() * (3 - 1 + 1) + 1);
            Customer c = new Customer(arrival,timeNeeded,unit);
            shift2.insert(c);
        }
        //generating random customers for shift 3
        for(int i = 0; i < 20; i++){
            String arrival = generateRandomTime("18:00","22:00");
            int timeNeeded = (int)Math.floor(Math.random() * (20 - 3 + 1) + 3);
            int unit = (int)Math.floor(Math.random() * (3 - 1 + 1) + 1);
            Customer c = new Customer(arrival,timeNeeded,unit);
            shift3.insert(c);
        }
        // Initialize minheaps for each unit and shift
        initializeMinHeaps();

    }

    public static void initializeMinHeaps() {
        shift1Commercial = new CustomersMinHeap(shift1.size);
        shift1casual = new CustomersMinHeap(shift1.size);
        shift1loans = new CustomersMinHeap(shift1.size);
        shift2Commercial = new CustomersMinHeap(shift2.size);
        shift2casual = new CustomersMinHeap(shift2.size);
        shift2loans = new CustomersMinHeap(shift2.size);
        shift3Commercial = new CustomersMinHeap(shift3.size);
        shift3casual = new CustomersMinHeap(shift3.size);
        shift3loans = new CustomersMinHeap(shift3.size);

        // Iterating through customers in each shift and add them to the appropriate minheap based on unit
        while (!shift1.isEmpty()) {
            Customer c = shift1.removeMin();
            if (c.unit == 1) {
                shift1Commercial.insert(c);
            } else if (c.unit == 2) {
                shift1casual.insert(c);
            } else if (c.unit == 3) {
                shift1loans.insert(c);
            }
        }
        // Iterating through customers in each shift and add them to the appropriate minheap based on unit
        while (!shift2.isEmpty()) {
            Customer c = shift2.removeMin();
            if (c.unit == 1) {
                shift2Commercial.insert(c);
            } else if (c.unit == 2) {
                shift2casual.insert(c);
            } else if (c.unit == 3) {
                shift2loans.insert(c);
            }
        }
        // Iterating through customers in each shift and add them to the appropriate minheap based on unit

        while (!shift3.isEmpty()) {
            Customer c = shift3.removeMin();
            if (c.unit == 1) {
                shift3Commercial.insert(c);
            } else if (c.unit == 2) {
                shift3casual.insert(c);
            } else if (c.unit == 3) {
                shift3loans.insert(c);
            }
        }
    }
    //method to copy minheap
    public static CustomersMinHeap copy(CustomersMinHeap customersMinHeap){
        ArrayList<Customer>temp = new ArrayList<>();

        CustomersMinHeap cMinheap = new CustomersMinHeap(customersMinHeap.size());
        //copying minheap
        while(customersMinHeap.isEmpty() == false){
            Customer c = customersMinHeap.top();
            customersMinHeap.removeMin();
            temp.add(c);
            cMinheap.insert(c);

        }

        //restoring previous minheap
        for(int i = 0; i < temp.size(); i++){
            customersMinHeap.insert(temp.get(i));
        }

        return cMinheap;

    }




}
