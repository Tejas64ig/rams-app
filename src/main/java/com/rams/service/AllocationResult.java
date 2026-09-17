package com.rams.service;

import lombok.Getter;

/**
 * Return type for TreatmentAllocationService.allocateTreatment().
 * success = true  -> resource allocated, appointment confirmed (the "alt" happy path)
 * success = false -> resourceNotFound() (the "else" path)
 */
@Getter
public class AllocationResult {

    private final boolean success;
    private final String message;

    private AllocationResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static AllocationResult confirmed(String message) {
        return new AllocationResult(true, message);
    }

    public static AllocationResult resourceNotFound() {
        return new AllocationResult(false, "resourceNotFound()");
    }
}
