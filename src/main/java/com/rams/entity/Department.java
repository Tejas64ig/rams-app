package com.rams.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer departmentId;

    private String name;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    /** Composition: resources cease to be tracked if the department is gone. */
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resource> resources = new ArrayList<>();

    /** Aggregation: staff can be reassigned without being destroyed. */
    @OneToMany(mappedBy = "department")
    private List<Doctor> doctors = new ArrayList<>();

    @OneToMany(mappedBy = "department")
    private List<Nurse> nurses = new ArrayList<>();

    public void addStaff(Doctor doctor) {
        doctors.add(doctor);
        doctor.setDepartment(this);
    }

    public void manageResources() {
        // Placeholder for department-level resource auditing logic.
    }
}
