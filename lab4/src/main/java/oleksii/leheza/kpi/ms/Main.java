package oleksii.leheza.kpi.ms;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        RequestGenerator generator = new RequestGenerator("Generator", 7);

        Process p5 = new Process("1)Process 5", 2, 2);
        Process p4 = new Process("1)Process 4", 2, 2, p5);
        Process p3 = new Process("1)Process 3", 2, 2, p4);
        Process p2 = new Process("1)Process 2", 2, 2, p3);
        Process p1 = new Process("1)Process 1", 2, 2, p2);
        List<Process> nextProcesses = new ArrayList<>();
        nextProcesses.add(p1);
        generator.setNextProcesses(nextProcesses);

        List<Element> smo1Elements = new ArrayList<>();
        smo1Elements.add(generator);
        smo1Elements.add(p1);
        smo1Elements.add(p2);
        Smo smo1 = new Smo(smo1Elements);

        List<Element> smo2Elements = new ArrayList<>();
        smo2Elements.add(p3);
        smo2Elements.add(p4);
        smo2Elements.add(p5);
        Smo smo2 = new Smo(smo2Elements);

        List<Smo> smos1 = new ArrayList<>();

        smos1.add(smo1);
        smos1.add(smo2);
        Server server = new Server(smos1);
        server.simulate(1000);

//        RequestGenerator generator = new RequestGenerator("Generator", 2);
//        Process p1_5 = new Process("2)Process 5", 2, 2);
//        Process p1_6 = new Process("2)Process 6", 1, 2);
//        List<Element> smo3Elements = new ArrayList<>();
//        smo3Elements.add(p1_5);
//        smo3Elements.add(p1_6);
//
//        Smo smo1_3 = new Smo(smo3Elements);
//
//        List<Element> previousProcesses1 = new ArrayList<>();
//        previousProcesses1.add(p1_5);
//        previousProcesses1.add(p1_6);
//        Process p1_3 = new Process("2)Process 3", 2, 2, previousProcesses1);
//        Process p1_4 = new Process("1)Process 4", 1, 2, previousProcesses1);
//        List<Element> smo2Elements = new ArrayList<>();
//        smo2Elements.add(p1_3);
//        smo2Elements.add(p1_4);
//        Smo smo1_2 = new Smo(smo2Elements);
//
//
//        List<Element> previousProcesses2 = new ArrayList<>();
//        previousProcesses2.add(p1_3);
//        previousProcesses2.add(p1_4);
//        Process p1_1 = new Process("2)Process 1", 2, 2, previousProcesses2);
//        Process p1_2 = new Process("2)Process 2", 1, 2, previousProcesses2);
//        List<Element> smo1Elements = new ArrayList<>();
//        smo1Elements.add(generator);
//        smo1Elements.add(p1_1);
//        smo1Elements.add(p1_2);
//        Smo smo1_1 = new Smo(smo1Elements);
//
//        List<Process> nextProcesses = new ArrayList<>();
//        nextProcesses.add(p1_1);
//        nextProcesses.add(p1_2);
//        generator.setNextProcesses(nextProcesses);
//
//        List<Smo> smos1 = new ArrayList<>();
//        smos1.add(smo1_1);
//        smos1.add(smo1_2);
//        smos1.add(smo1_3);
//        Server server = new Server(smos1);
//        server.simulate(1000);

        printStatistic(server.getRequests(), server.getProcessed(), server.getFailed());
    }

    public static void printStatistic(int requests, int processed, int failed) {
        System.out.println("----Statistic----\n" + "All requests :" + requests + "\n" + "Processed requests: " + processed + "\n" + "Failed requests: " + failed + "\n");
    }
}
