package com.rams.controller;

import com.rams.entity.Department;
import com.rams.repository.DepartmentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @GetMapping("/departments")
    public String list(Model model) {
        model.addAttribute("departments", departmentRepository.findAll());
        return "departments";
    }

    @PostMapping("/departments")
    public String add(@RequestParam String name) {
        Department d = new Department();
        d.setName(name);
        departmentRepository.save(d);
        return "redirect:/departments";
    }

    @PostMapping("/departments/{id}/delete")
    public String delete(@PathVariable Integer id) {
        departmentRepository.deleteById(id);
        return "redirect:/departments";
    }
}
