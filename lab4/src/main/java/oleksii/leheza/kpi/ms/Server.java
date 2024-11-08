package oleksii.leheza.kpi.ms;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Server {

    private double currentTime;
    private double nextTime;
    private List<Element> elements = new ArrayList<>();
    private int requestNum;
    private int processedRequests;
    private int failedRequests;
    private Set<Request> processedRequest = new HashSet<>();

    public Server(List<Smo> smos) {
        for (Smo smo : smos) {
            elements.addAll(smo.getSmoElements());
        }
    }

    public void simulate(double modelTime) {
        Element currentElement = null;
        while (currentTime < modelTime) {
            nextTime = Double.MAX_VALUE;
            for (Element e : elements) {
                if (e.getNextEventTime() < nextTime) {
                    nextTime = e.getNextEventTime();
                    currentElement = e;
                }
            }
            currentTime = nextTime;
            for (Element e : elements) {
                e.setCurrentTime(currentTime);
            }

            if (currentElement instanceof RequestGenerator requestGenerator) {
                Request request = requestGenerator.generateRequest();
                requestNum += 1;
                assignRequestToProcess(requestGenerator, request);
            } else if (currentElement instanceof Process process) {
                Request request = process.getCurrentRequest();
                processedRequest.add(request);
                process.releaseRequest();
            }
        }
    }

    private void assignRequestToProcess(RequestGenerator generator, Request request) {
        boolean assigned = false;

        List<Process> processes = generator.getNextProcesses();

        List<Process> sortedProcesses = processes.stream()
                .sorted(Comparator.comparingInt(Process::getPriority).reversed())
                .toList();

        for (Process process : sortedProcesses) {
            if (!process.isBusy() || process.getRequestQueue().size() < process.getMaxQueue()) {
                System.out.println("Request ID: " + request.getId() + " assigned to " + process.getName());
                process.processRequest(request);
                assigned = true;
                processedRequests += 1;
                break;
            }
        }

        if (!assigned) {
            System.out.println("Request ID: " + request.getId() + " rejected: All processes busy or queues full");
            failedRequests += 1;
        }
    }

    public int getRequests() {
        return requestNum;
    }

    public int getProcessed() {
        return processedRequests;
    }

    public int getFailed() {
        return failedRequests;
    }
}
