package io.floci.cli.commands.snapshot;

import io.floci.cli.GlobalOptions;
import io.floci.cli.output.Printer;
import picocli.CommandLine.*;

import java.util.concurrent.Callable;

@Command(
        name = "import",
        description = "Import a snapshot from a tarball file",
        mixinStandardHelpOptions = true
)
public class SnapshotImportCommand implements Callable<Integer> {

    @Mixin
    GlobalOptions global;

    @Option(names = {"--file", "-f"}, description = "Source tarball path", required = true, paramLabel = "<path>")
    String inputFile;

    @Option(names = {"--name"}, description = "Name for the imported snapshot", paramLabel = "<name>")
    String name;

    @Override
    public Integer call() {
        Printer printer = global.printer();
        // TODO: implement POST /_floci/snapshots/import with multipart upload
        printer.error("snapshot import requires floci-server >= 1.6.0\n" +
                "See TODO.md for the required server-side API contract.");
        return 1;
    }
}
