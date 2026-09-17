package com.rams.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "resources")
@Getter
@Setter
@NoArgsConstructor
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resourceId;

    private String type; // e.g. "Bed", "Ventilator", "OT Room"

    @Enumerated(EnumType.STRING)
    private ResourceStatus status = ResourceStatus.AVAILABLE;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // ---- Statechart transitions (Experiment 5) ----

    public void requestResource() {
        this.status = ResourceStatus.REQUEST_LOGGED;
    }

    public void beginVerification() {
        this.status = ResourceStatus.VERIFYING_DEPARTMENT;
    }

    public void markMismatch() {
        this.status = ResourceStatus.MISMATCH_NOTICE;
    }

    public void locate() {
        this.status = ResourceStatus.LOCATING_RESOURCE;
    }

    public void markBusy() {
        this.status = ResourceStatus.RESOURCE_BUSY;
    }

    public void awaitApproval() {
        this.status = ResourceStatus.AWAITING_APPROVAL;
    }

    /**
     * Sequence diagram step 7: allocate(user) -> status = allocated.
     * Returns false ("resourceNotFound") if not currently available,
     * mirroring the sequence diagram's [else] branch.
     */
    public boolean allocate(Object user) {
        if (status != ResourceStatus.AVAILABLE && status != ResourceStatus.AWAITING_APPROVAL) {
            return false;
        }
        this.status = ResourceStatus.ALLOCATED;
        return true;
    }

    /** Statechart step 1: task complete → RELEASED (persist after calling this). */
    public void release() {
        this.status = ResourceStatus.RELEASED;
    }

    /** Statechart step 2: statusUpdated event → AVAILABLE (persist after calling this). */
    public void markAvailable() {
        this.status = ResourceStatus.AVAILABLE;
    }

    /** Convenience: full RELEASED → AVAILABLE cycle in one call (no intermediate save). */
    public void deallocate() {
        release();
        markAvailable();
    }

    public void cancelRequest() {
        this.status = ResourceStatus.AVAILABLE;
    }
}
