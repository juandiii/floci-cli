package io.floci.cli.commands.gcp.snapshot;

import io.floci.cli.GcpGlobalOptions;
import io.floci.cli.output.Ansi;
import picocli.CommandLine.*;

import java.util.concurrent.Callable;

@Command(
        name = "save",
        description = "Save the current Floci GCP state as a named snapshot",
        mixinStandardHelpOptions = true
)
public class GcpSnapshotSaveCommand implements Callable<Integer> {

    @Mixin
    GcpGlobalOptions global;

    @Parameters(index = "0", description = "Snapshot name", paramLabel = "<name>")
    String name;

    @Option(names = {"--message", "-m"}, description = "Optional description", paramLabel = "<text>")
    String message;

    @Override
    public Integer call() {
        global.printer().println(Ansi.yellow("Snapshots are not yet available for Floci GCP.") +
                "\nTrack progress: https://github.com/floci-io/floci-gcp/issues");
        return 0;
    }
}
