package io.floci.cli.commands;

import io.floci.cli.GlobalOptions;
import io.floci.cli.output.Printer;
import picocli.AutoComplete;
import picocli.CommandLine;
import picocli.CommandLine.*;

import java.util.concurrent.Callable;

@Command(
        name = "completion",
        description = "Generate shell completion scripts",
        mixinStandardHelpOptions = true
)
public class CompletionCommand implements Callable<Integer> {

    @Mixin
    GlobalOptions global;

    @Parameters(index = "0", description = "Shell type: bash, zsh, fish, powershell", paramLabel = "bash|zsh|fish|powershell")
    String shell;

    @Override
    public Integer call() {
        Printer printer = global.printer();
        CommandLine root = new CommandLine(new io.floci.cli.FlociCli());

        String script = switch (shell.toLowerCase()) {
            case "bash" -> AutoComplete.bash("floci", root);
            case "zsh"  -> AutoComplete.bash("floci", root); // zsh is bash-compatible; picocli bash script works for zsh too
            default -> {
                printer.error("Shell '" + shell + "' not supported. Supported: bash, zsh\n" +
                        "For fish and powershell, track https://github.com/floci-io/floci-cli/issues");
                yield null;
            }
        };

        if (script == null) return 1;
        System.out.print(script);
        return 0;
    }
}
