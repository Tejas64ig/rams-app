package com.rams.controller;

import com.rams.entity.Doctor;
import com.rams.repository.DoctorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DoctorController {

    private final DoctorRepository doctorRepository;

    public DoctorController(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @GetMapping("/doctors")
    public String list(Model model) {
        model.addAttribute("doctors", doctorRepository.findAll());
        return "doctors";
    }

    @PostMapping("/doctors")
    public String add(@RequestParam String name, @RequestParam String specialization) {
        Doctor d = new Doctor();
        d.setName(name);
        d.setSpecialization(specialization);
        doctorRepository.save(d);
        return "redirect:/doctors";
    }

    @GetMapping("/doctors/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("doctor", doctorRepository.findById(id).orElseThrow());
        return "doctor-edit";
    }

    @PostMapping("/doctors/{id}/edit")
    public String update(@PathVariable Integer id, @RequestParam String name, @RequestParam String specialization) {
        Doctor d = doctorRepository.findById(id).orElseThrow();
        d.setName(name);
        d.setSpecialization(specialization);
        doctorRepository.save(d);
        return "redirect:/doctors";
    }

    @PostMapping("/doctors/{id}/free")
    public String markFree(@PathVariable Integer id) {
        Doctor d = doctorRepository.findById(id).orElseThrow();
        d.setBusy(false);
        doctorRepository.save(d);
        return "redirect:/doctors";
    }

    @PostMapping("/doctors/{id}/delete")
    public String delete(@PathVariable Integer id) {
        doctorRepository.deleteById(id);
        return "redirect:/doctors";
    }
}
