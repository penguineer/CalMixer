package com.penguineering.calmixer.health;

import java.util.Map;
import java.util.Optional;

/**
 * Provide health informatin for a sub-system.
 */
public interface HealthProvider {
    /**
     * @return a key for this health provider
     */
    String getKey();

    /**
     * Get health status information
     * @return Map of health status from this provider
     */
    Optional<Object> getStatus();

    /**
     * State if this provider's status is considered healthy
     * @return True if healthy, otherwise false
     */
    boolean isHealthy();
}
