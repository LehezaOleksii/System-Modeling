package oleksii.leheza.kpi.ms.task3;

import lombok.Getter;
import lombok.Setter;

import java.util.Deque;

@Getter
@Setter
public class Doctor extends HospitalElement {

    @Setter
    public static Deque<Patient> patientsRegistrationQueue;

    private Patient currentPatient;

    public Doctor(String name) {
        super(name);
    }

    public void treatPatient(Patient patient) {
        isBusy = true;
        currentPatient = patient;
        setNextEventTime(getCurrentTime() + patient.getRegistrationTime());
        System.out.println(patient.getName() + " treat by a " + name);
    }

    public void releasePatient() {
        isBusy = false;
        System.out.println(currentPatient.getName() + " END treating by a " + name);
        if (!patientsRegistrationQueue.isEmpty()) {
            Patient nextPatient = patientsRegistrationQueue.pop();
            treatPatient(nextPatient);
        } else {
            setNextEventTime(Double.MAX_VALUE);
            System.out.println(name + " is free");
        }
    }
}
