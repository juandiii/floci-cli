package io.floci.cli.unit;

import io.floci.cli.doctor.checks.DockerVersionCheck;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class DockerVersionCheckTest {

    @ParameterizedTest
    @CsvSource({
            "24.0.7, true",
            "20.10.0, true",
            "20.10, true",
            "20.9.0, false",
            "19.03.0, false",
            "26.1.3, true"
    })
    void testVersionParsing(String version, boolean expected) {
        assertEquals(expected, DockerVersionCheck.meetsMinimum(version));
    }

    @Test
    void testUnparseable() {
        assertTrue(DockerVersionCheck.meetsMinimum("dev"), "unparseable version should pass");
    }
}
