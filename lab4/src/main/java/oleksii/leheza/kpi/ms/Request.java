package oleksii.leheza.kpi.ms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Request {

    private static int generalId = 1;

    private int id;
    private double processingTime;
    private double busyTime;

    public Request(double processingTime) {
        id = generalId;
        generalId += 1;
        this.processingTime = processingTime;
        System.out.println("Generate Request ID: " + id);
    }
}
