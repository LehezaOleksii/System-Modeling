//package oleksii.leheza.kpi.ms.task2;
//
//import java.util.LinkedList;
//import java.util.Queue;
//import java.util.Random;
//
//class Bank {
//    private Cashier cashier1;
//    private Cashier cashier2;
//    private double currentTime;
//    private Queue<Request> arrivals;
//    private Random random;
//    private int totalCustomers;
//    private int servedCustomers;
//    private int rejectedCustomers;
//
//    public Bank() {
//        this.cashier1 = new Cashier("Cashier 1");
//        this.cashier2 = new Cashier("Cashier 2");
//        this.arrivals = new LinkedList<>();
//        this.random = new Random();
//        this.currentTime = 0;
//        this.totalCustomers = 0;
//        this.servedCustomers = 0;
//        this.rejectedCustomers = 0;
//    }
//
//    public void simulate(double modelTime) {
//        while (currentTime < modelTime) {
//            generateArrivals();
//            processCashiers();
//            currentTime += 0.1; // Increment time
//        }
//        printStatistics();
//    }
//
//    private void generateArrivals() {
//        if (Math.random() < 0.5) { // Arrival probability
//            Request newRequest = new Request(randomExponential(0.3)); // Generate new request
//            totalCustomers++;
//            if (canAcceptCustomer()) {
//                assignRequestToCashier(newRequest);
//            } else {
//                rejectedCustomers++;
//                System.out.println("Customer rejected: Bank is full.");
//            }
//        }
//    }
//
//    private boolean canAcceptCustomer() {
//        return (cashier1.getQueueSize() + cashier2.getQueueSize() < 6);
//    }
//
//    private void assignRequestToCashier(Request request) {
//        if (cashier1.getQueueSize() <= cashier2.getQueueSize() && cashier1.isBusy() == false) {
//            cashier1.processRequest(request);
//        } else if (cashier2.isBusy() == false) {
//            cashier2.processRequest(request);
//        } else {
//            // Check queue sizes
//            if (cashier1.getQueueSize() < cashier2.getQueueSize() || cashier2.getQueueSize() >= 3) {
//                cashier1.processRequest(request);
//            } else {
//                cashier2.processRequest(request);
//            }
//        }
//    }
//
//    private void processCashiers() {
//        if (cashier1.isBusy() && cashier1.getNextEventTime() <= currentTime) {
//            cashier1.releaseRequest();
//            servedCustomers++;
//        }
//        if (cashier2.isBusy() && cashier2.getNextEventTime() <= currentTime) {
//            cashier2.releaseRequest();
//            servedCustomers++;
//        }
//    }
//
//    private double randomExponential(double mean) {
//        return -mean * Math.log(Math.random());
//    }
//
//    private void printStatistics() {
//        System.out.println("Total customers: " + totalCustomers);
//        System.out.println("Served customers: " + servedCustomers);
//        System.out.println("Rejected customers: " + rejectedCustomers);
//    }
//}