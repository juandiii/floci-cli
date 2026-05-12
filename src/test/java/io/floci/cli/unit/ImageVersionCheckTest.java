package io.floci.cli.unit;

import io.floci.cli.doctor.checks.ImageVersionCheck;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ImageVersionCheckTest {

    @ParameterizedTest
    @CsvSource({
            "1.5.0, 1.5.0, true",
            "1.5.1, 1.5.0, true",
            "2.0.0, 1.5.0, true",
            "1.4.9, 1.5.0, false",
            "1.5.0-SNAPSHOT, 1.5.0, true"
    })
    void testSemVerComparison(String version, String minimum, boolean expected) {
        assertEquals(expected, ImageVersionCheck.meetsMinimum(version, minimum));
    }
}
