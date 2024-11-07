package oleksii.leheza.kpi.ms;

import java.util.List;
import java.util.Random;

public class SmoSimulator {

    private double currentTime;
    private double nextTime;
    private SmoElement currentElement;
    private List<Smo> smos;
    private Random random = new Random();

    public SmoSimulator(List<Smo> smos) {
        this.smos = smos;
    }

    public void simulate(double modelTime) {
        while (currentTime < modelTime) {
            System.out.println("=======================================");
            nextTime = Double.MAX_VALUE;
            for (Smo smo : smos) {
                List<SmoElement> smoElements = smo.getSmoElements();
                for (SmoElement e : smoElements) {
                    if (e.getNextEventTime() < nextTime) {
                        nextTime = e.getNextEventTime();
                        currentElement = e;
                    }
                }
            }
            currentTime = nextTime;
            for (Smo smo : smos) {
                List<SmoElement> smoElements = smo.getSmoElements();
                for (SmoElement e : smoElements) {
                    e.setCurrentTime(currentTime);
                    System.out.println(e.name + " next event time " + e.getNextEventTime());
                }
            }
            if (currentElement instanceof Device device) {
                device.releaseRequest();
            } else if (currentElement instanceof RequestGenerator requestGenerator) {
                Request request = requestGenerator.generateRequest();
                System.out.println("Generated Request: " + request.getId());
                registerRequestToElement(request, requestGenerator.getRandomElement());
            }
        }
    }

    private void registerRequestToElement(Request request, SmoElement element) {
        if (element instanceof Device device) {
            if (device.isBusy() == true) {
                System.out.println("Device is busy, request rejected");
            } else {
                System.out.println("add " + request.getName() + " to " + device.getName());
                device.processRequestWithoutQueue(request);
            }
        } else if (element instanceof Queue queue) {
            if (queue.getRequests().size() < queue.getMaxSize()) {
                queue.addRequest(request);
                if (!queue.getNextElements().isEmpty()) {
                    //find no busy element
                    List<SmoElement> nextQueueElements = queue.getNextElements();
                    for (SmoElement e : nextQueueElements) {
                        if (e instanceof Device device) {
                            if (!device.isBusy()) {
                                device.processRequestWithoutQueue(request);
                                break;
                            }
                        }
                    }
                }
            } else {
                System.out.println("Queue is full, request rejected");
            }
        }
    }
}
