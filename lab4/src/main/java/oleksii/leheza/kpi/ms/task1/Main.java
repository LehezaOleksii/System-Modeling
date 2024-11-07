package oleksii.leheza.kpi.ms.task1;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Smo smo1 = getSimpleSmo(1);
        Smo smo2 = getSimpleSmo(2);
        Smo smo3 = getSimpleSmo(3);

        List<Smo> smos1 = new ArrayList<>();
        smos1.add(smo1);
        smos1.add(smo2);
        smos1.add(smo3);
        Server server = new Server(smos1);
        server.simulate(200);

        printStatistic(server.getRequests(), server.getProcessed(), server.getFailed());
    }

    private static Smo getSimpleSmo(int smoNumber) {
        Process p1 = new Process(smoNumber + ")Process 1", 2, 7, 2);
        Process p2 = new Process(smoNumber + ")Process 2", 1, 7, 2);
        RequestGenerator generator = new RequestGenerator("Generator", 2);

        List<Element> elements = new ArrayList<>();
        elements.add(p1);
        elements.add(p2);
        elements.add(generator);

        List<Element> smo1Elements = new ArrayList<>(elements);
        Smo smo1 = new Smo(smo1Elements);
        return smo1;
    }

    public static void printStatistic(int requests, int processed, int failed) {
        System.out.println("----Statistic----\n" + "All requests :" + requests + "\n" + "Processed requests: " + processed + "\n" + "Failed requests: " + failed + "\n");
    }
}
