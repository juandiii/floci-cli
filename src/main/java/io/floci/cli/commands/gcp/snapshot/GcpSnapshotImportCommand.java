package io.floci.cli.commands.gcp.snapshot;

import io.floci.cli.GcpGlobalOptions;
import io.floci.cli.output.Ansi;
import picocli.CommandLine.*;

import java.util.concurrent.Callable;

@Command(
        name = "import",
        description = "Import a Floci GCP snapshot from a tarball file",
        mixinStandardHelpOptions = true
)
public class GcpSnapshotImportCommand implements Callable<Integer> {

    @Mixin
    GcpGlobalOptions global;

    @Parameters(index = "0", description = "Snapshot name", paramLabel = "<name>")
    String name;

    @Option(names = {"--file", "-f"}, description = "Source file path", required = true, paramLabel = "<path>")
    String inputFile;

    @Override
    public Integer call() {
        global.printer().println(Ansi.yellow("Snapshots are not yet available for Floci GCP.") +
                "\nTrack progress: https://github.com/floci-io/floci-gcp/issues");
        return 0;
    }
}
