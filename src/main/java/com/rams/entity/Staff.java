package com.rams.entity;


import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * Abstract Staff class from the class diagram. Doctor and Nurse both
 * generalize from this to reuse staffId/name/reportAvailability().
 *
 * MappedSuperclass = its fields get copied into each subclass's table
 * rather than creating a separate "staff" table (simplest mapping for
 * a generalization relationship like this one).
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer staffId;

    private String name;

    /** Every staff member can report whether they're free to take on work. */
    public abstract boolean reportAvailability();
}
