package oleksii.leheza.kpi.ms.task2;

import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        Cashier cashier1 = new Cashier("Cashier 1", 3);
        Cashier cashier2 = new Cashier("Cashier 2", 3);
        RequestGenerator generator = new RequestGenerator("Request Generator", 0.5); // Mean arrival time 0.5

        List<Cashier> cashiers = new ArrayList<>();
        cashiers.add(cashier1);
        cashiers.add(cashier2);

        Server server = new Server(cashiers, generator);
        server.simulate(200); // Run the simulation for 200 time units
    }
}