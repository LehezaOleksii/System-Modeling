package oleksii.leheza.kpi.ms.task3;

import lombok.Getter;
import lombok.Setter;

import java.util.Deque;

@Getter
@Setter
public class LaboratoryAssistant extends HospitalElement {
    @Setter
    public static Deque<Patient> patientsLaboratoryAssistantQueue;

    private ErlangDistribution erlangDistribution = new ErlangDistribution();

    private Patient currentPatient;

    public LaboratoryAssistant(String name) {
        super(name);
    }

    public void treatPatient(Patient patient) {
        isBusy = true;
        currentPatient = patient;
        setNextEventTime(getCurrentTime() + erlangDistribution.getNumber(4, 2));
        System.out.println(patient.getName() + " treat by a " + name);
    }

    public void releasePatient() {
        isBusy = false;
        if (!patientsLaboratoryAssistantQueue.isEmpty()) {
            Patient nextPatient = patientsLaboratoryAssistantQueue.pop();
            treatPatient(nextPatient);
        } else {
            setNextEventTime(Double.MAX_VALUE);
            System.out.println(name + " is free");
        }
    }
}