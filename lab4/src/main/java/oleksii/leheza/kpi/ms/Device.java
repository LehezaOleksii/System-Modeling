package oleksii.leheza.kpi.ms;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Device extends SmoElement {

    private boolean isBusy;
    private double productivity;
    protected List<Queue> previousQueues = new ArrayList<>();
    private Request currentRequest;

    public Device(String name, double productivity) {
        super(name);
        this.productivity = productivity;
    }

    public Device(String name, List<SmoElement> nextElements, double productivity) {
        super(name, nextElements);
        this.productivity = productivity;
        for (SmoElement element : nextElements) {
            if (element instanceof Queue queue) {
                previousQueues.add(queue);
            }
        }
    }

    public Device(String name, SmoElement nextElement, double productivity) {
        super(name, nextElement);
        if (nextElement instanceof Queue queue) {
            previousQueues.add(queue);
        }
        this.productivity = productivity;
    }

    public void processRequest(Request request) {
        if (isBusy) {
            for (Queue queue : previousQueues) {
                if (queue.getRequests().size() < queue.getMaxSize()) {
                    queue.getRequests().add(request);
                    System.out.println(request.getName() + " assigned to queue with id:" + queue.getId());
                    break;
                } else {
                    System.out.println(" Queue with id " + queue.getId() + " is full");
                }
            }
        } else {
            isBusy = true;
            setNextEventTime(currentTime + request.getRequestTime() / productivity);
            currentRequest = request;
            System.out.println(request.getName() + " is processing by " + name);
        }
    }

    public void processRequestWithoutQueue(Request request) {
        if (isBusy) {
            System.out.println(request.getName() + " device is busy");
        } else {
            isBusy = true;
            setNextEventTime(currentTime + request.getRequestTime() / productivity);
            currentRequest = request;
            System.out.println(request.getName() + " is processing by " + name);
        }
    }

    public void releaseRequest() {
        isBusy = false;
        if (nextElements != null) {
            for (SmoElement element : nextElements) {
                if (element instanceof Device device) {
                    device.processRequest(currentRequest);
                }
            }
        }
        System.out.println(name + " device released; request ");
        for (Queue queue : previousQueues) {
            if (!queue.getRequests().isEmpty()) {
                Request nextRequest = queue.getRequests().poll();
                setNextEventTime(currentTime + nextRequest.getRequestTime() / productivity);
                currentRequest = nextRequest;
                isBusy = true;
                System.out.println(nextRequest.getName() + " assigned from queue with id:" + queue.getId());
            } else {
                setNextEventTime(Double.MAX_VALUE);
                System.out.println(name + " is free");
            }
        }
    }
}
