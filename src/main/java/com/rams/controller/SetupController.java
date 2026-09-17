package com.rams.controller;

import com.rams.entity.*;
import com.rams.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.constraints.NotBlank;

/**
 * Minimal setup screens so you can create Patients, Doctors and Resources
 * from the browser before running the /allocate flow. Swap for proper
 * registration forms per module (Patient, Doctor, Resource) as you expand.
 */
@Controller
public class SetupController {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ResourceRepository resourceRepository;

    public SetupController(PatientRepository patientRepository,
                            DoctorRepository doctorRepository,
                            ResourceRepository resourceRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.resourceRepository = resourceRepository;
    }

    @GetMapping("/setup")
    public String setupPage(Model model) {
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("doctors", doctorRepository.findAll());
        model.addAttribute("resources", resourceRepository.findAll());
        return "setup";
    }

    @PostMapping("/setup/patient")
    public String addPatient(@RequestParam @NotBlank String name, @RequestParam(required = false) String medicalHistory) {
        Patient p = new Patient();
        p.setName(name);
        p.setMedicalHistory(medicalHistory);
        patientRepository.save(p);
        return "redirect:/setup";
    }

    @PostMapping("/setup/doctor")
    public String addDoctor(@RequestParam @NotBlank String name, @RequestParam @NotBlank String specialization) {
        Doctor d = new Doctor();
        d.setName(name);
        d.setSpecialization(specialization);
        doctorRepository.save(d);
        return "redirect:/setup";
    }

    @PostMapping("/setup/resource")
    public String addResource(@RequestParam String type) {
        Resource r = new Resource();
        r.setType(type);
        r.setStatus(ResourceStatus.AVAILABLE);
        resourceRepository.save(r);
        return "redirect:/setup";
    }
}
