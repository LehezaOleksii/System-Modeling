package oleksii.leheza.kpi.ms.task5;

import java.util.Arrays;

public class Process extends Element {

    private int queue, maxqueue, failure;
    private double meanQueue;
    private boolean[] devices;

    public Process(double delay, int devicesQuantity) {
        super(delay);
        queue = 0;
        maxqueue = Integer.MAX_VALUE;
        meanQueue = 0.0;
        devices = new boolean[devicesQuantity];
        Arrays.fill(devices, false);
    }

    public int getFreeDeviceIndex() {
        for (int i = 0; i < devices.length; i++) {
            if (!devices[i]) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void inAct() {
        int freeDeviceIndex = getFreeDeviceIndex();
        if (freeDeviceIndex != -1) {
            devices[freeDeviceIndex] = true;
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

        for (int i = 0; i < devices.length; i++) {
            if (devices[i]) {
                devices[i] = false;
                break;
            }
        }

        if (getQueue() > 0) {
            setQueue(getQueue() - 1);
            inAct();
        }

        if (this.getNextElement() != null) {
            this.getNextElement().inAct();
        }
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
