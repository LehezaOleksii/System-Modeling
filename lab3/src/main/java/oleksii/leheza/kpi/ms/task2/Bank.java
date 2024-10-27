package oleksii.leheza.kpi.ms.task2;

import lombok.Getter;
import lombok.Setter;

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

    public Bank(List<Element> elements) {
        this.elements = elements;
    }

    public void simulate(double modelTime) {
        Element currentElement = null;
        Cashier firstCashier = (Cashier) elements.get(0);
        Cashier secondCashier = (Cashier) elements.get(1);
        while (currentTime < modelTime) {
            nextTime = Double.MAX_VALUE;
            for (Element e : elements) {
                if (e.getNextEventTime() < nextTime) {
                    nextTime = e.getNextEventTime();
                    currentElement = e;
                }
            }
            currentTime = nextTime;
            for (Element e : elements) {
                e.setCurrentTime(currentTime);
            }

            if (currentElement instanceof ClientGenerator) {
                Client client = ((ClientGenerator) currentElement).generateRequest();
                requestNum += 1;
                assignRequestToProcess(client);
            } else if (currentElement instanceof Cashier) {
                ((Cashier) currentElement).releaseRequest();
            }
            optimizeCashierQueues(firstCashier, secondCashier);
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
                firstCashier.processRequest(client);
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
                preferredCashier.processRequest(client);
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
}
