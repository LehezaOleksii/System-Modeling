package oleksii.leheza.kpi.ms.task2;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
class Server {
    private double currentTime;
    private List<Cashier> cashiers;
    private RequestGenerator generator;
    private int totalRejectedCustomers;
    private int totalServedCustomers;

    public Server(List<Cashier> cashiers, RequestGenerator generator) {
        this.cashiers = cashiers;
        this.generator = generator;
        this.totalRejectedCustomers = 0;
        this.totalServedCustomers = 0;
    }

    public void simulate(double simulationTime) {
        currentTime = 0;

        while (currentTime < simulationTime) {
            Customer customer = generator.generateCustomer(currentTime);
            assignCustomer(customer);
            updateCashiers();
            currentTime += 0.1; // Simulation step, can be adjusted
        }

        printStatistics();
    }

    private void assignCustomer(Customer customer) {
        if (isBankFull()) {
            totalRejectedCustomers++;
            System.out.println("Customer ID " + customer.getId() + " rejected due to full bank.");
            return;
        }

        Cashier selectedCashier = selectCashier();
        selectedCashier.addCustomer(customer);
    }

    private boolean isBankFull() {
        int totalInBank = 0;
        for (Cashier cashier : cashiers) {
            totalInBank += cashier.getQueueSize();
            if (cashier.isBusy()) totalInBank++;
        }
        return totalInBank >= 8; // Maximum of 8 cars in the bank
    }

    private Cashier selectCashier() {
        Cashier selectedCashier = cashiers.get(0); // Start with the first cashier

        for (Cashier cashier : cashiers) {
            if (!cashier.isBusy() || (cashier.getQueueSize() < selectedCashier.getQueueSize())) {
                selectedCashier = cashier;
            }
        }

        return selectedCashier;
    }

    private void updateCashiers() {
        for (Cashier cashier : cashiers) {
            if (!cashier.isBusy() && !cashier.getQueue().isEmpty()) {
                cashier.serveCustomer(currentTime);
            }
            cashier.update(0.1); // Update with a fixed time step
        }
    }

    private void printStatistics() {
        System.out.println("Total Customers Rejected: " + totalRejectedCustomers);
        System.out.println("Total Customers Served: " + totalServedCustomers);
        for (Cashier cashier : cashiers) {
            System.out.println(cashier.getName() + " served " + cashier.getServicedCustomers().size() + " customers.");
        }
    }
}