package oleksii.leheza.kpi.ms.task6;

import java.util.ArrayList;

public class SimModel {
    public static void main(String[] args) {

        Process p3 = createProcess(1.0, "PROCESS_3", "exp", 5);
        Process p2 = createProcess(1.0, "PROCESS_2", "exp", 5);
        Process p1 = createProcess(1.0, "PROCESS_1", "exp", 5);
        Create c = create(2.0, "CREATOR", "exp");

        p1.addNextElement(p2);
        p1.addNextElement(p3);

        p2.addNextElement(p1);
        p2.addNextElement(p3);

        c.setNextElement(p1);

        ArrayList<Element> list = new ArrayList<>();
        list.add(c);
        list.add(p1);
        list.add(p2);
        list.add(p3);

        Model model = new Model(list);
        model.simulate(1000.0);
    }

    private static Create create(double delay, String name, String distribution) {
        Create c = new Create(delay);
        c.setName(name);
        c.setDistribution(distribution);
        System.out.println(c.getName() + " id = " + c.getId());
        return c;
    }

    private static Process createProcess(double delay, String name, String distribution, int maxQueue) {
        Process p = new Process(delay);
        p.setName(name);
        p.setDistribution(distribution);
        p.setMaxqueue(maxQueue);
        System.out.println(p.getName() + " id = " + p.getId());
        return p;
    }
}