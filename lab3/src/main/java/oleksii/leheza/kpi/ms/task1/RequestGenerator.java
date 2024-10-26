package oleksii.leheza.kpi.ms.task1;

public class RequestGenerator extends Element {

    private double taskTime;

    public RequestGenerator(String name, double taskTime) {
        super(name);
        this.taskTime = taskTime;
    }

    public Request generateRequest() {
        setNextEventTime(getCurrentTime() + generateTime(taskTime));
        return new Request(generateTime(taskTime));
    }

    private double generateTime(double timeMean) {
        double a = 0;
        while (a == 0) {
            a = Math.random();
        }
        a = -timeMean * Math.log(a);
        return a;
    }
}
