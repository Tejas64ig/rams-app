package com.rams.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer patientId;

    @NotBlank(message = "Patient name is required")
    private String name;

    @Column(length = 2000)
    private String medicalHistory;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Treatment> treatments = new ArrayList<>();

    private String lastRequestedTreatment;

    /**
     * Sequence diagram step 1: requestTreatment(treatment) -> treatmentLogged()
     * Returns a confirmation string, matching the diagram's return message.
     */
    public String requestTreatment(String treatmentDescription) {
        this.lastRequestedTreatment = treatmentDescription;
        return "treatmentLogged()";
    }
}
