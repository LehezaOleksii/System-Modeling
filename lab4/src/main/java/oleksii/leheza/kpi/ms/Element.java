package oleksii.leheza.kpi.ms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract class Element {

    private static int generalId = 1;

    protected int id;
    protected String name;
    protected double currentTime;
    protected double nextEventTime;
    protected int quantity;

    public Element(String name) {
        this.name = name;
        id = generalId;
        generalId++;
        nextEventTime = Double.MAX_VALUE;
    }
}
