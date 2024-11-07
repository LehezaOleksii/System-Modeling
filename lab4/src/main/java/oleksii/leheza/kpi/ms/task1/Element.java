package oleksii.leheza.kpi.ms.task1;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract class Element {

    private static int generalId = 1;

    private int id;
    private String name;
    private double currentTime;
    private double nextEventTime;
    protected int quantity;

    public Element(String name) {
        this.name = name;
        id = generalId;
        generalId++;
    }
}
