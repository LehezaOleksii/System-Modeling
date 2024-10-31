package oleksii.leheza.kpi.ms.task2;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        double meanTaskTime = 1;
        double clientArrivalFrequency = 0.5;
        double modelTime = 200;

        Cashier c1 = new Cashier("Cashier 1", 2, 3);
        Cashier c2 = new Cashier("Cashier 2", 1, 3);
        ClientGenerator generator = new ClientGenerator("Generator", clientArrivalFrequency, meanTaskTime);

        Client client1 = new Client(0.0, generator.generateTime(meanTaskTime));
        Client client2 = new Client(0.0, generator.generateTime(meanTaskTime));
        Client client3 = new Client(0.0, generator.generateTime(meanTaskTime));
        Client client4 = new Client(0.0, generator.generateTime(meanTaskTime));

        c1.getClientQueue().add(client1);
        c1.getClientQueue().add(client2);
        c2.getClientQueue().add(client3);
        c2.getClientQueue().add(client4);

        generator.setNextEventTime(0.1);

        List<Element> elements = new ArrayList<>();
        elements.add(c1);
        elements.add(c2);
        elements.add(generator);

        Bank bank = new Bank(elements);
        bank.simulate(modelTime);

        printStatistic(bank.getRequestNum(), bank.getServedClients(), bank.getFailedRequests(), bank.getSwitchedClients());
        printResult(c1, c2, modelTime, bank);
    }

    public static void printStatistic(int requests, int servedClients, int failed, int switchedClients) {
        System.out.println("----Statistic----\n" +
                "All clients: " + requests + "\n" +
                "Served clients: " + servedClients + "\n" +
                "Switched clients: " + switchedClients + "\n" +
                "Failed requests: " + failed + "\n");
    }

    public static void printResult(Cashier firstCashier, Cashier secondCashier, double modelTime, Bank bank) {

        double clientSum = 0;
        double timeSum = bank.getTimes().stream().mapToDouble(Double::doubleValue).sum();
        for (int i = 0; i < bank.getClientsNum().size(); i++) {
            clientSum = clientSum + bank.getClientsNum().get(i) * bank.getTimes().get(i);
        }

        double departureSumTime = 0;
        List<Double> departureList = bank.getDepartureTimes();
        for (int i = 0; i < departureList.size(); i++) {
            departureSumTime += departureList.get(i);
        }

        double firstCashierQueueLength = 0;
        double firstCashierQueueTime = firstCashier.getTimeOfQueue().stream().mapToDouble(Double::doubleValue).sum();
        for (int i = 0; i < firstCashier.getClientsNum().size(); i++) {
            firstCashierQueueLength = firstCashierQueueLength + firstCashier.getClientsNum().get(i) * firstCashier.getTimeOfQueue().get(i);
        }

        double secondCashierQueueLength = 0;
        double secondCashierQueueTime = secondCashier.getTimeOfQueue().stream().mapToDouble(Double::doubleValue).sum();
        for (int i = 0; i < secondCashier.getClientsNum().size(); i++) {
            secondCashierQueueLength = secondCashierQueueLength + secondCashier.getClientsNum().get(i) * secondCashier.getTimeOfQueue().get(i);
        }

        double averageFirstCashierLoad = (firstCashier.getTotalBusyTime() / modelTime) * 100;
        double averageSecondCashierLoad = (secondCashier.getTotalBusyTime() / modelTime) * 100;
        double averageClientsInBank = (timeSum == 0) ? 0 : clientSum / timeSum;
        double averageTimeBetweenDeparture = departureSumTime / departureList.size();
        double averageClientTimeInBank = bank.getClientTimeInBank() / (double) bank.getServedClients();
        double averageFirstCashierQueueLength = firstCashierQueueLength / firstCashierQueueTime;
        double averageSecondCashierQueueLength = secondCashierQueueLength / secondCashierQueueTime;
        double rejectionRate = ((double) bank.getFailedRequests() / bank.getRequestNum()) * 100;
        int switchedLaneCount = bank.getSwitchedClients();
        System.out.println("-------Result-------\n" +
                "Average first cashier load: " + averageFirstCashierLoad + "%\n" +
                "Average second cashier load: " + averageSecondCashierLoad + "%\n" +
                "Average clients in bank: " + averageClientsInBank + "\n" +
                "Average time between departure: " + averageTimeBetweenDeparture + "\n" +
                "Average time client stays in the bank: " + averageClientTimeInBank + "\n" +
                "Average first cashier queue length: " + averageFirstCashierQueueLength + "\n" +
                "Average second cashier queue length: " + averageSecondCashierQueueLength + "\n" +
                "Rejection rate: " + rejectionRate + "%\n" +
                "Switched lanes count: " + switchedLaneCount + "\n" +
                "\n");
    }
}
