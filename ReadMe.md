CENG303-Project

group 6
18050111027 Harun Yahya Öztürk
19050141027 Justino Macosso
18050111018 Eray Ergüleç
20050141028 Mohamed Mohamud Ali

# Banking Clerks

This program simulates a banking system with multiple shifts and units (commercial, casual, loans). The program allows the user to either generate random customers or input customer data manually, and then calculates the minimum number of clerks needed for each unit and shift, as well as the maximum waiting time for each type of customer.

## Algorithm
The program uses a combination of min-heap data structures and binary search to calculate the minimum number of clerks needed for each unit and shift, according to a given maximum waiting time for each type of customer.
The program first creates a min-heap for each unit and shift to store the customers' data.
The program then prompts the user to either input customer data manually or generate random customer data.
After the customer data is loaded, the program uses binary search to guess the number of clerks needed for each unit and shift.
For each guess, the program simulates the work of clerks using the min-heap data structure, and checks whether the  predefined maximum waiting time for each type of customer is less than the maximum waiting time.
If the predefined maximum waiting time is less than the maximum waiting time, the program increases the number of clerks and continues the simulation with the new number of clerks. If the predefined maximum waiting time is greater than the  maximum waiting time, the program decreases the number of clerks and continues the simulation.
The program repeats last two steps until it finds the minimum number of clerks needed for each unit and shift.
The time Complexity is **O(nlogn)**
## Code Structure
The program consists of the following main classes:
1.BankingClerks: This is the main class of the program, it contains the main method and calls the other classes and methods.
2.Customer: This class represents a customer and contains the data of each customer such as arrival time, time needed and unit type.
3.CustomersMinHeap: This class is a min-heap data structure that stores the customer data, it is used to simulate the customers.
4.Clerk: This class contains the methods that are used to input the customer data and generate random customer data.
5.clerksMinheap: is a minheap data structure. It's used to simulate clerks.

## How to run

java -jar (path)

github link
https://github.com/erayerglc/BankingClerks.git


