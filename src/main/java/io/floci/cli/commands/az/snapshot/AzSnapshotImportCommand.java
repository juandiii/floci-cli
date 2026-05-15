package io.floci.cli.commands.az.snapshot;

import io.floci.cli.AzGlobalOptions;
import io.floci.cli.output.Ansi;
import picocli.CommandLine.*;

import java.util.concurrent.Callable;

@Command(
        name = "import",
        description = "Import a Floci Azure snapshot from a tarball file",
        mixinStandardHelpOptions = true
)
public class AzSnapshotImportCommand implements Callable<Integer> {

    @Mixin
    AzGlobalOptions global;

    @Option(names = {"--file", "-f"}, description = "Source tarball path", required = true, paramLabel = "<path>")
    String inputFile;

    @Option(names = {"--name"}, description = "Name for the imported snapshot", paramLabel = "<name>")
    String name;

    @Override
    public Integer call() {
        global.printer().println(Ansi.yellow("Snapshots are not yet available for Floci Azure.") +
                "\nTrack progress: https://github.com/floci-io/floci-az/issues");
        return 0;
    }
}
