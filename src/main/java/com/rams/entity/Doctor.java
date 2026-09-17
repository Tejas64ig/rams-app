package com.rams.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@NoArgsConstructor
public class Doctor extends Staff implements Notifiable {

    private String specialization;

    /** true while the doctor is mid-appointment / mid-surgery */
    private boolean busy = false;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Treatment> treatments = new ArrayList<>();

    @Override
    public boolean reportAvailability() {
        return !busy;
    }

    @Override
    public void sendNotification(String message) {
        // In a real system this would push an email/SMS/in-app alert.
        System.out.println("[Doctor " + getName() + "] Notification: " + message);
    }

    /** Sequence diagram: updateAvailability() -> availableSlots() */
    public List<String> updateAvailability() {
        // Placeholder slot logic; wire this to a real schedule table later.
        return busy ? List.of() : List.of("09:00", "10:00", "14:00");
    }

    /** Sequence diagram: assignResources(resource) delegated from Appointment */
    public boolean assignResources(Resource resource) {
        return resource.allocate(this);
    }
}
