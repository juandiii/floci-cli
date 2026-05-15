package io.floci.cli.commands.config;

import picocli.CommandLine;
import picocli.CommandLine.*;

@Command(
        name = "config",
        description = "Manage Floci configuration",
        mixinStandardHelpOptions = true,
        subcommands = {
                ConfigShowCommand.class,
                ConfigValidateCommand.class,
                ConfigProfileCommand.class,
                ConfigDefaultProductCommand.class,
                HelpCommand.class
        }
)
public class ConfigCommand implements Runnable {

    @Override
    public void run() {
        new CommandLine(this).usage(System.out);
    }
}
