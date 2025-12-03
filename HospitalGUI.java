import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HospitalGUI extends JFrame {

    HospitalManager manager = new HospitalManager();

    // Tables and Models
    DefaultTableModel patientModel, doctorModel, apptModel;
    JTable patientTable, doctorTable, apptTable;

    public HospitalGUI() {
        setTitle("Hospital Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Null Layout

        initLoginScreen();
        setVisible(true);
    }

    private void initLoginScreen() {
        getContentPane().removeAll();
        repaint();

        JLabel userLabel = new JLabel("Username:"); userLabel.setBounds(250, 150, 100, 30); add(userLabel);
        JTextField userField = new JTextField(); userField.setBounds(350, 150, 150, 30); add(userField);

        JLabel passLabel = new JLabel("Password:"); passLabel.setBounds(250, 200, 100, 30); add(passLabel);
        JPasswordField passField = new JPasswordField(); passField.setBounds(350, 200, 150, 30); add(passField);

        JButton loginBtn = new JButton("Login"); loginBtn.setBounds(350, 250, 100, 30); add(loginBtn);
        JLabel hint = new JLabel("(admin / 1234)"); hint.setBounds(350, 290, 200, 20); add(hint);

        loginBtn.addActionListener(e -> {
            if (manager.checkLogin(userField.getText(), new String(passField.getPassword()))) {
                initMainDashboard();
            } else {
                JOptionPane.showMessageDialog(this, "Wrong details");
            }
        });
    }

    private void initMainDashboard() {
        getContentPane().removeAll();
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBounds(0, 0, 785, 560);

        tabs.add("Patients", createPatientPanel());
        tabs.add("Doctors", createDoctorPanel());
        tabs.add("Appointments", createApptPanel());
        tabs.add("Billing", createBillingPanel());

        add(tabs);
        repaint();
    }

    // --- PATIENT PANEL ---
    private JPanel createPatientPanel() {
        JPanel p = new JPanel(null);

        JLabel l1 = new JLabel("ID:"); l1.setBounds(20, 20, 80, 25); p.add(l1);
        JTextField tfId = new JTextField(); tfId.setBounds(80, 20, 100, 25); p.add(tfId);

        JLabel l2 = new JLabel("Name:"); l2.setBounds(200, 20, 80, 25); p.add(l2);
        JTextField tfName = new JTextField(); tfName.setBounds(250, 20, 120, 25); p.add(tfName);

        JLabel l3 = new JLabel("Age:"); l3.setBounds(390, 20, 80, 25); p.add(l3);
        JTextField tfAge = new JTextField(); tfAge.setBounds(430, 20, 50, 25); p.add(tfAge);

        JLabel l4 = new JLabel("Disease:"); l4.setBounds(500, 20, 80, 25); p.add(l4);
        JTextField tfDis = new JTextField(); tfDis.setBounds(560, 20, 100, 25); p.add(tfDis);

        // --- BUTTONS ---
        JButton btnAdd = new JButton("Add"); btnAdd.setBounds(20, 60, 80, 30); p.add(btnAdd);
        JButton btnUpd = new JButton("Update"); btnUpd.setBounds(110, 60, 80, 30); p.add(btnUpd);
        JButton btnDel = new JButton("Delete"); btnDel.setBounds(200, 60, 80, 30); p.add(btnDel);

        // NEW SEARCH BUTTON
        JButton btnSearch = new JButton("Search"); btnSearch.setBounds(290, 60, 80, 30); p.add(btnSearch);

        String[] columns = {"ID", "Name", "Age", "Disease"};
        patientModel = new DefaultTableModel(columns, 0);
        patientTable = new JTable(patientModel);
        JScrollPane scroll = new JScrollPane(patientTable);
        scroll.setBounds(20, 110, 740, 400);
        p.add(scroll);

        // --- ACTIONS ---
        btnAdd.addActionListener(e -> {
            try {
                manager.addPatient(new Patient(Integer.parseInt(tfId.getText()), tfName.getText(), Integer.parseInt(tfAge.getText()), tfDis.getText()));
                refreshPatientTable();
                tfId.setText(""); tfName.setText(""); tfAge.setText(""); tfDis.setText("");
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Invalid Input"); }
        });

        // NEW SEARCH ACTION
        btnSearch.addActionListener(e -> {
            try {
                Patient pObj = manager.searchPatient(Integer.parseInt(tfId.getText()));
                if (pObj != null) {
                    String msg = "--- PATIENT FOUND ---\n" +
                            "ID: " + pObj.getId() + "\n" +
                            "Name: " + pObj.getName() + "\n" +
                            "Age: " + pObj.getAge() + "\n" +
                            "Disease: " + pObj.getDisease();
                    JOptionPane.showMessageDialog(this, msg, "Search Result", JOptionPane.INFORMATION_MESSAGE);

                    // Optional: Auto-fill fields for easier editing
                    tfName.setText(pObj.getName());
                    tfAge.setText(String.valueOf(pObj.getAge()));
                    tfDis.setText(pObj.getDisease());
                } else {
                    JOptionPane.showMessageDialog(this, "No such patient found.");
                }
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Please enter a valid ID to search."); }
        });

        btnDel.addActionListener(e -> {
            try {
                manager.deletePatient(Integer.parseInt(tfId.getText()));
                refreshPatientTable();
                tfId.setText("");
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Enter ID to delete"); }
        });

        btnUpd.addActionListener(e -> {
            try {
                Patient pat = manager.searchPatient(Integer.parseInt(tfId.getText()));
                if(pat != null) {
                    pat.setName(tfName.getText());
                    pat.setAge(Integer.parseInt(tfAge.getText()));
                    pat.setDisease(tfDis.getText());
                    refreshPatientTable();
                    tfId.setText(""); tfName.setText(""); tfAge.setText(""); tfDis.setText("");
                } else { JOptionPane.showMessageDialog(this, "Patient not found"); }
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Invalid ID"); }
        });

        return p;
    }
    private void refreshPatientTable() {
        patientModel.setRowCount(0);
        for (Patient p : manager.getPatients()) {
            patientModel.addRow(new Object[]{p.getId(), p.getName(), p.getAge(), p.getDisease()});
        }
    }

    // --- DOCTOR PANEL ---
    // Replace the old createDoctorPanel with this one
    private JPanel createDoctorPanel() {
        JPanel p = new JPanel(null);

        JLabel l1 = new JLabel("ID:"); l1.setBounds(20, 20, 80, 25); p.add(l1);
        JTextField tfId = new JTextField(); tfId.setBounds(80, 20, 100, 25); p.add(tfId);

        JLabel l2 = new JLabel("Name:"); l2.setBounds(200, 20, 80, 25); p.add(l2);
        JTextField tfName = new JTextField(); tfName.setBounds(250, 20, 150, 25); p.add(tfName);

        JLabel l3 = new JLabel("Spec:"); l3.setBounds(420, 20, 80, 25); p.add(l3);
        JTextField tfSpec = new JTextField(); tfSpec.setBounds(470, 20, 150, 25); p.add(tfSpec);

        JButton btnAdd = new JButton("Add"); btnAdd.setBounds(20, 60, 80, 30); p.add(btnAdd);
        JButton btnUpd = new JButton("Update"); btnUpd.setBounds(110, 60, 80, 30); p.add(btnUpd);
        JButton btnDel = new JButton("Delete"); btnDel.setBounds(200, 60, 80, 30); p.add(btnDel);

        // NEW SEARCH BUTTON
        JButton btnSearch = new JButton("Search"); btnSearch.setBounds(290, 60, 80, 30); p.add(btnSearch);

        String[] columns = {"ID", "Name", "Specialization"};
        doctorModel = new DefaultTableModel(columns, 0);
        doctorTable = new JTable(doctorModel);
        JScrollPane scroll = new JScrollPane(doctorTable);
        scroll.setBounds(20, 110, 740, 400);
        p.add(scroll);

        // --- ACTIONS ---
        btnAdd.addActionListener(e -> {
            try {
                manager.addDoctor(new Doctor(Integer.parseInt(tfId.getText()), tfName.getText(), tfSpec.getText()));
                refreshDoctorTable();
                tfId.setText(""); tfName.setText(""); tfSpec.setText("");
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Invalid Input"); }
        });

        // NEW SEARCH ACTION
        btnSearch.addActionListener(e -> {
            try {
                Doctor dObj = manager.searchDoctor(Integer.parseInt(tfId.getText()));
                if (dObj != null) {
                    String msg = "--- DOCTOR FOUND ---\n" +
                            "ID: " + dObj.getId() + "\n" +
                            "Name: " + dObj.getName() + "\n" +
                            "Specialization: " + dObj.getSpecialization();
                    JOptionPane.showMessageDialog(this, msg, "Search Result",
                            JOptionPane.INFORMATION_MESSAGE);

                    tfName.setText(dObj.getName());
                    tfSpec.setText(dObj.getSpecialization());
                } else {
                    JOptionPane.showMessageDialog(this, "No such doctor found.");
                }
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Please enter a valid ID to search."); }
        });

        btnDel.addActionListener(e -> {
            try {
                manager.deleteDoctor(Integer.parseInt(tfId.getText()));
                refreshDoctorTable();
                tfId.setText("");
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Enter ID to delete"); }
        });

        btnUpd.addActionListener(e -> {
            try {
                Doctor d = manager.searchDoctor(Integer.parseInt(tfId.getText()));
                if(d != null) {
                    d.setName(tfName.getText());
                    d.setSpecialization(tfSpec.getText());
                    refreshDoctorTable();
                    tfId.setText(""); tfName.setText(""); tfSpec.setText("");
                } else { JOptionPane.showMessageDialog(this, "Doctor not found"); }
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Invalid ID"); }
        });

        return p;
    }
    // Add this helper method to refresh the table (paste inside HospitalGUI class)
    private void refreshDoctorTable() {
        doctorModel.setRowCount(0);
        for (Doctor d : manager.getDoctors()) {
            doctorModel.addRow(new Object[]{d.getId(), d.getName(), d.getSpecialization()});
        }
    }

    // --- APPOINTMENT PANEL ---
    private JPanel createApptPanel() {
        JPanel p = new JPanel(null);

        JLabel l1 = new JLabel("Appt ID:"); l1.setBounds(20, 20, 80, 25); p.add(l1);
        JTextField tfId = new JTextField(); tfId.setBounds(100, 20, 100, 25); p.add(tfId);

        JLabel l2 = new JLabel("Patient Name:"); l2.setBounds(220, 20, 100, 25); p.add(l2);
        JTextField tfPName = new JTextField(); tfPName.setBounds(310, 20, 120, 25); p.add(tfPName);

        JLabel l3 = new JLabel("Doctor Name:"); l3.setBounds(440, 20, 100, 25); p.add(l3);
        JTextField tfDName = new JTextField(); tfDName.setBounds(530, 20, 120, 25); p.add(tfDName);

        JLabel l4 = new JLabel("Date:"); l4.setBounds(20, 60, 50, 25); p.add(l4);
        JTextField tfDate = new JTextField(); tfDate.setBounds(80, 60, 100, 25); p.add(tfDate);

        JButton btnBook = new JButton("Book"); btnBook.setBounds(200, 60, 100, 30); p.add(btnBook);

        String[] columns = {"Appt ID", "Patient", "Doctor", "Date"};
        apptModel = new DefaultTableModel(columns, 0);
        apptTable = new JTable(apptModel);
        JScrollPane scroll = new JScrollPane(apptTable);
        scroll.setBounds(20, 110, 740, 400);
        p.add(scroll);

        btnBook.addActionListener(e -> {
            try {
                manager.bookAppointment(Integer.parseInt(tfId.getText()), tfPName.getText(), tfDName.getText(), tfDate.getText());
                apptModel.addRow(new Object[]{tfId.getText(), tfPName.getText(), tfDName.getText(), tfDate.getText()});
                // CLEAR FIELDS
                tfId.setText(""); tfPName.setText(""); tfDName.setText(""); tfDate.setText("");
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Invalid Appt ID"); }
        });

        return p;
    }

    // --- BILLING PANEL ---
    private JPanel createBillingPanel() {
        JPanel p = new JPanel(null);

        JLabel l1 = new JLabel("Patient ID for Bill:"); l1.setBounds(20, 20, 150, 25); p.add(l1);
        JTextField tfId = new JTextField(); tfId.setBounds(180, 20, 100, 25); p.add(tfId);
        JButton btnGen = new JButton("Generate"); btnGen.setBounds(300, 20, 100, 25); p.add(btnGen);

        JTextArea area = new JTextArea();
        area.setBounds(20, 70, 400, 300);
        area.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));
        area.setEditable(false);
        p.add(area);

        btnGen.addActionListener(e -> {
            try {
                area.setText(manager.generateBill(Integer.parseInt(tfId.getText())));
                tfId.setText(""); // Clear ID after generation
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Invalid ID"); }
        });

        return p;
    }

    public static void main(String[] args) {
        new HospitalGUI();
    }
}