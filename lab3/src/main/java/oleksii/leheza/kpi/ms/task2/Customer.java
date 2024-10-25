package oleksii.leheza.kpi.ms.task2;

class Customer {
    private static int generalId = 1;
    private int id;
    private double arrivalTime;
    private double serviceTime;
    private double startServiceTime;

    public Customer(double arrivalTime, double serviceTime) {
        this.id = generalId++;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getId() {
        return id;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public double getServiceTime() {
        return serviceTime;
    }

    public void setStartServiceTime(double startServiceTime) {
        this.startServiceTime = startServiceTime;
    }

    public double getStartServiceTime() {
        return startServiceTime;
    }

    public double getWaitingTime() {
        return startServiceTime - arrivalTime;
    }
}
