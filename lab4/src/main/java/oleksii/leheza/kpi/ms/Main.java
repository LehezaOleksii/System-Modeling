package oleksii.leheza.kpi.ms;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Smo> smoList = new ArrayList<>();

        List<SmoElement> smo1AllElements = new ArrayList<>();

        List<SmoElement> smo1Queue1Elements = new ArrayList<>();
        SmoElement smo1Device1 = new Device("1) Device 1", 1.0);
        SmoElement smo1Device2 = new Device("1) Device 2", 2.0);
        smo1Queue1Elements.add(smo1Device1);
        smo1Queue1Elements.add(smo1Device2);
        SmoElement smo1Queue2 = new Queue("1) Queue 1", smo1Queue1Elements, 5);
        SmoElement smo1Generator1 = new RequestGenerator("1) Generator 1", smo1Queue2, 1, 4);

        smo1AllElements.add(smo1Generator1);
        smo1AllElements.add(smo1Queue2);
        smo1AllElements.add(smo1Device2);
        smo1AllElements.add(smo1Device1);

        Smo smo1 = new Smo(smo1AllElements);

        List<SmoElement> smo2AllElements = new ArrayList<>();

        SmoElement smo2Device2 = new Device("2) Device 2", 1.0);
        SmoElement smo2Device3 = new Device("2) Device 3", 2.0);
        List<SmoElement> smo2Queue1Elements = new ArrayList<>();
        smo2Queue1Elements.add(smo2Device2);
        smo2Queue1Elements.add(smo2Device3);
        SmoElement smo2Queue2 = new Queue("2) Queue 1", smo2Queue1Elements);
        SmoElement smo2Device1 = new Device("2) Device 1", smo2Queue2, 1.0);
        SmoElement smo2Generator1 = new RequestGenerator("2) Generator 1", smo2Device1, 1, 3);

        smo2Device1.addElement(smo1Device1);
        smo2Device2.addElement(smo1Device1);

        smo2AllElements.add(smo2Queue2);
        smo2AllElements.add(smo2Device1);
        smo2AllElements.add(smo2Device2);
        smo2AllElements.add(smo2Device3);
        smo2AllElements.add(smo2Generator1);

        Smo smo2 = new Smo(smo2AllElements);

        smoList.add(smo1);
//        smoList.add(smo2);

        SmoSimulator smoSimulator = new SmoSimulator(smoList);
        smoSimulator.simulate(200);
    }
}
