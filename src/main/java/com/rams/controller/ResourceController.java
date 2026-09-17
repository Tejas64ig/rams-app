package com.rams.controller;

import com.rams.entity.Resource;
import com.rams.entity.ResourceStatus;
import com.rams.repository.ResourceRepository;
import com.rams.service.TreatmentAllocationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ResourceController {

    private final ResourceRepository resourceRepository;
    private final TreatmentAllocationService allocationService;

    public ResourceController(ResourceRepository resourceRepository,
                               TreatmentAllocationService allocationService) {
        this.resourceRepository = resourceRepository;
        this.allocationService = allocationService;
    }

    @GetMapping("/resources")
    public String list(Model model) {
        model.addAttribute("resources", resourceRepository.findAll());
        return "resources";
    }

    @PostMapping("/resources")
    public String add(@RequestParam String type) {
        Resource r = new Resource();
        r.setType(type);
        r.setStatus(ResourceStatus.AVAILABLE);
        resourceRepository.save(r);
        return "redirect:/resources";
    }

    @PostMapping("/resources/{id}/release")
    public String release(@PathVariable Integer id) {
        allocationService.releaseResource(id);
        return "redirect:/resources";
    }

    @GetMapping("/resources/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("resource", resourceRepository.findById(id).orElseThrow());
        model.addAttribute("statuses", ResourceStatus.values());
        return "resource-edit";
    }

    @PostMapping("/resources/{id}/edit")
    public String update(@PathVariable Integer id, @RequestParam String type, @RequestParam ResourceStatus status) {
        Resource r = resourceRepository.findById(id).orElseThrow();
        r.setType(type);
        r.setStatus(status);
        resourceRepository.save(r);
        return "redirect:/resources";
    }

    @PostMapping("/resources/{id}/delete")
    public String delete(@PathVariable Integer id) {
        resourceRepository.deleteById(id);
        return "redirect:/resources";
    }
}
