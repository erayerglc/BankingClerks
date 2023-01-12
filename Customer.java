public class customer {

    int  arrival;
    int timeNeeded;

    public customer(int arrival, int timeNeeded) {
        this.arrival = arrival;
        this.timeNeeded = timeNeeded;
    }

    public int getArrival() {
        return arrival;
    }

    public int getTimeNeeded() {
        return timeNeeded;
    }

    public void setArrival(int arrival) {
        this.arrival = arrival;
    }

    public void setTimeNeeded(int timeNeeded) {
        this.timeNeeded = timeNeeded;
    }
}
