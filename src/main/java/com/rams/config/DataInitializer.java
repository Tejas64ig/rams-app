package com.rams.config;

import com.rams.entity.*;
import com.rams.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Loads sample hospital data on startup based on configuration.
 * Covers all modules from the SRS: patients, doctors, nurses,
 * departments, resources, appointments, and treatments.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Value("${rams.load-test-data:false}")
    private boolean loadTestDataFromConfig;

    private final HospitalRepository hospitalRepository;
    private final DepartmentRepository departmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final NurseRepository nurseRepository;
    private final ResourceRepository resourceRepository;
    private final AppointmentRepository appointmentRepository;
    private final TreatmentRepository treatmentRepository;

    public DataInitializer(HospitalRepository hospitalRepository,
                           DepartmentRepository departmentRepository,
                           PatientRepository patientRepository,
                           DoctorRepository doctorRepository,
                           NurseRepository nurseRepository,
                           ResourceRepository resourceRepository,
                           AppointmentRepository appointmentRepository,
                           TreatmentRepository treatmentRepository) {
        this.hospitalRepository = hospitalRepository;
        this.departmentRepository = departmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.nurseRepository = nurseRepository;
        this.resourceRepository = resourceRepository;
        this.appointmentRepository = appointmentRepository;
        this.treatmentRepository = treatmentRepository;
    }

    @Override
    public void run(String... args) {
        // Check if data already exists
        if (hospitalRepository.count() > 0) {
            System.out.println("\n⚠️  Database already contains data. Skipping test data initialization.");
            return;
        }

        // Check configuration or prompt user
        boolean shouldLoadTestData = shouldLoadTestData();

        if (!shouldLoadTestData) {
            System.out.println("\n✅ Starting with empty database. You can add data through the UI.");
            return;
        }

        System.out.println("\n🔄 Loading test data...");
        loadSampleData();
    }

    private boolean shouldLoadTestData() {
        // First check the configuration property
        if (loadTestDataFromConfig) {
            System.out.println("\n📋 Test data loading enabled in configuration (rams.load-test-data=true)");
            return true;
        }

        // If not enabled in config, prompt user
        System.out.println("\n" + "=".repeat(80));
        System.out.println("🗂️  TEST DATA INITIALIZATION");
        System.out.println("=".repeat(80));
        System.out.println("Would you like to load sample test data?");
        System.out.println("(1 hospital, 5 departments, 5 doctors, 5 nurses, 7 patients, 16 resources, etc.)");
        System.out.println();
        System.out.println("Options:");
        System.out.println("  [Y/y] - Yes, load test data");
        System.out.println("  [N/n] - No, start with empty database");
        System.out.println("=".repeat(80));
        System.out.print("Your choice (Y/N): ");

        try {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine().trim().toLowerCase();
            return input.equals("y") || input.equals("yes");
        } catch (Exception e) {
            System.out.println("\n⚠️  Could not read input. Starting with empty database.");
            return false;
        }
    }

    private void loadSampleData() {
        // ──────────────── Hospital ────────────────
        Hospital hospital = new Hospital();
        hospital.setName("City Multi-Speciality Hospital");
        hospitalRepository.save(hospital);

        // ──────────────── Departments ────────────────
        Department cardiology = makeDept("Cardiology", hospital);
        Department orthopedics = makeDept("Orthopedics", hospital);
        Department neurology = makeDept("Neurology", hospital);
        Department emergency = makeDept("Emergency", hospital);
        Department pediatrics = makeDept("Pediatrics", hospital);

        // ──────────────── Doctors ────────────────
        Doctor drSharma   = makeDoctor("Dr. Rajesh Sharma", "Cardiologist", cardiology);
        Doctor drPatel    = makeDoctor("Dr. Priya Patel", "Orthopedic Surgeon", orthopedics);
        Doctor drMehra    = makeDoctor("Dr. Anil Mehra", "Neurologist", neurology);
        Doctor drKhan     = makeDoctor("Dr. Farah Khan", "Emergency Medicine", emergency);
        Doctor drDeshmukh = makeDoctor("Dr. Sneha Deshmukh", "Pediatrician", pediatrics);

        // ──────────────── Nurses ────────────────
        Nurse n1 = makeNurse("Nurse Anjali Verma", true);
        Nurse n2 = makeNurse("Nurse Pooja Singh", true);
        Nurse n3 = makeNurse("Nurse Meena Kulkarni", true);
        Nurse n4 = makeNurse("Nurse Rekha Joshi", false);  // off-shift
        Nurse n5 = makeNurse("Nurse Suman Tiwari", true);

        // ──────────────── Patients ────────────────
        Patient p1 = makePatient("Amit Kumar", "Hypertension, Diabetes Type-2");
        Patient p2 = makePatient("Sunita Reddy", "Fractured left femur from road accident");
        Patient p3 = makePatient("Rahul Jain", "Chronic migraine, MRI pending");
        Patient p4 = makePatient("Kavita Nair", "Chest pain, ECG normal, under observation");
        Patient p5 = makePatient("Vikram Thakur", "Asthma since childhood");
        Patient p6 = makePatient("Neha Gupta", "Pregnancy – 32 weeks, routine checkup");
        Patient p7 = makePatient("Arjun Malhotra", "Sports injury – torn ACL");

        // ──────────────── Resources ────────────────
        // ICU Beds
        Resource icuBed1 = makeResource("ICU Bed", ResourceStatus.AVAILABLE, cardiology);
        Resource icuBed2 = makeResource("ICU Bed", ResourceStatus.ALLOCATED, cardiology);
        Resource icuBed3 = makeResource("ICU Bed", ResourceStatus.AVAILABLE, emergency);

        // General Ward Beds
        Resource genBed1 = makeResource("General Ward Bed", ResourceStatus.AVAILABLE, orthopedics);
        Resource genBed2 = makeResource("General Ward Bed", ResourceStatus.ALLOCATED, pediatrics);
        Resource genBed3 = makeResource("General Ward Bed", ResourceStatus.AVAILABLE, neurology);

        // Operation Theatres
        Resource ot1 = makeResource("Operation Theatre", ResourceStatus.AVAILABLE, orthopedics);
        Resource ot2 = makeResource("Operation Theatre", ResourceStatus.ALLOCATED, cardiology);

        // Equipment
        Resource ventilator1 = makeResource("Ventilator", ResourceStatus.AVAILABLE, emergency);
        Resource ventilator2 = makeResource("Ventilator", ResourceStatus.ALLOCATED, cardiology);
        Resource mri = makeResource("MRI Machine", ResourceStatus.AVAILABLE, neurology);
        Resource xray = makeResource("X-Ray Machine", ResourceStatus.AVAILABLE, orthopedics);
        Resource ecg = makeResource("ECG Monitor", ResourceStatus.AVAILABLE, cardiology);
        Resource wheelchair = makeResource("Wheelchair", ResourceStatus.AVAILABLE, null);

        // Ambulances
        Resource ambulance1 = makeResource("Ambulance", ResourceStatus.AVAILABLE, emergency);
        Resource ambulance2 = makeResource("Ambulance", ResourceStatus.ALLOCATED, emergency);

        // ──────────────── Appointments (pre-existing) ────────────────
        makeAppointment(p1, drSharma, LocalDateTime.now().minusDays(2), true);
        makeAppointment(p2, drPatel, LocalDateTime.now().minusDays(1), true);
        makeAppointment(p3, drMehra, LocalDateTime.now().minusHours(3), true);
        makeAppointment(p4, drKhan, LocalDateTime.now().minusHours(1), true);
        makeAppointment(p5, drDeshmukh, LocalDateTime.now().plusHours(2), false);  // upcoming
        makeAppointment(p6, drDeshmukh, LocalDateTime.now().plusDays(1), false);   // upcoming
        makeAppointment(p7, drPatel, LocalDateTime.now().plusDays(2), false);      // upcoming

        // ──────────────── Treatments (completed ones) ────────────────
        makeTreatment("Angioplasty – stent placed in LAD artery", drSharma, p1, true);
        makeTreatment("Femur fracture reduction and cast application", drPatel, p2, true);
        makeTreatment("MRI Brain Scan + prescribed Sumatriptan", drMehra, p3, true);
        makeTreatment("Emergency triage – chest pain evaluation", drKhan, p4, true);
        makeTreatment("Nebulization therapy for acute asthma attack", drDeshmukh, p5, false); // pending

        // Mark the busy doctor
        drSharma.setBusy(true);
        doctorRepository.save(drSharma);

        // Assign nurses to patients
        n1.attendPatient(p1);
        nurseRepository.save(n1);
        n2.attendPatient(p2);
        nurseRepository.save(n2);
        n3.attendPatient(p4);
        nurseRepository.save(n3);

        System.out.println("\n✅ Sample data loaded: 1 hospital, 5 departments, 5 doctors, 5 nurses, 7 patients, 16 resources, 7 appointments, 5 treatments\n");
    }

    // ──────────────── Helper methods ────────────────

    private Department makeDept(String name, Hospital hospital) {
        Department d = new Department();
        d.setName(name);
        d.setHospital(hospital);
        return departmentRepository.save(d);
    }

    private Doctor makeDoctor(String name, String specialization, Department dept) {
        Doctor d = new Doctor();
        d.setName(name);
        d.setSpecialization(specialization);
        d.setDepartment(dept);
        return doctorRepository.save(d);
    }

    private Nurse makeNurse(String name, boolean onShift) {
        Nurse n = new Nurse();
        n.setName(name);
        n.setOnShift(onShift);
        return nurseRepository.save(n);
    }

    private Patient makePatient(String name, String history) {
        Patient p = new Patient();
        p.setName(name);
        p.setMedicalHistory(history);
        return patientRepository.save(p);
    }

    private Resource makeResource(String type, ResourceStatus status, Department dept) {
        Resource r = new Resource();
        r.setType(type);
        r.setStatus(status);
        r.setDepartment(dept);
        return resourceRepository.save(r);
    }

    private void makeAppointment(Patient patient, Doctor doctor, LocalDateTime date, boolean confirmed) {
        Appointment a = new Appointment();
        a.setPatient(patient);
        a.setDoctor(doctor);
        a.setDate(date);
        a.setConfirmed(confirmed);
        appointmentRepository.save(a);
    }

    private void makeTreatment(String desc, Doctor doctor, Patient patient, boolean applied) {
        Treatment t = new Treatment();
        t.setDescription(desc);
        t.setDoctor(doctor);
        t.setPatient(patient);
        t.setApplied(applied);
        treatmentRepository.save(t);
    }
}
