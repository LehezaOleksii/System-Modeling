package oleksii.leheza.kpi.ms.task3;

import lombok.Getter;
import lombok.Setter;

import java.util.Deque;

@Getter
@Setter
public class Accompanying extends HospitalElement {
    @Setter
    public static Deque<Patient> queueForAccompaniment;

    private Patient currentPatient;

    public Accompanying(String name) {
        super(name);
    }

    public void accompanyToRoom(Patient patient) {
        isBusy = true;
        double transferTime = 3 + Math.random() * 5;
        currentPatient = patient;
        setNextEventTime(getCurrentTime() + transferTime);
        System.out.println("Patient ID " + patient.getId() + " sent to the ward by " + name);
//        patient.setTotalTimeInSystem(patient.getTotalTimeInSystem() + transferTime);
    }

    public void release() {
        isBusy = false;
        if (!queueForAccompaniment.isEmpty()) {
            Patient nextPatient = queueForAccompaniment.pop();
            accompanyToRoom(nextPatient);
        } else {
            setNextEventTime(Double.MAX_VALUE);
            System.out.println(name + " is free");
        }
        System.out.println("Patient ID " + currentPatient.getId() + " finished the hospital session\n" + "---------------------------------------------------");
    }
}