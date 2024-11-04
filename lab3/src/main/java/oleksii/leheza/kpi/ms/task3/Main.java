package oleksii.leheza.kpi.ms.task3;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        int simulateTime = 500;

        Deque<Patient> patientsRegistrationQueue = new LinkedList<>();
        Deque<Patient> queueForAccompaniment = new LinkedList<>();
        Deque<Patient> patientsLaboratoryAssistantQueue = new LinkedList<>();

        Doctor doctor1 = new Doctor("Doctor 1");
        Doctor doctor2 = new Doctor("Doctor 2");
        Doctor.setPatientsRegistrationQueue(patientsRegistrationQueue);

        Accompanying accompanying1 = new Accompanying("Accompanying 1");
        Accompanying accompanying2 = new Accompanying("Accompanying 2");
        Accompanying accompanying3 = new Accompanying("Accompanying 3");
        Accompanying.setQueueForAccompaniment(queueForAccompaniment);

        LaboratoryAssistant labAssistant1 = new LaboratoryAssistant("Assistant 1");
        LaboratoryAssistant labAssistant2 = new LaboratoryAssistant("Assistant 2");
        LaboratoryAssistant.setPatientsLaboratoryAssistantQueue(patientsLaboratoryAssistantQueue);

        PatientGenerator generator = new PatientGenerator("Generator", 15);

        List<HospitalElement> elements = new ArrayList<>();
        elements.add(doctor1);
        elements.add(doctor2);
        elements.add(accompanying1);
        elements.add(accompanying2);
        elements.add(accompanying3);
        elements.add(labAssistant1);
        elements.add(labAssistant2);
        elements.add(generator);

        Hospital hospital = new Hospital(elements);
        Hospital.setPatientsRegistrationQueue(patientsRegistrationQueue);
        Hospital.setPatientsLaboratoryAssistantQueue(patientsLaboratoryAssistantQueue);
        Hospital.setQueueForAccompaniment(queueForAccompaniment);

        hospital.simulate(simulateTime);


        List<Double> times = hospital.getArrivalLabTimes();
        Double lastTime = times.get(1);
        double sum = lastTime;
        int labVisiting = 1;
        for (int i = 2; i < times.size(); i++) {
            double time = times.get(i);
            sum += time - lastTime;
            lastTime = time;
            labVisiting++;
        }
        System.out.println("----------Statistic----------\n" +
                "patients: " + hospital.getPatientTreated() + "\n" +
                "Average patient time in the hospital: " + hospital.getClientTotalTime() / (double) hospital.getPatientTreated() + "\n" +
                "Average clients arrival to laboratory: " + sum / (double) labVisiting);
    }
}
