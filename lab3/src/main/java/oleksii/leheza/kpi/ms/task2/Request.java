package oleksii.leheza.kpi.ms.task2;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Request {
    private static int generalId = 1;
    private int id;

    public Request() {
        id = generalId++;
        System.out.println("Generated Request ID: " + id);
    }
}
