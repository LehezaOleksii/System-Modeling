package oleksii.leheza.kpi.ms.task2;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Cashier c1 = new Cashier("Cashier 1", 2, 3);
        Cashier c2 = new Cashier("Cashier 2", 1, 3);
        ClientGenerator generator = new ClientGenerator("Generator", 0.5, 1);

        List<Element> elements = new ArrayList<>();
        elements.add(c1);
        elements.add(c2);
        elements.add(generator);

        Bank bank = new Bank(elements);
        bank.simulate(200);

        printStatistic(bank.getRequestNum(), bank.getServedClients(), bank.getFailedRequests(), bank.getSwitchedClients());
    }

    public static void printStatistic(int requests, int servedClients, int failed, int switchedClients) {
        System.out.println("----Statistic----\n" +
                "All clients :" + requests + "\n" +
                "Served clients: " + servedClients + "\n" +
                "Switched clients: " + switchedClients + "\n" +
                "Failed requests: " + failed + "\n");
    }
}
