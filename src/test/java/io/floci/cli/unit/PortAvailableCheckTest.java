package io.floci.cli.unit;

import io.floci.cli.doctor.checks.PortAvailableCheck;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PortAvailableCheckTest {

    @Test
    void testPortExtraction() {
        assertEquals(4566, PortAvailableCheck.extractPort("http://localhost:4566"));
        assertEquals(8080, PortAvailableCheck.extractPort("http://localhost:8080"));
        assertEquals(4566, PortAvailableCheck.extractPort("invalid"));
    }
}
