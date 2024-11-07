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
    private double taskDelay;
    private List<Element> nextElements = new LinkedList<>();

    public Process(String name, int priority, double taskDelay, int maxQueue) {
        super(name);
        this.maxQueue = maxQueue;
        this.priority = priority;
        this.requestQueue = new LinkedList<>();
        this.taskDelay = taskDelay;
        this.isBusy = false;
    }

    public Process(String name, int priority, double taskDelay, int maxQueue, List<Element> nextElements) {
        super(name);
        this.maxQueue = maxQueue;
        this.priority = priority;
        this.requestQueue = new LinkedList<>();
        this.taskDelay = taskDelay;
        this.isBusy = false;
        this.nextElements = nextElements;
    }

    public Process(String name, int priority, double taskDelay, int maxQueue, Element nextElement) {
        super(name);
        this.maxQueue = maxQueue;
        this.priority = priority;
        this.requestQueue = new LinkedList<>();
        this.taskDelay = taskDelay;
        this.isBusy = false;
        this.nextElements.add(nextElement);
    }

    public void processRequest(Request request) {
        if (isBusy) {
            if (requestQueue.size() < maxQueue) {
                requestQueue.add(request);
                System.out.println(getProcessInfo() + " is busy; Queue size: " + requestQueue.size());
            } else {
                System.out.println(getProcessInfo() + " is busy; Queue size: " + requestQueue.size() + " FAILURE");
            }
        } else {
            isBusy = true;
            setNextEventTime(getCurrentTime() + request.getProcessingTime() + taskDelay);
            System.out.println(getProcessInfo() + " is processing Request ID: " + request.getId() + "; Queue size: " + requestQueue.size());
        }
    }

    public void releaseRequest() {
        quantity++;
        isBusy = false;
        if (nextElements != null && !nextElements.isEmpty()) {
            for (Element element : nextElements) {
                if (element instanceof Process process) {
                    if (!process.isBusy || process.getRequestQueue().size() < process.getMaxQueue()) {
                        Request releasedRequest = requestQueue.peek();
                        process.processRequest(releasedRequest);
                    }
                }
            }
        }
        if (!requestQueue.isEmpty()) {
            Request nextRequest = requestQueue.poll();
            setNextEventTime(getCurrentTime() + nextRequest.getProcessingTime() + taskDelay);
            isBusy = true;
            System.out.println(getProcessInfo() + " is processing next Request ID: " + nextRequest.getId() + "; Queue size: " + requestQueue.size());
        } else {
            setNextEventTime(Double.MAX_VALUE);
            System.out.println(getProcessInfo() + " is free; Queue size: " + requestQueue.size());
        }
    }

    private String getProcessInfo() {
        return getName() + " id :" + getId();
    }
}
