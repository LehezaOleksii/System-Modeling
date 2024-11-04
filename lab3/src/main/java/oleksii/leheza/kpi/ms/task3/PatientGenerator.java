package oleksii.leheza.kpi.ms.task3;

public class PatientGenerator extends HospitalElement {

    private double type1Frequency = PatientType.TYPE1.getFrequency();
    private int type1RegistrationTime = PatientType.TYPE1.getRegistrationTime();
    private double type2Frequency = PatientType.TYPE2.getFrequency();
    private int type2RegistrationTime = PatientType.TYPE2.getRegistrationTime();
    private int type3RegistrationTime = PatientType.TYPE3.getRegistrationTime();

    private double clientArrivalFrequency;

    public PatientGenerator(String name, double clientArrivalFrequency) {
        super(name);
        this.clientArrivalFrequency = clientArrivalFrequency;
        nextEventTime = 0;
    }

    public Patient generatePatient(double currentTime) {
        PatientType patientType = generatePatientType();
        setNextEventTime(getCurrentTime() + generateTime(clientArrivalFrequency));
        double patientRegistartionTime = getRegistrationTime(patientType);
        return new Patient(patientType, patientRegistartionTime, currentTime);
    }

    private PatientType generatePatientType() {
        double randomValue = Math.random();
        if (randomValue < type1Frequency) {
            return PatientType.TYPE1;
        } else if (randomValue < type1Frequency + type2Frequency) {
            return PatientType.TYPE2;
        } else {
            return PatientType.TYPE3;
        }
    }

    private int getRegistrationTime(PatientType patientType) {
        return switch (patientType) {
            case PatientType.TYPE1 -> type1RegistrationTime;
            case PatientType.TYPE2 -> type2RegistrationTime;
            case PatientType.TYPE3 -> type3RegistrationTime;
            default -> throw new IllegalArgumentException("Unknown patient type: " + patientType);
        };
    }

    public double generateTime(double timeMean) {
        double a = 0;
        while (a == 0) {
            a = Math.random();
        }
        a = -timeMean * Math.log(a);
        return a;
    }
}