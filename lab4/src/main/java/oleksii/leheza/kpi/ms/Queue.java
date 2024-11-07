package oleksii.leheza.kpi.ms;

import lombok.Getter;
import lombok.Setter;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class Queue extends SmoElement {

    private Deque<Request> requests;
    private int maxSize;

    public Queue(String name, List<SmoElement> nextElements) {
        super(name, nextElements);
        this.maxSize = Integer.MAX_VALUE;
        this.requests = new LinkedList<>();
    }

    public Queue(String name, List<SmoElement> nextElements, int maxSize) {
        super(name, nextElements);
        this.maxSize = maxSize;
        this.requests = new LinkedList<>();
    }

    public void addRequest(Request request) {
        if (requests.size() < maxSize) {
            requests.add(request);
        }
    }
}
