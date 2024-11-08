package oleksii.leheza.kpi.ms;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Getter
@Setter
public class Process extends Element {

    private int maxQueue;
    private Queue<Request> requestQueue;
    private int priority;
    private boolean isBusy;
    private Request currentRequest;
    private List<Element> nextElements = new LinkedList<>();

    public Process(String name, int priority, int maxQueue) {
        super(name);
        this.maxQueue = maxQueue;
        this.priority = priority;
        this.requestQueue = new LinkedList<>();
        this.isBusy = false;
    }

    public Process(String name, int priority, int maxQueue, List<Element> nextElements) {
        super(name);
        this.maxQueue = maxQueue;
        this.priority = priority;
        this.requestQueue = new LinkedList<>();
        this.isBusy = false;
        this.nextElements = nextElements;
    }

    public Process(String name, int priority, int maxQueue, Element nextElement) {
        super(name);
        this.maxQueue = maxQueue;
        this.priority = priority;
        this.requestQueue = new LinkedList<>();
        this.isBusy = false;
        this.nextElements.add(nextElement);
    }

    public void processRequest(Request request) {
        if (isBusy) {
            if (requestQueue.size() < maxQueue) {
                requestQueue.add(request);
                System.out.println(name + " is busy; Queue size: " + requestQueue.size());
            } else {
                System.out.println(name + " is busy; Queue size: " + requestQueue.size() + " FAILURE");
            }
        } else {
            isBusy = true;
            currentRequest = request;
            setNextEventTime(getCurrentTime() + request.getProcessingTime());
            System.out.println(name + " is processing Request ID: " + request.getId() + "; Queue size: " + requestQueue.size());
        }
    }

    public void releaseRequest() {
        quantity++;
        isBusy = false;
        currentRequest.setBusyTime(currentRequest.getBusyTime() + currentRequest.getProcessingTime());
        if (nextElements != null && !nextElements.isEmpty()) {
            for (Element element : nextElements) {
                if (element instanceof Process process) {
                    process.processRequest(currentRequest);
                    currentRequest = requestQueue.peek();
                    break;
                }
            }
        }
        if (!requestQueue.isEmpty()) {
            Request nextRequest = requestQueue.poll();
            setNextEventTime(getCurrentTime() + nextRequest.getProcessingTime());
            isBusy = true;
            System.out.println(name + " is processing next Request ID: " + nextRequest.getId() + "; Queue size: " + requestQueue.size());
        } else {
            setNextEventTime(Double.MAX_VALUE);
            System.out.println(name + " is free; Queue size: " + requestQueue.size());
        }
    }
}
