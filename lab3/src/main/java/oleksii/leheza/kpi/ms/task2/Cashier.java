package oleksii.leheza.kpi.ms.task2;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Getter
@Setter
class Cashier extends Element {
    private Queue<Customer> queue;
    private boolean isBusy;
    private double serviceTimeRemaining;
    private int maxQueueSize;
    private List<Customer> servicedCustomers;

    public Cashier(String name, int maxQueueSize) {
        super(name);
        this.queue = new LinkedList<>();
        this.isBusy = false;
        this.maxQueueSize = maxQueueSize;
        this.servicedCustomers = new ArrayList<>();
    }

    public void addCustomer(Customer customer) {
        if (queue.size() < maxQueueSize) {
            queue.add(customer);
            System.out.println(getName() + " added Customer ID: " + customer.getId());
        } else {
            System.out.println(getName() + " queue is full. Customer ID: " + customer.getId() + " is rejected.");
        }
    }

    public void serveCustomer(double currentTime) {
        if (!queue.isEmpty()) {
            Customer customer = queue.poll();
            customer.setStartServiceTime(currentTime);
            serviceTimeRemaining = customer.getServiceTime();
            isBusy = true;
            servicedCustomers.add(customer);
            System.out.println(getName() + " started serving Customer ID: " + customer.getId());
        }
    }

    public void update(double deltaTime) {
        if (isBusy) {
            serviceTimeRemaining -= deltaTime;
            if (serviceTimeRemaining <= 0) {
                isBusy = false;
                System.out.println(getName() + " finished serving a customer.");
            }
        }
    }

    public boolean isBusy() {
        return isBusy;
    }

    public int getQueueSize() {
        return queue.size();
    }

    public List<Customer> getServicedCustomers() {
        return servicedCustomers;
    }
}
