package oleksii.leheza.kpi.ms.task2;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Client {

    private static int generalId = 1;

    private int id;
    private double processingTime;

    public Client(double processingTime) {
        id = generalId;
        generalId += 1;
        this.processingTime = processingTime;
        System.out.println("Generate client ID: " + id);
    }
}
