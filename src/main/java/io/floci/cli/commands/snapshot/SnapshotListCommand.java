package io.floci.cli.commands.snapshot;

import io.floci.cli.GlobalOptions;
import io.floci.cli.http.FlociException;
import io.floci.cli.http.FlociHttpClient;
import io.floci.cli.output.Ansi;
import io.floci.cli.output.OutputFormat;
import io.floci.cli.output.Printer;
import picocli.CommandLine.*;

import java.util.concurrent.Callable;

@Command(
        name = "list",
        description = "List available snapshots",
        mixinStandardHelpOptions = true
)
public class SnapshotListCommand implements Callable<Integer> {

    @Mixin
    GlobalOptions global;

    @Override
    public Integer call() {
        Printer printer = global.printer();
        FlociHttpClient client = new FlociHttpClient(global.endpoint);
        try {
            var node = client.listSnapshots();
            if (printer.format() != OutputFormat.text) {
                printer.structured(node);
                return 0;
            }
            if (node.isArray() && node.size() == 0) {
                printer.println(Ansi.gray("No snapshots found."));
                return 0;
            }
            printer.println(Ansi.bold("Snapshots:"));
            node.forEach(s -> printer.println("  " + s.asText()));
            return 0;
        } catch (FlociException e) {
            if (e.getMessage().contains("404") || e.getMessage().contains("501")) {
                printer.error("Snapshot API not available on this server version.\n" +
                        "See TODO.md for the required server-side API contract.");
            } else {
                printer.error("Failed to list snapshots: " + e.getMessage());
            }
            return 1;
        }
    }
}
