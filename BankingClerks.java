import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class BankingClerks {
    public static int maxWaitingTime = 5;
    public static void main(String[] args) throws CloneNotSupportedException {

        Customer c = new Customer("08:05", 2);
        Customer c2 = new Customer("08:08", 3);
        Customer c3 = new Customer("08:01", 6);
        Customer c4 = new Customer("08:02", 4);

        CustomersMinHeap temp = new CustomersMinHeap(4);

        temp.insert(c);
        temp.insert(c2);
        temp.insert(c3);
        temp.insert(c4);






//        System.out.println(ans);




    }

    static int minimumClerks( CustomersMinHeap temp, int maximumWaitingTime) throws CloneNotSupportedException {
        int low = 1;
        int high = 4;
        int ans = 0;

        while(low < high){

            int clerks = (low+high)/2;
            ClerksMinHeap clerksMinheap =initialise(clerks);
            CustomersMinHeap customersMinHeap = temp.clone();

            Boolean isPossible = simulate(clerks,clerksMinheap,customersMinHeap);
//            System.out.println(clerks+" "+isPossible);


            if(isPossible == true){
                ans = clerks;
                high = clerks-1;

            }
            else{
                low = clerks+1;
            }


        }
        return ans;
    }

    static boolean simulate(int clerks, ClerksMinHeap clerksMinheap, CustomersMinHeap customersMinHeap){
        int time = 0;

        while (customersMinHeap.isEmpty() == false){
            if(time>maxWaitingTime){
                return false;
            }
            Customer c = customersMinHeap.top();
            customersMinHeap.removeMin();

            if(clerksMinheap.top().available.isBefore(c.arrival)){
                clerksMinheap.top().setAvailable(c.arrival.plusMinutes(c.timeNeeded));
            }
            else{

                long minutes = Duration.between(c.arrival, clerksMinheap.top().available).toMinutes();
                time+=(int)minutes;


            }
           

        }
        System.out.println();
        return true;

    }
    public static String generateRandomTime(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date start = null, end = null;
        try {
            start = sdf.parse(startTime);
            end = sdf.parse(endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

        long startTimeInMillis = start.getTime();
        long endTimeInMillis = end.getTime();
        long randomTime = startTimeInMillis + (long) (Math.random() * (endTimeInMillis - startTimeInMillis));

        return sdf.format(new Date(randomTime));
    }


    static ClerksMinHeap initialise(int clerks){
        ClerksMinHeap temp = new ClerksMinHeap(clerks);


        for (int i = 0; i < clerks; i++){
            Clerk c = new Clerk("09:00");
            temp.insert(c);

        }
        return temp;

    }



}
