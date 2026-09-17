package com.rams.entity;

/**
 * <<Interface>> Notifiable from the class diagram (Experiment 3).
 * Doctor and Nurse both realize this so the hospital can broadcast
 * alerts to any staff member polymorphically.
 */
public interface Notifiable {
    void sendNotification(String message);
}
