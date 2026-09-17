package com.rams.entity;

/**
 * Mirrors the states from Experiment 5 (Statechart Diagram).
 * Idle -> RequestLogged -> Verifying -> Allocated -> Released -> back to Available.
 * Exception states (TIMEOUT, BUSY, RESTRICTED, UNAVAILABLE) are terminal-ish
 * branches that a cancelRequest / retry can route back out of.
 */
public enum ResourceStatus {
    AVAILABLE,
    REQUEST_LOGGED,
    VERIFYING_DEPARTMENT,
    LOCATING_RESOURCE,
    AWAITING_APPROVAL,
    ALLOCATED,
    RELEASED,
    TIMEOUT_ALERT,
    MISMATCH_NOTICE,
    RESOURCE_BUSY,
    ACCESS_RESTRICTED
}
