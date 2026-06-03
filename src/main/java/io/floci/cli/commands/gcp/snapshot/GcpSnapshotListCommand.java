package io.floci.cli.commands.gcp.snapshot;

import io.floci.cli.GcpGlobalOptions;
import io.floci.cli.output.Ansi;
import io.floci.cli.output.OutputFormat;
import io.floci.cli.output.Printer;
import picocli.CommandLine.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

@Command(
        name = "list",
        description = "List available Floci GCP snapshots",
        mixinStandardHelpOptions = true
)
public class GcpSnapshotListCommand implements Callable<Integer> {

    @Mixin
    GcpGlobalOptions global;

    @Override
    public Integer call() {
        Printer printer = global.printer();
        if (printer.format() != OutputFormat.text) {
            printer.structured(Map.of("snapshots", List.of(), "note", "not yet available"));
            return 0;
        }
        printer.println(Ansi.gray("No snapshots found.") +
                " (Snapshot support for Floci GCP is coming — https://github.com/floci-io/floci-gcp/issues)");
        return 0;
    }
}
