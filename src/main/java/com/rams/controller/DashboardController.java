package com.rams.controller;

import com.rams.repository.*;
import com.rams.entity.ResourceStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final NurseRepository nurseRepository;
    private final ResourceRepository resourceRepository;
    private final AppointmentRepository appointmentRepository;
    private final TreatmentRepository treatmentRepository;
    private final DepartmentRepository departmentRepository;

    public DashboardController(PatientRepository patientRepository,
                                DoctorRepository doctorRepository,
                                NurseRepository nurseRepository,
                                ResourceRepository resourceRepository,
                                AppointmentRepository appointmentRepository,
                                TreatmentRepository treatmentRepository,
                                DepartmentRepository departmentRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.nurseRepository = nurseRepository;
        this.resourceRepository = resourceRepository;
        this.appointmentRepository = appointmentRepository;
        this.treatmentRepository = treatmentRepository;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("totalPatients", patientRepository.count());
        model.addAttribute("totalDoctors", doctorRepository.count());
        model.addAttribute("totalNurses", nurseRepository.count());
        model.addAttribute("totalDepartments", departmentRepository.count());

        long totalResources = resourceRepository.count();
        long allocatedResources = resourceRepository.countByStatus(ResourceStatus.ALLOCATED);
        long availableResources = resourceRepository.countByStatus(ResourceStatus.AVAILABLE);
        model.addAttribute("totalResources", totalResources);
        model.addAttribute("allocatedResources", allocatedResources);
        model.addAttribute("availableResources", availableResources);

        model.addAttribute("totalAppointments", appointmentRepository.count());
        model.addAttribute("confirmedAppointments", appointmentRepository.countByConfirmed(true));
        model.addAttribute("totalTreatments", treatmentRepository.count());

        return "dashboard";
    }
}
