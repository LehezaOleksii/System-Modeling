package oleksii.leheza.kpi.ms.task3;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Patient extends HospitalElement {

    private static int patientId = 1;

    private int id;

    private PatientType type;
    private PatientStatus status;
    private double arrivalTime;
    private double registrationTime;
    private double totalTimeInSystem;

    public Patient(PatientType type, double registrationTime) {
        super("Patient ID " + patientId);
        id = patientId++;
        this.type = type;
        this.registrationTime = registrationTime;
        this.arrivalTime = getCurrentTime();
    }
}
