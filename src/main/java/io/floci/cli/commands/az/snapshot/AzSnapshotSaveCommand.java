package io.floci.cli.commands.az.snapshot;

import io.floci.cli.AzGlobalOptions;
import io.floci.cli.output.Ansi;
import io.floci.cli.output.Printer;
import picocli.CommandLine.*;

import java.util.concurrent.Callable;

@Command(
        name = "save",
        description = "Save the current Floci Azure state as a named snapshot",
        mixinStandardHelpOptions = true
)
public class AzSnapshotSaveCommand implements Callable<Integer> {

    @Mixin
    AzGlobalOptions global;

    @Parameters(index = "0", description = "Snapshot name", paramLabel = "<name>")
    String name;

    @Option(names = {"--message", "-m"}, description = "Optional description", paramLabel = "<text>")
    String message;

    @Override
    public Integer call() {
        global.printer().println(Ansi.yellow("Snapshots are not yet available for Floci Azure.") +
                "\nTrack progress: https://github.com/floci-io/floci-az/issues");
        return 0;
    }
}
