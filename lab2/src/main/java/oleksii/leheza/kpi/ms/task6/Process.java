package oleksii.leheza.kpi.ms.task6;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Process extends Element {

    private int queue, maxqueue, failure;
    private double meanQueue;
    private List<Element> nextElements = new ArrayList<>();

    public void addNextElement(Element element) {
        nextElements.add(element);
    }

    public Process(double delay) {
        super(delay);
        queue = 0;
        maxqueue = Integer.MAX_VALUE;
        meanQueue = 0.0;
    }

    @Override
    public void inAct() {
        if (super.getState() == 0) {
            super.setState(1);
            super.setTnext(super.getTcurr() + super.getDelay());
        } else {
            if (getQueue() < getMaxqueue()) {
                setQueue(getQueue() + 1);
            } else {
                failure++;
            }
        }
    }

    @Override
    public void outAct() {
        super.outAct();
        super.setTnext(Double.MAX_VALUE);
        super.setState(0);
        if (nextElement() != null) {
            nextElement().inAct();
        }
        if (getQueue() > 0) {
            setQueue(getQueue() - 1);
            super.setState(1);
            super.setTnext(super.getTcurr() + super.getDelay());
        }
        if (!nextElements.isEmpty()) {
            Element nextElement = nextElements.get(0);
            if (nextElement != null) {
                nextElement.inAct();
            }
        }
    }

    public Element nextElement() {
        Random rand = new Random();
        if (nextElements.size() > 0) {
            int nextElementIndex = rand.nextInt(nextElements.size());
            return nextElements.get(nextElementIndex);
        }
        return null;
    }

    public int getFailure() {
        return failure;
    }

    public int getQueue() {
        return queue;
    }

    public void setQueue(int queue) {
        this.queue = queue;
    }

    public int getMaxqueue() {
        return maxqueue;
    }

    public void setMaxqueue(int maxqueue) {
        this.maxqueue = maxqueue;
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("failure = " + this.getFailure());
    }

    @Override
    public void doStatistics(double delta) {
        meanQueue = getMeanQueue() + queue * delta;
    }

    public double getMeanQueue() {
        return meanQueue;
    }
}
