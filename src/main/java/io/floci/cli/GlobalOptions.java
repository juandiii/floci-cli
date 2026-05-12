package io.floci.cli;

import io.floci.cli.output.Ansi;
import io.floci.cli.output.OutputFormat;
import io.floci.cli.output.Printer;
import picocli.CommandLine.Option;

public class GlobalOptions {

    @Option(names = {"--endpoint"},
            description = "Floci server endpoint URL",
            defaultValue = "${FLOCI_ENDPOINT:-http://localhost:4566}",
            paramLabel = "<url>")
    public String endpoint;

    @Option(names = {"--container"},
            description = "Floci container name",
            defaultValue = "${FLOCI_CONTAINER:-floci}",
            paramLabel = "<name>")
    public String container;

    @Option(names = {"--profile"},
            description = "Config profile from ~/.floci/profiles/",
            paramLabel = "<name>")
    public String profile;

    @Option(names = {"--output", "-o"},
            description = "Output format: text, json, yaml",
            defaultValue = "text",
            paramLabel = "text|json|yaml")
    public OutputFormat output;

    @Option(names = {"--quiet", "-q"}, description = "Suppress non-error output")
    public boolean quiet;

    @Option(names = {"--verbose", "-v"}, description = "Debug logging to stderr")
    public boolean verbose;

    @Option(names = {"--no-color"}, description = "Disable ANSI colors")
    public boolean noColor;

    public Printer printer() {
        if (noColor || !isStdoutTty()) {
            Ansi.disable();
        }
        return new Printer(System.out, System.err, output, quiet);
    }

    private static boolean isStdoutTty() {
        return System.console() != null;
    }
}
