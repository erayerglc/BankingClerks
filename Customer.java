import java.time.LocalTime;

public class Customer {
    // time
    LocalTime arrival;
    //how long does it take to process a customer
    int processingTime;
    //unit the customer intend to go
    public int unit;

    //constructor
    public Customer(String arrival, int processingTime, int unit) {
        this.arrival = LocalTime.parse(arrival);
        this.processingTime = processingTime;
        this.unit = unit;
    }


    //getters and setters
    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }
    public LocalTime getArrival() {
        return arrival;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public void setArrival(LocalTime arrival) {
        this.arrival = arrival;
    }

    public void setProcessingTime(int processingTime) {
        this.processingTime = processingTime;
    }
}
