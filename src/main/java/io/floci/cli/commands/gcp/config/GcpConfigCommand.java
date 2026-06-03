package io.floci.cli.commands.gcp.config;

import picocli.CommandLine;
import picocli.CommandLine.*;

@Command(
        name = "config",
        description = "Manage Floci GCP configuration",
        mixinStandardHelpOptions = true,
        subcommands = {
                GcpConfigShowCommand.class,
                GcpConfigValidateCommand.class,
                GcpConfigProfileCommand.class,
                HelpCommand.class
        }
)
public class GcpConfigCommand implements Runnable {

    @Override
    public void run() {
        new CommandLine(this).usage(System.out);
    }
}
