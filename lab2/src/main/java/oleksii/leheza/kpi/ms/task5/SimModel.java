package oleksii.leheza.kpi.ms.task5;

import java.util.ArrayList;

public class SimModel {
    public static void main(String[] args) {

        Process p3 = createProcess(1.0, null, "PROCESS_3", "exp", 1, 5);
        Process p2 = createProcess(1.0, p3, "PROCESS_2", "exp", 1, 5);
        Process p1 = createProcess(1.0, p2, "PROCESS_1", "exp", 1, 5);
        Create c = create(2.0, p1, "CREATOR", "exp");

        ArrayList<Element> list = new ArrayList<>();
        list.add(c);
        list.add(p1);
        list.add(p2);
        list.add(p3);

        Model model = new Model(list);
        model.simulate(1000.0);
    }

    private static Create create(double delay, Element nextElement, String name, String distribution) {
        Create c = new Create(delay);
        c.setNextElement(nextElement);
        c.setName(name);
        c.setDistribution(distribution);
        System.out.println(c.getName() + " id = " + c.getId());
        return c;
    }

    private static Process createProcess(double delay, Element nextElement, String name, String distribution, int devices, int maxQueue) {
        Process p = new Process(delay, devices);
        p.setNextElement(nextElement);
        p.setName(name);
        p.setDistribution(distribution);
        p.setMaxqueue(maxQueue);
        System.out.println(p.getName() + " id = " + p.getId());
        return p;
    }
}