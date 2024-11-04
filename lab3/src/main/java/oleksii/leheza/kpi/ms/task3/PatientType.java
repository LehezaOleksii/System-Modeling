package oleksii.leheza.kpi.ms.task3;

public enum PatientType {

    TYPE1(0.5, 15),
    TYPE2(0.1, 40),
    TYPE3(0.4, 30);

    private final double frequency;
    private final int registrationTime;

    PatientType(double frequency, int registrationTime) {
        this.frequency = frequency;
        this.registrationTime = registrationTime;
    }

    public double getFrequency() {
        return frequency;
    }

    public int getRegistrationTime() {
        return registrationTime;
    }
}