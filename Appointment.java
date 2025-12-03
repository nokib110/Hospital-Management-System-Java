public class Appointment {
    private int id;
    private String patientName;
    private String doctorName;
    private String date;

    public Appointment(int id, String pName, String dName, String date) {
        this.id = id;
        this.patientName = pName;
        this.doctorName = dName;
        this.date = date;
    }

    // Getters needed for JTable
    public int getId() { return id; }
    public String getPatientName() { return patientName; }
    public String getDoctorName() { return doctorName; }
    public String getDate() { return date; }
}