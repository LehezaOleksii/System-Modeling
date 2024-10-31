package oleksii.leheza.kpi.ms.task2;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;

@Getter
@Setter
public class Bank {

    private double currentTime;
    private double nextTime;
    private List<Element> elements;
    private int requestNum;
    private int servedClients;
    private int failedRequests;
    private int switchedClients;
    private List<Double> times = new ArrayList<>();
    private List<Integer> clientsNum = new ArrayList<>();
    private double lastDeparture;
    private List<Double> departureTimes = new ArrayList<>();
    private double clientTimeInBank;

    public Bank(List<Element> elements) {
        this.elements = elements;
    }

    public void simulate(double modelTime) {
        Element currentElement = null;
        Cashier firstCashier = (Cashier) elements.get(0);
        Cashier secondCashier = (Cashier) elements.get(1);
        trackClientsInBank(0.1);
        while (currentTime < modelTime) {
            nextTime = Double.MAX_VALUE;
            for (Element e : elements) {
                if (e.getNextEventTime() < nextTime) {
                    nextTime = e.getNextEventTime();
                    currentElement = e;
                }
            }
            double differenceBetweenActions = nextTime - currentTime;
            currentTime = nextTime;
            for (Element e : elements) {
                e.setCurrentTime(currentTime);
            }

            if (currentElement instanceof ClientGenerator) {
                Client client = ((ClientGenerator) currentElement).generateClient(currentTime);
                requestNum += 1;
                assignRequestToProcess(client);
            } else if (currentElement instanceof Cashier cashier) {
                if (cashier.getClientQueue().peek() != null) {
                    clientTimeInBank += currentTime - cashier.getClientQueue().peek().getArrivalTime();
                }
                cashier.releaseRequest();
                departureTimes.add(currentTime - lastDeparture);
                lastDeparture = currentTime;
            }
            optimizeCashierQueues(firstCashier, secondCashier);
            trackClientsInBank(differenceBetweenActions);
        }
    }

    private void assignRequestToProcess(Client client) {
        boolean assigned = false;

        List<Cashier> sortedCashiers = elements.stream()
                .filter(e -> e instanceof Cashier)
                .map(e -> (Cashier) e)
                .sorted(Comparator.comparingInt(Cashier::getPriority).reversed())
                .toList();

        Cashier firstCashier = sortedCashiers.get(0);
        Cashier secondCashier = sortedCashiers.get(1);

        if (firstCashier.getClientQueue().size() == secondCashier.getClientQueue().size()) {
            if (!firstCashier.isBusy() || firstCashier.getClientQueue().size() < firstCashier.getMaxQueue()) {
                System.out.println("Request ID " + client.getId() + " assigned to " + firstCashier.getName());
                firstCashier.processRequest(client, currentTime);
                assigned = true;
                servedClients += 1;
            }
        } else {
            // Choose the cashier with the shorter queue
            Cashier preferredCashier = (firstCashier.getClientQueue().size() < secondCashier.getClientQueue().size())
                    ? firstCashier
                    : secondCashier;

            if (!preferredCashier.isBusy() || preferredCashier.getClientQueue().size() < preferredCashier.getMaxQueue()) {
                System.out.println("Request ID " + client.getId() + " assigned to " + preferredCashier.getName());
                preferredCashier.processRequest(client, currentTime);
                assigned = true;
                servedClients += 1;
            }
        }

        if (!assigned) {
            System.out.println("Request ID " + client.getId() + " rejected: All processes busy or queues full");
            failedRequests += 1;
        }
    }

    private void optimizeCashierQueues(Cashier firstCashier, Cashier secondCashier) {
        Deque<Client> firstQueue = firstCashier.getClientQueue();
        Deque<Client> secondQueue = secondCashier.getClientQueue();
        if (firstQueue.size() >= secondQueue.size() + 2) {
            Client client;
            for (int i = secondQueue.size(); firstQueue.size() - i >= 2; ) {
                client = firstQueue.peek();
                switchRequest(client, firstQueue, secondQueue);
            }
        }
        if (firstQueue.size() >= secondQueue.size() + 2) {
            Client client;
            for (int i = firstQueue.size(); secondQueue.size() - i >= 2; ) {
                client = secondQueue.peek();
                switchRequest(client, secondQueue, firstQueue);
            }
        }
    }

    private void switchRequest(Client client, Deque<Client> fromQueue, Deque<Client> toQueue) {
        if (client == null) {
            return;
        }
        client = fromQueue.pollLast();
        toQueue.add(client);
        switchedClients += 1;
        System.out.println("Switch client id: " + client.getId());
    }

    private void trackClientsInBank(double time) {
        int clients = 0;
        for (Element e : elements) {
            if (e instanceof Cashier cashier) {
                clients += cashier.getClientQueue().size();
                if (cashier.isBusy()) {
                    clients += 1;
                }
                clientsNum.add(clients);
                times.add(time);
            }
        }
    }
}
