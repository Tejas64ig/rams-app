package com.rams;

import com.rams.entity.Doctor;
import com.rams.entity.Patient;
import com.rams.entity.Resource;
import com.rams.entity.ResourceStatus;
import com.rams.repository.DoctorRepository;
import com.rams.repository.PatientRepository;
import com.rams.repository.ResourceRepository;
import com.rams.service.AllocationResult;
import com.rams.service.TreatmentAllocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Exercises both branches of the alt combined fragment from Experiment 4:
 *  - [resource available]  -> allocationConfirmed()
 *  - [else]                -> resourceNotFound()
 */
@SpringBootTest
class TreatmentAllocationServiceTest {

    @Autowired private TreatmentAllocationService allocationService;
    @Autowired private PatientRepository patientRepository;
    @Autowired private DoctorRepository doctorRepository;
    @Autowired private ResourceRepository resourceRepository;

    private Integer patientId;
    private Integer doctorId;

    @BeforeEach
    void setUp() {
        Patient patient = new Patient();
        patient.setName("Test Patient");
        patient = patientRepository.save(patient);
        patientId = patient.getPatientId();

        Doctor doctor = new Doctor();
        doctor.setName("Dr. Test");
        doctor.setSpecialization("General");
        doctor = doctorRepository.save(doctor);
        doctorId = doctor.getStaffId();
    }

    @Test
    void allocatesSuccessfully_whenResourceAvailable() {
        Resource resource = new Resource();
        resource.setType("ICU Bed");
        resource.setStatus(ResourceStatus.AVAILABLE);
        resourceRepository.save(resource);

        AllocationResult result = allocationService.allocateTreatment(
                patientId, doctorId, "Knee Surgery", "ICU Bed");

        assertTrue(result.isSuccess());
    }

    @Test
    void returnsResourceNotFound_whenNoMatchingResourceExists() {
        // No "ICU Bed" resource created for this test — mirrors the [else] branch.
        AllocationResult result = allocationService.allocateTreatment(
                patientId, doctorId, "Knee Surgery", "ICU Bed");

        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("resourceNotFound"));
    }
}
