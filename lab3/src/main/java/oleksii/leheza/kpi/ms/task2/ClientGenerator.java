package oleksii.leheza.kpi.ms.task2;

public class ClientGenerator extends Element {

    private double taskTime;
    private double clientArrivalFrequency;

    public ClientGenerator(String name, double clientArrivalFrequency, double taskTime) {
        super(name);
        this.taskTime = taskTime;
        this.clientArrivalFrequency = clientArrivalFrequency;
    }

    public Client generateClients() {
        setNextEventTime(getCurrentTime() + generateTime(clientArrivalFrequency));
        return new Client(generateTime(taskTime));
    }

    public double generateTime(double timeMean) {
        double a = 0;
        while (a == 0) {
            a = Math.random();
        }
        a = -timeMean * Math.log(a);
        return a;
    }
}
