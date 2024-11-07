package oleksii.leheza.kpi.ms;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        RequestGenerator generator = new RequestGenerator("Generator", 2);

        Process p1_5 = new Process("1)Process 5", 2, 7, 2);
        Process p1_6 = new Process("1)Process 6", 1, 7, 2);
        List<Element> smo3Elements = new ArrayList<>();
        smo3Elements.add(p1_5);
        smo3Elements.add(p1_6);
        Smo smo1_3 = new Smo(smo3Elements);

        List<Element> previousProcesses1 = new ArrayList<>();
        previousProcesses1.add(p1_5);
        previousProcesses1.add(p1_6);
        Process p1_3 = new Process("1)Process 3", 2, 7, 2, previousProcesses1);
        Process p1_4 = new Process("1)Process 4", 1, 7, 2, previousProcesses1);
        List<Element> smo2Elements = new ArrayList<>();
        smo2Elements.add(p1_3);
        smo2Elements.add(p1_4);
        Smo smo1_2 = new Smo(smo2Elements);

        List<Element> previousProcesses2 = new ArrayList<>();
        previousProcesses2.add(p1_3);
        previousProcesses2.add(p1_4);
        Process p1_1 = new Process("1)Process 1", 2, 7, 2, previousProcesses2);
        Process p1_2 = new Process("1)Process 2", 1, 7, 2, previousProcesses2);
        List<Element> smo1Elements = new ArrayList<>();
        smo1Elements.add(generator);
        smo1Elements.add(p1_1);
        smo1Elements.add(p1_2);
        Smo smo1_1 = new Smo(smo1Elements);

        List<Smo> smos1 = new ArrayList<>();
        smos1.add(smo1_1);
        smos1.add(smo1_2);
        smos1.add(smo1_3);
        Server server = new Server(smos1);
        server.simulate(200);

//        Smo smo1 = getSimpleSmo(1);
//        Smo smo2 = getSimpleSmo(2);
//        Smo smo3 = getSimpleSmo(3);
//
//        List<Smo> smos1 = new ArrayList<>();
//        smos1.add(smo1);
//        smos1.add(smo2);
//        smos1.add(smo3);
//        Server server = new Server(smos1);
//        server.simulate(200);

//        Smo smo1d = getDifficultSmo(1);
//        Smo smo2d = getDifficultSmo(2);
//        Smo smo3d = getDifficultSmo(3);
//
//        List<Smo> smos1d = new ArrayList<>();
//        smos1d.add(smo1d);
//        smos1d.add(smo2d);
//        smos1d.add(smo3d);
//        Server server = new Server(smos1d);
//        server.simulate(200);

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

    private static Smo getDifficultSmo(List<Element> smoElements) {
        List<Element> smo1Elements = new ArrayList<>(smoElements);
        Smo smo1 = new Smo(smo1Elements);
        return smo1;
    }

    public static void printStatistic(int requests, int processed, int failed) {
        System.out.println("----Statistic----\n" + "All requests :" + requests + "\n" + "Processed requests: " + processed + "\n" + "Failed requests: " + failed + "\n");
    }
}
