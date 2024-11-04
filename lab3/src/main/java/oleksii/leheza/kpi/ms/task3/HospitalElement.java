package oleksii.leheza.kpi.ms.task3;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HospitalElement {

    protected static int generalId = 1;

    protected int id;
    protected String name;
    protected double currentTime;
    protected double nextEventTime;
    protected int quantity;
    protected boolean isBusy;

    public HospitalElement(String name) {
        this.name = name;
        id = generalId;
        generalId++;
        nextEventTime = Double.MAX_VALUE;
    }
}