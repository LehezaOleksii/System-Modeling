package oleksii.leheza.kpi.ms.task2;

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

    public Element(String name) {
        this.name = name;
        this.id = generalId++;
    }
}
