package com.rams.controller;

import com.rams.repository.AppointmentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;

    public AppointmentController(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping("/appointments")
    public String list(Model model) {
        model.addAttribute("appointments", appointmentRepository.findAllByOrderByDateDesc());
        return "appointments";
    }
}
