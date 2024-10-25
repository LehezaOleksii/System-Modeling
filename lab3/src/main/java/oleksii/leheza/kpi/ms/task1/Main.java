package oleksii.leheza.kpi.ms.task1;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Process p1 = new Process("Process 1", 2, 7, 2);
        Process p2 = new Process("Process 2", 1, 7, 2);
        RequestGenerator generator = new RequestGenerator("Generator", 2);

        List<Element> elements = new ArrayList<>();
        elements.add(p1);
        elements.add(p2);
        elements.add(generator);

        Server server = new Server(elements);
        server.simulate(200);

        printStatistic(server.getRequests(), server.getProcessed(), server.getFailed());
    }

    public static void printStatistic(int requests, int processed, int failed) {
        System.out.println("----Statistic----\n" +
                "All requests :" + requests + "\n" +
                "Processed requests: " + processed + "\n" +
                "Failed requests: " + failed + "\n");
    }
}
