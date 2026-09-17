package com.rams.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "treatments")
@Getter
@Setter
@NoArgsConstructor
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer treatmentId;

    private String description;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private boolean applied = false;

    /** Sequence diagram step 9: apply(patient) -> treatmentApplied() */
    public String apply(Patient patient) {
        this.patient = patient;
        this.applied = true;
        return "treatmentApplied()";
    }
}
