package com.rams.controller;

import com.rams.repository.TreatmentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TreatmentController {

    private final TreatmentRepository treatmentRepository;

    public TreatmentController(TreatmentRepository treatmentRepository) {
        this.treatmentRepository = treatmentRepository;
    }

    @GetMapping("/treatments")
    public String list(Model model) {
        model.addAttribute("treatments", treatmentRepository.findAll());
        return "treatments";
    }
}
