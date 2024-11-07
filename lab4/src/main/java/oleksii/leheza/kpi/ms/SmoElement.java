package oleksii.leheza.kpi.ms;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
abstract class SmoElement {

    private static int generalId = 1;

    protected int id;
    protected String name;
    protected List<SmoElement> nextElements = new ArrayList<>();
    protected double currentTime = 0;
    protected double nextEventTime;

    public SmoElement(String name) {
        id = generalId++;
        this.name = name + " Id: " + id;
        this.nextEventTime = Integer.MAX_VALUE;
    }

    public SmoElement(String name, List<SmoElement> nextElements) {
        id = generalId++;
        this.name = name + " Id: " + id;
        this.nextElements = nextElements;
        this.nextEventTime = Integer.MAX_VALUE;
    }

    public SmoElement(String name, SmoElement nextElement) {
        id = generalId++;
        this.name = name + " Id: " + id;
        List<SmoElement> nextElements = new ArrayList<>();
        nextElements.add(nextElement);
        this.nextElements = nextElements;
        this.nextEventTime = Integer.MAX_VALUE;
    }

    public void addElement(SmoElement element) {
        nextElements.add(element);
    }
}
