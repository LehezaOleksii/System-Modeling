package oleksii.leheza.kpi.ms.task1;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.Queue;

@Getter
@Setter
public class Process extends Element {

    private int maxQueue;
    private Queue<Request> requestQueue;
    private int priority;
    private boolean isBusy;
    private double taskDelay;

    public Process(String name, int priority, double taskDelay, int maxQueue) {
        super(name);
        this.maxQueue = maxQueue;
        this.priority = priority;
        this.requestQueue = new LinkedList<>();
        this.taskDelay = taskDelay;
        this.isBusy = false;
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
            setNextEventTime(getCurrentTime() + request.getProcessingTime()+ taskDelay);
            System.out.println(getProcessInfo() + " is processing Request ID: " + request.getId() + "; Queue size: " + requestQueue.size());
        }
    }

    public void releaseRequest() {
        quantity++;
        isBusy = false;
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
