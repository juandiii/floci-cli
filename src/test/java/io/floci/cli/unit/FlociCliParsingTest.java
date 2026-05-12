package io.floci.cli.unit;

import io.floci.cli.FlociCli;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.junit.jupiter.api.Assertions.*;

class FlociCliParsingTest {

    @Test
    void helpExitsZero() {
        int code = new CommandLine(new FlociCli()).execute("--help");
        assertEquals(0, code);
    }

    @Test
    void versionFlag() {
        int code = new CommandLine(new FlociCli()).execute("--version");
        assertEquals(0, code);
    }

    @Test
    void unknownCommandExitsNonZero() {
        int code = new CommandLine(new FlociCli()).execute("nonexistent-subcommand");
        assertNotEquals(0, code);
    }

    @Test
    void subcommandHelpExitsZero() {
        int code = new CommandLine(new FlociCli()).execute("doctor", "--help");
        assertEquals(0, code);
    }
}
