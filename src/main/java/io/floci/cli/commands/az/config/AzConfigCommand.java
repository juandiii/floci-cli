package io.floci.cli.commands.az.config;

import picocli.CommandLine;
import picocli.CommandLine.*;

@Command(
        name = "config",
        description = "Manage Floci Azure configuration",
        mixinStandardHelpOptions = true,
        subcommands = {
                AzConfigShowCommand.class,
                AzConfigValidateCommand.class,
                AzConfigProfileCommand.class,
                HelpCommand.class
        }
)
public class AzConfigCommand implements Runnable {

    @Override
    public void run() {
        new CommandLine(this).usage(System.out);
    }
}
