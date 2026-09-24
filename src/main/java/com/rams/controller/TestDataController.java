package com.rams.controller;

import com.rams.config.DataInitializer;
import com.rams.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TestDataController {

    private final HospitalRepository hospitalRepository;
    private final DepartmentRepository departmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final NurseRepository nurseRepository;
    private final ResourceRepository resourceRepository;
    private final AppointmentRepository appointmentRepository;
    private final TreatmentRepository treatmentRepository;
    private final DataInitializer dataInitializer;

    public TestDataController(HospitalRepository hospitalRepository,
                              DepartmentRepository departmentRepository,
                              PatientRepository patientRepository,
                              DoctorRepository doctorRepository,
                              NurseRepository nurseRepository,
                              ResourceRepository resourceRepository,
                              AppointmentRepository appointmentRepository,
                              TreatmentRepository treatmentRepository,
                              DataInitializer dataInitializer) {
        this.hospitalRepository = hospitalRepository;
        this.departmentRepository = departmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.nurseRepository = nurseRepository;
        this.resourceRepository = resourceRepository;
        this.appointmentRepository = appointmentRepository;
        this.treatmentRepository = treatmentRepository;
        this.dataInitializer = dataInitializer;
    }

    @GetMapping("/test-data")
    public String testDataPage(Model model) {
        // Check if test data exists
        boolean hasData = hospitalRepository.count() > 0;
        
        model.addAttribute("hasData", hasData);
        model.addAttribute("hospitalCount", hospitalRepository.count());
        model.addAttribute("departmentCount", departmentRepository.count());
        model.addAttribute("patientCount", patientRepository.count());
        model.addAttribute("doctorCount", doctorRepository.count());
        model.addAttribute("nurseCount", nurseRepository.count());
        model.addAttribute("resourceCount", resourceRepository.count());
        model.addAttribute("appointmentCount", appointmentRepository.count());
        model.addAttribute("treatmentCount", treatmentRepository.count());
        
        return "test-data";
    }

    @PostMapping("/test-data/load")
    public String loadTestData(RedirectAttributes redirectAttributes) {
        // Check if data already exists
        if (hospitalRepository.count() > 0) {
            redirectAttributes.addFlashAttribute("error", 
                "Test data already exists. Clear database first to reload.");
            return "redirect:/test-data";
        }

        try {
            // Load test data programmatically
            dataInitializer.run();
            redirectAttributes.addFlashAttribute("success", 
                "Test data loaded successfully! 1 hospital, 5 departments, 5 doctors, 5 nurses, 7 patients, 16 resources, 7 appointments, 5 treatments.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error loading test data: " + e.getMessage());
        }

        return "redirect:/test-data";
    }

    @PostMapping("/test-data/clear")
    public String clearTestData(RedirectAttributes redirectAttributes) {
        try {
            // Delete in proper order to avoid foreign key constraints
            treatmentRepository.deleteAll();
            appointmentRepository.deleteAll();
            resourceRepository.deleteAll();
            nurseRepository.deleteAll();
            doctorRepository.deleteAll();
            patientRepository.deleteAll();
            departmentRepository.deleteAll();
            hospitalRepository.deleteAll();

            redirectAttributes.addFlashAttribute("success", 
                "All test data cleared successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error clearing test data: " + e.getMessage());
        }

        return "redirect:/test-data";
    }
}
