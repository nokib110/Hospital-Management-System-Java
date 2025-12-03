import java.util.ArrayList;

public class HospitalManager {
    private ArrayList<Doctor> doctors = new ArrayList<>();
    private ArrayList<Patient> patients = new ArrayList<>();
    private ArrayList<Appointment> appointments = new ArrayList<>();

    public boolean checkLogin(String user, String pass) {
        return user.equals("admin") && pass.equals("1234");
    }

    // Patients
    public void addPatient(Patient p) { patients.add(p); }
    public ArrayList<Patient> getPatients() { return patients; }
    public Patient searchPatient(int id) {
        for(Patient p : patients) if(p.getId() == id) return p;
        return null;
    }
    public void deletePatient(int id) {
        patients.removeIf(p -> p.getId() == id);
    }

    // Doctors
    public void addDoctor(Doctor d) { doctors.add(d); }
    // Add these methods inside your HospitalManager class if they are missing
    public Doctor searchDoctor(int id) {
        for(Doctor d : doctors) {
            if(d.getId() == id) return d;
        }
        return null;
    }

    public void deleteDoctor(int id) {
        doctors.removeIf(d -> d.getId() == id);
    }
    public ArrayList<Doctor> getDoctors() { return doctors; }

    // Appointments
    public void bookAppointment(int id, String pName, String dName, String date) {
        appointments.add(new Appointment(id, pName, dName, date));
    }
    public ArrayList<Appointment> getAppointments() { return appointments; }

    // Billing
    // Inside HospitalManager.java

    public String generateBill(int patientId) {
        Patient p = searchPatient(patientId);

        if (p == null) {
            return "Error: Patient with ID " + patientId + " not found.";
        }

        // 1. Determine Basic Consultation Fee
        double basicFee = 300.00;
        double treatmentCost = 0.00;

        // 2. Calculate Cost based on Disease (Simple Logic)
        // You can change these disease names to match what you type in the app
        String disease = p.getDisease().toLowerCase();

        switch (disease) {
            case "fever":
            case "flu":
                treatmentCost = 100.00;
                break;
            case "heart":
            case "cardiac":
                treatmentCost = 5000.00;
                break;
            case "fracture":
            case "bone":
                treatmentCost = 1500.00;
                break;
            default:
                treatmentCost = 500.00; // General treatment cost
                break;
        }

        double totalAmount = basicFee + treatmentCost;

        // 3. Generate a Receipt String
        StringBuilder bill = new StringBuilder();
        bill.append("       HOSPITAL BILL          \n");
        bill.append("==============================\n");
        bill.append("Patient ID  : " + p.getId() + "\n");
        bill.append("Patient Name: " + p.getName() + "\n");
        bill.append("Disease     : " + p.getDisease() + "\n");
        bill.append("------------------------------\n");
        bill.append("Consultation Fee : $" + basicFee + "\n");
        bill.append("Treatment Cost   : $" + treatmentCost + "\n");
        bill.append("------------------------------\n");
        bill.append("TOTAL AMOUNT     : $" + totalAmount + "\n");

        return bill.toString();
    }
}