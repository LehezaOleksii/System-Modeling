package oleksii.leheza.kpi.ms.task2;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Client {

    private static int generalId = 1;

    private int id;
    private double processingTime;
    private double arrivalTime;

    public Client(double arrivalTime, double processingTime) {
        id = generalId;
        generalId += 1;
        this.processingTime = processingTime;
        this.arrivalTime = arrivalTime;
        System.out.println("Generate client ID: " + id);
    }
}
