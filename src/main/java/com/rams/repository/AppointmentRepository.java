package com.rams.repository;

import com.rams.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    long countByConfirmed(boolean confirmed);
    List<Appointment> findAllByOrderByDateDesc();
}
