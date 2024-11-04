package oleksii.leheza.kpi.ms.task3;

import java.util.Random;

public class ErlangDistribution {

    public double getNumber(double expectedValue, double k) {

        Random random = new Random();
        double lambda = k / expectedValue;
        double erlangValue = 0;
        for (int i = 0; i < k; i++) {
            erlangValue += -Math.log(1 - random.nextDouble()) / lambda;
        }
        return erlangValue;
    }
}
