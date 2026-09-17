package com.rams.controller;

import com.rams.entity.Nurse;
import com.rams.repository.NurseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class NurseController {

    private final NurseRepository nurseRepository;

    public NurseController(NurseRepository nurseRepository) {
        this.nurseRepository = nurseRepository;
    }

    @GetMapping("/nurses")
    public String list(Model model) {
        model.addAttribute("nurses", nurseRepository.findAll());
        return "nurses";
    }

    @PostMapping("/nurses")
    public String add(@RequestParam String name, @RequestParam(required = false) Boolean onShift) {
        Nurse n = new Nurse();
        n.setName(name);
        n.setOnShift(onShift != null && onShift);
        nurseRepository.save(n);
        return "redirect:/nurses";
    }

    @PostMapping("/nurses/{id}/delete")
    public String delete(@PathVariable Integer id) {
        nurseRepository.deleteById(id);
        return "redirect:/nurses";
    }
}
