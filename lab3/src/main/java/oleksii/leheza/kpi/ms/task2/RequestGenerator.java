package oleksii.leheza.kpi.ms.task2;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter
class RequestGenerator extends Element {
    private double arrivalInterval;
    private Random random;

    public RequestGenerator(String name, double arrivalInterval) {
        super(name);
        this.arrivalInterval = arrivalInterval;
        this.random = new Random();
    }

    public Customer generateCustomer(double currentTime) {
        double serviceTime = generateServiceTime();
        return new Customer(currentTime + generateArrivalTime(), serviceTime);
    }

    private double generateArrivalTime() {
        return -arrivalInterval * Math.log(1 - random.nextDouble());
    }

    private double generateServiceTime() {
        double mean = 1.0; // Mean service time
        double stddev = 0.3; // Standard deviation
        return mean + stddev * random.nextGaussian();
    }
}