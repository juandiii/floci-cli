package io.floci.cli.commands.az.snapshot;

import io.floci.cli.AzGlobalOptions;
import io.floci.cli.output.Ansi;
import picocli.CommandLine.*;

import java.util.concurrent.Callable;

@Command(
        name = "load",
        description = "Restore a saved Floci Azure snapshot",
        mixinStandardHelpOptions = true
)
public class AzSnapshotLoadCommand implements Callable<Integer> {

    @Mixin
    AzGlobalOptions global;

    @Parameters(index = "0", description = "Snapshot name", paramLabel = "<name>")
    String name;

    @Override
    public Integer call() {
        global.printer().println(Ansi.yellow("Snapshots are not yet available for Floci Azure.") +
                "\nTrack progress: https://github.com/floci-io/floci-az/issues");
        return 0;
    }
}
