public class CustomersMinHeap implements Cloneable {
    private Customer[] arr;
    private int maximumSize;
    public int size;


    public CustomersMinHeap(int capacity) {
        arr = new Customer[capacity];
        size = 0;
    }

    //method to check if minheap is empty
    public boolean isEmpty() {
        return maximumSize == 0;
    }
    // method to return top or min
    public Customer top() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        return arr[0];
    }
    //method to insert customer into minheap
    public void insert(Customer customer) {
        size++;
        if (maximumSize == arr.length) {
            throw new IllegalStateException("Heap is full");
        }

        //add to the array heap
        arr[maximumSize] = customer;
        int current = maximumSize;
        maximumSize++;

        // iterating and swapping clerks
        while (current != 0) {
            int parent = parent(current);
            if (arr[current].getArrival().isBefore(arr[parent].getArrival())) {
                swap(current, parent);
                current = parent;
            } else if (arr[current].getArrival().equals(arr[parent].getArrival()) && arr[current].getProcessingTime() < arr[parent].getProcessingTime()) {
                swap(current, parent);
                current = parent;
            } else {
                break;
            }
        }
    }


    //method to remove minimum
    public Customer removeMin() {
        size--;
        Customer min = top();
        arr[0] = arr[maximumSize - 1];
        //update size
        maximumSize--;
        heapify(0);
        return min;
    }
    //method to fix minheap
    private void heapify(int index) {
        int left = left(index);
        int right = right(index);
        int smallest = index;
        //check for smallest
        if (left < maximumSize && arr[left].getArrival().isBefore(arr[smallest].getArrival())) {
            smallest = left;
        } else if (left < maximumSize && arr[left].getArrival().toString().equals(arr[smallest].getArrival()) && arr[left].getProcessingTime() < arr[smallest].getProcessingTime()) {
            smallest = left;
        }

        if (right < maximumSize && arr[right].getArrival().isBefore(arr[smallest].getArrival())) {
            smallest = right;
        } else if (right < maximumSize && arr[right].getArrival().equals(arr[smallest].getArrival()) && arr[right].getProcessingTime() < arr[smallest].getProcessingTime()) {
            smallest = right;
        }

        //swap smaller with index and fix the minheap
        if (smallest != index) {
            swap(index, smallest);
            heapify(smallest);
        }
    }
    //method to find parent
    private int parent(int index) {
        return (index - 1) / 2;
    }
    //method to find left child
    private int left(int index) {
        return 2 * index + 1;
    }
    //method to find right child
    private int right(int index) {
        return 2 * index + 2;
    }
    //method for swapping elements
    private void swap(int i, int j) {
        Customer temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    public CustomersMinHeap clone() throws CloneNotSupportedException {
        return (CustomersMinHeap) super.clone();
    }
    //method to return size
    public int size(){
        return size;
    }


}
