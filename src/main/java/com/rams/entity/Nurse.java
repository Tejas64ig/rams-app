package com.rams.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "nurses")
@Getter
@Setter
@NoArgsConstructor
public class Nurse extends Staff implements Notifiable {

    private boolean onShift = true;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient; // "cared by" relationship

    @Override
    public boolean reportAvailability() {
        return onShift;
    }

    @Override
    public void sendNotification(String message) {
        System.out.println("[Nurse " + getName() + "] Notification: " + message);
    }

    public void attendPatient(Patient patient) {
        this.patient = patient;
    }

    public void recordNotes() {
        // Placeholder for clinical note-taking.
    }
}
