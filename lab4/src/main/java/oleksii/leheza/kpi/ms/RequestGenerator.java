package oleksii.leheza.kpi.ms;

import java.util.List;
import java.util.Random;

public class RequestGenerator extends SmoElement {

    private double frequency;
    private double requestValue;

    public RequestGenerator(String name, List<SmoElement> nextElements, double frequency, double requestValue) {
        super(name, nextElements);
        nextEventTime = 0;
        this.frequency = frequency;
        this.requestValue = requestValue;
    }

    public RequestGenerator(String name, SmoElement nextElement, double frequency, double requestValue) {
        super(name, nextElement);
        nextEventTime = 0;
        this.frequency = frequency;
        this.requestValue = requestValue;
    }

    public Request generateRequest() {
        setNextEventTime(getCurrentTime() + generateTime(frequency));
        return new Request(generateTime(requestValue));
    }

    public double generateTime(double timeMean) {
        double a = 0;
        while (a == 0) {
            a = Math.random();
        }
        a = -timeMean * Math.log(a);
        return a;
    }

    public SmoElement getRandomElement() {
        if (nextElements.size() == 1) {
            return nextElements.get(0);
        } else {
            Random rand = new Random();
            int index = rand.nextInt(nextElements.size());
            return nextElements.get(index);
        }
    }
}
