package com.rams.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer appointmentId;

    private LocalDateTime date;

    private boolean confirmed = false;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    /** Sequence diagram step 5: schedule(patient, doctor) */
    public void schedule(Patient patient, Doctor doctor) {
        this.patient = patient;
        this.doctor = doctor;
        this.date = LocalDateTime.now();
    }

    /** Sequence diagram step 11: confirm() -> allocationConfirmed() */
    public String confirm() {
        this.confirmed = true;
        return "allocationConfirmed()";
    }
}
