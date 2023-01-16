import java.time.Duration;

public class BankingClerks {
    public static int maxWaitingTime = 5;
    public static void main(String[] args) throws CloneNotSupportedException {

        Customer c = new Customer("09:05", 5);
        Customer c2 = new Customer("09:08", 8);
        Customer c3 = new Customer("09:01", 6);
        Customer c4 = new Customer("09:02", 4);

        CustomersMinHeap temp = new CustomersMinHeap(4);

        temp.insert(c);
        temp.insert(c2);
        temp.insert(c3);
        temp.insert(c4);





        int low = 1;
        int high = 4;
        int ans = 0;

        while(low < high){

            int clerks = (low+high)/2;
            ClerksMinHeap clerksMinheap =initialise(clerks);
            CustomersMinHeap customersMinHeap = temp.clone();

            Boolean isPossible = simulate(clerks,clerksMinheap,customersMinHeap);
            System.out.println(clerks+" "+isPossible);


            if(isPossible == true){
                ans = clerks;
                high = clerks-1;

            }
            else{
                low = clerks+1;
            }









        }
        System.out.println(low+" "+high);




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
