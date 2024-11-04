package oleksii.leheza.kpi.ms.task3;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Random;

@Getter
@Setter
public class Hospital {

    private final ErlangDistribution erlangDistribution = new ErlangDistribution();
    @Setter
    private static Deque<Patient> patientsRegistrationQueue;
    @Setter
    private static Deque<Patient> queueForAccompaniment;
    @Setter
    private static Deque<Patient> patientsLaboratoryAssistantQueue;

    private double clientTotalTime;
    private int patientTreated;

    private double currentTime;
    private double nextTime;
    private HospitalElement currentElement;
    private List<HospitalElement> elements;
    private Random random = new Random();

    private List<Double> arrivalLabTimes = new ArrayList<>();
    private int arrivalLabNum;

    public Hospital(List<HospitalElement> elements) {
        this.elements = elements;
    }

    public void simulate(double modelTime) {
        while (currentTime < modelTime) {
            nextTime = Double.MAX_VALUE;
            for (HospitalElement e : elements) {
                if (e.getNextEventTime() < nextTime) {
                    nextTime = e.getNextEventTime();
                    currentElement = e;
                }
            }
            currentTime = nextTime;
            for (HospitalElement e : elements) {
                e.setCurrentTime(currentTime);
            }
            if (currentElement instanceof PatientGenerator) {
                Patient patient = ((PatientGenerator) currentElement).generatePatient(currentTime);
                System.out.println("Generated Patient: " + patient.getId() + " " + patient.getType());
                registerPatientToHospital(patient);
            } else if (currentElement instanceof Doctor doctor) {
                Patient currentPatient = doctor.getCurrentPatient();
                doctor.releasePatient();
                if (currentPatient.getType().equals(PatientType.TYPE1)) {
                    sendToWard(currentPatient);
                } else {
                    sendToLaboratory(currentPatient);
                }
            } else if (currentElement instanceof Accompanying accompanying) {
                Patient currentPatient = accompanying.getCurrentPatient();
                accompanying.release();
                double totalTime = currentTime - currentPatient.getArrivalTime();
                currentPatient.setTotalTimeInSystem(totalTime);
                System.out.println("Total patient " + currentPatient.getId() + " time :" + totalTime);
                clientTotalTime += currentPatient.getTotalTimeInSystem();
                patientTreated++;
            } else if (currentElement instanceof LaboratoryAssistant laboratoryAssistant) {
                double randomValue = random.nextDouble();
                Patient patient = laboratoryAssistant.getCurrentPatient();
                if (randomValue <= 0.5) {
                    double totalTime = currentTime - patient.getArrivalTime();
                    patient.setTotalTimeInSystem(totalTime);
                    System.out.println("Total patient " + patient.getId() + " time :" + totalTime);
                    clientTotalTime += patient.getTotalTimeInSystem();
                    patientTreated++;
                    patient.setType(PatientType.TYPE1);
                    patientsRegistrationQueue.add(patient);
                    System.out.println("Patient ID " + patient.getId() + " return to the hospital\n" + "---------------------------------------------------");
                } else {
                    System.out.println("Patient ID " + patient.getId() + " finished the hospital session\n" + "---------------------------------------------------");
                }
                laboratoryAssistant.releasePatient();
            } else if (currentElement instanceof Patient patient) {
                if (patient.getStatus().equals(PatientStatus.HEAD_TO_LABORATORY)) {
                    arrivalLabTimes.add(currentTime);
                    arrivalLabNum += 1;
                    sendToRegistryOffice(patient);
                } else if (patient.getStatus().equals(PatientStatus.IN_REGISTRY_OFFICE)) {
                    assignToLaboratoryAssistant(patient);
                    if (elements.contains(patient)) {
                        elements.remove(patient);
                    }
                }
            }
        }
    }

    private void registerPatientToHospital(Patient patient) {
        List<Doctor> doctors = elements.stream().filter(e -> e instanceof Doctor).map(e -> (Doctor) e).toList();

        for (Doctor doctor : doctors) {
            if (!doctor.isBusy()) {
                doctor.treatPatient(patient);
                return;
            }
        }
        patientsRegistrationQueue.add(patient);
    }

    private void sendToWard(Patient patient) {
        List<Accompanying> accompanyings = elements.stream().filter(e -> e instanceof Accompanying).map(e -> (Accompanying) e).toList();
        for (Accompanying accompanying : accompanyings) {
            if (!accompanying.isBusy()) {
                accompanying.accompanyToRoom(patient);
                return;
            }
        }
        queueForAccompaniment.add(patient);
    }

    private void sendToLaboratory(Patient patient) {
        double transferTime = 2 + Math.random() * 3;
        patient.setNextEventTime(currentTime + transferTime);
        patient.setStatus(PatientStatus.HEAD_TO_LABORATORY);
        System.out.println("Patient ID " + patient.getId() + " sent to the laboratory");
        elements.add(patient);
    }

    private void sendToRegistryOffice(Patient patient) {
        double time = erlangDistribution.getNumber(4.5, 3);
        patient.setNextEventTime(currentTime + time);
        patient.setStatus(PatientStatus.IN_REGISTRY_OFFICE);
        System.out.println("Patient ID " + patient.getId() + " sent to the registry office");
    }

    private void assignToLaboratoryAssistant(Patient patient) {
        List<LaboratoryAssistant> assistants = elements.stream().filter(e -> e instanceof LaboratoryAssistant).map(e -> (LaboratoryAssistant) e).toList();
        for (LaboratoryAssistant assistant : assistants) {
            if (!assistant.isBusy()) {
                System.out.println("Patient ID " + patient.getId() + " assigned to " + assistant.getName());
                assistant.treatPatient(patient);
                return;
            }
        }
        patientsLaboratoryAssistantQueue.add(patient);
    }
}

