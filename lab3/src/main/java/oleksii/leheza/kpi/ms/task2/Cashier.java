package oleksii.leheza.kpi.ms.task2;

import lombok.Getter;
import lombok.Setter;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
public class Cashier extends Element {

    private int maxQueue;
    private Deque<Client> clientQueue;
    private int priority;
    private boolean isBusy;
    private double totalBusyTime;
    private Random random = new Random();
    private Client currentClient;
    private List<Double> timeOfQueue = new LinkedList<>();
    private List<Integer> clientsNum = new LinkedList<>();

    public Cashier(String name, int priority, int maxQueue) {
        super(name);
        this.maxQueue = maxQueue;
        this.priority = priority;
        this.clientQueue = new LinkedList<>();
        this.isBusy = false;
    }

    public void processRequest(Client client, double currentTime) {
        if (isBusy) {
            if (clientQueue.size() < maxQueue) {
                clientQueue.add(client);
                System.out.println(getProcessInfo() + " is busy; Queue size: " + clientQueue.size());
            } else {
                System.out.println(getProcessInfo() + " is busy; Queue size: " + clientQueue.size() + " FAILURE");
            }
        } else {
            isBusy = true;
            currentClient = client;
            setNextEventTime(getCurrentTime() + generateServiceTime());
            System.out.println(getProcessInfo() + " is processing Request ID: " + client.getId() + "; Queue size: " + clientQueue.size());
        }
        timeOfQueue.add(currentTime);
        clientsNum.add(clientQueue.size());
    }

    public void releaseRequest() {
        quantity++;
        isBusy = false;
        if (!clientQueue.isEmpty()) {
            Client nextClient = clientQueue.poll();
            double busyTime = generateServiceTime();
            setNextEventTime(getCurrentTime() + busyTime);
            totalBusyTime += busyTime;
            isBusy = true;
            System.out.println(getProcessInfo() + " is processing next Request ID: " + nextClient.getId() + "; Queue size: " + clientQueue.size());
        } else {
            setNextEventTime(Double.MAX_VALUE);
            System.out.println(getProcessInfo() + " is free; Queue size: " + clientQueue.size());
        }
    }

    private double generateServiceTime() {
        double mean = 1.0;
        double stdDev = 0.3;
        return mean + random.nextGaussian() * stdDev;
    }

//    private double generateExponential() {
//        double lambda = 1 / 0.3;
//        Random random = new Random();
//        return -Math.log(1 - random.nextDouble()) / lambda;
//    }

    private String getProcessInfo() {
        return getName() + " id :" + getId();
    }
}
