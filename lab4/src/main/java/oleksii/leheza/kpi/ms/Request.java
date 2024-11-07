package oleksii.leheza.kpi.ms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Request {

    private static int generalRequestId = 1;

    private String name;
    private int id;
    private double requestTime;

    public Request(double requestTime) {
        name = "Request ID " + generalRequestId;
        id = generalRequestId++;
        this.requestTime = requestTime;
    }
}
