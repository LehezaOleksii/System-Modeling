package oleksii.leheza.kpi.ms.task1;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public class Server {

    private double currentTime;
    private double nextTime;
    private List<Element> elements = new ArrayList<>();
    private int requestNum;
    private int processedRequests;
    private int failedRequests;

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

            if (currentElement instanceof RequestGenerator) {
                Request request = ((RequestGenerator) currentElement).generateRequest();
                requestNum += 1;
                assignRequestToProcess(request);
            } else if (currentElement instanceof Process) {
                ((Process) currentElement).releaseRequest();
            }
        }
    }

    private void assignRequestToProcess(Request request) {
        boolean assigned = false;

        List<Process> sortedProcesses = elements.stream()
                .filter(e -> e instanceof Process)
                .map(e -> (Process) e)
                .sorted(Comparator.comparingInt(Process::getPriority).reversed())
                .toList();

        for (Process process : sortedProcesses) {
            if (!process.isBusy() || process.getRequestQueue().size() < process.getMaxQueue()) {
                System.out.println("Request ID " + request.getId() + " assigned to " + process.getName());
                process.processRequest(request);
                assigned = true;
                processedRequests += 1;
                break;
            }
        }

        if (!assigned) {
            System.out.println("Request ID " + request.getId() + " rejected: All processes busy or queues full");
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
