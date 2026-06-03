package io.floci.cli.commands.gcp.config;

import io.floci.cli.GcpGlobalOptions;
import io.floci.cli.output.Ansi;
import io.floci.cli.output.Printer;
import picocli.CommandLine.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

@Command(
        name = "validate",
        description = "Validate a docker-compose.yml file for Floci GCP compatibility",
        mixinStandardHelpOptions = true
)
public class GcpConfigValidateCommand implements Callable<Integer> {

    @Mixin
    GcpGlobalOptions global;

    @Option(names = {"--file", "-f"}, description = "Path to docker-compose file", paramLabel = "<path>")
    String file;

    @Override
    public Integer call() {
        Printer printer = global.printer();

        Path composeFile = resolveComposeFile();
        if (composeFile == null) {
            printer.error("No docker-compose file found. Pass --file <path> or run in a directory with docker-compose.yml.");
            return 1;
        }

        String content;
        try {
            content = Files.readString(composeFile);
        } catch (IOException e) {
            printer.error("Could not read " + composeFile + ": " + e.getMessage());
            return 1;
        }

        printer.println(Ansi.bold("Validating") + " " + composeFile);
        printer.println("");

        List<String> issues = new ArrayList<>();
        List<String> warnings = new ArrayList<>();

        if (!content.contains("/var/run/docker.sock")) {
            warnings.add("Missing /var/run/docker.sock volume mount — required for Kafka/Redpanda support");
        }

        if (!Pattern.compile("4588\\s*:\\s*4588").matcher(content).find()) {
            issues.add("Port mapping 4588:4588 not found — Floci GCP listens on 4588 by default");
        }

        if (!content.contains("floci/floci-gcp") && !content.contains("ghcr.io/floci-io/floci-gcp")) {
            warnings.add("Floci GCP image reference not detected — ensure your service uses floci/floci-gcp");
        }

        for (String issue : issues) {
            printer.println("  " + Ansi.red("✗") + "  " + issue);
        }
        for (String warn : warnings) {
            printer.println("  " + Ansi.yellow("⚠") + "  " + warn);
        }
        if (issues.isEmpty() && warnings.isEmpty()) {
            printer.println("  " + Ansi.green("✓") + "  Compose file looks good");
        }

        printer.println("");
        if (!issues.isEmpty()) {
            printer.println(issues.size() + " error(s) found.");
            return 1;
        }
        return 0;
    }

    private Path resolveComposeFile() {
        if (file != null) return Path.of(file);
        for (String name : List.of("docker-compose.yml", "docker-compose.yaml", "compose.yml", "compose.yaml")) {
            Path p = Path.of(name);
            if (Files.exists(p)) return p;
        }
        return null;
    }
}
