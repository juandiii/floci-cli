package io.floci.cli.commands.snapshot;

import io.floci.cli.GlobalOptions;
import io.floci.cli.http.FlociException;
import io.floci.cli.http.FlociHttpClient;
import io.floci.cli.output.Ansi;
import io.floci.cli.output.Printer;
import picocli.CommandLine.*;

import java.util.concurrent.Callable;

@Command(
        name = "delete",
        description = "Delete a snapshot",
        mixinStandardHelpOptions = true
)
public class SnapshotDeleteCommand implements Callable<Integer> {

    @Mixin
    GlobalOptions global;

    @Parameters(index = "0", description = "Snapshot name", paramLabel = "<name>")
    String name;

    @Override
    public Integer call() {
        Printer printer = global.printer();
        FlociHttpClient client = new FlociHttpClient(global.endpoint);
        try {
            client.deleteSnapshot(name);
            printer.println(Ansi.green("Snapshot deleted:") + " " + name);
            return 0;
        } catch (FlociException e) {
            printer.error("Failed to delete snapshot: " + e.getMessage());
            return 1;
        }
    }
}
