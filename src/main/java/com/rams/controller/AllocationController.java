package com.rams.controller;

import com.rams.repository.DoctorRepository;
import com.rams.repository.PatientRepository;
import com.rams.service.AllocationResult;
import com.rams.service.TreatmentAllocationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AllocationController {

    private final TreatmentAllocationService allocationService;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AllocationController(TreatmentAllocationService allocationService,
                                 PatientRepository patientRepository,
                                 DoctorRepository doctorRepository) {
        this.allocationService = allocationService;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    /** Shows the "request treatment" form with live patient/doctor dropdowns. */
    @GetMapping("/allocate")
    public String showForm(Model model) {
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("doctors", doctorRepository.findAll());
        return "allocate"; // -> templates/allocate.html
    }

    /** Runs the full sequence-diagram flow and shows success or resourceNotFound(). */
    @PostMapping("/allocate")
    public String submit(@RequestParam Integer patientId,
                          @RequestParam Integer doctorId,
                          @RequestParam String treatmentDescription,
                          @RequestParam String resourceType,
                          Model model) {

        AllocationResult result = allocationService.allocateTreatment(
                patientId, doctorId, treatmentDescription, resourceType);

        model.addAttribute("success", result.isSuccess());
        model.addAttribute("message", result.getMessage());
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("doctors", doctorRepository.findAll());
        return "allocate";
    }
}
