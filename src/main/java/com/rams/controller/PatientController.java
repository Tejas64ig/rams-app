package com.rams.controller;

import com.rams.entity.Patient;
import com.rams.repository.PatientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PatientController {

    private final PatientRepository patientRepository;

    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @GetMapping("/patients")
    public String list(Model model) {
        model.addAttribute("patients", patientRepository.findAll());
        return "patients";
    }

    @PostMapping("/patients")
    public String add(@RequestParam String name, @RequestParam(required = false) String medicalHistory) {
        Patient p = new Patient();
        p.setName(name);
        p.setMedicalHistory(medicalHistory);
        patientRepository.save(p);
        return "redirect:/patients";
    }

    @GetMapping("/patients/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("patient", patientRepository.findById(id).orElseThrow());
        return "patient-edit";
    }

    @PostMapping("/patients/{id}/edit")
    public String update(@PathVariable Integer id, @RequestParam String name, @RequestParam(required = false) String medicalHistory) {
        Patient p = patientRepository.findById(id).orElseThrow();
        p.setName(name);
        p.setMedicalHistory(medicalHistory);
        patientRepository.save(p);
        return "redirect:/patients";
    }

    @PostMapping("/patients/{id}/delete")
    public String delete(@PathVariable Integer id) {
        patientRepository.deleteById(id);
        return "redirect:/patients";
    }
}
