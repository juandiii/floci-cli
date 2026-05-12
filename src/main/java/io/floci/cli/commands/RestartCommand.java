package io.floci.cli.commands;

import io.floci.cli.GlobalOptions;
import io.floci.cli.output.Printer;
import picocli.CommandLine.*;

import java.util.concurrent.Callable;

@Command(
        name = "restart",
        description = "Stop and restart the Floci container",
        mixinStandardHelpOptions = true
)
public class RestartCommand implements Callable<Integer> {

    @Mixin
    GlobalOptions global;

    @Override
    public Integer call() {
        Printer printer = global.printer();

        StopCommand stop = new StopCommand();
        stop.global = global;
        stop.remove = false;
        stop.timeout = 10;
        int stopResult = stop.call();
        if (stopResult != 0) return stopResult;

        // Minimal wait to avoid port-already-in-use races
        try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        StartCommand start = new StartCommand();
        start.global = global;
        start.port = 4566;
        start.pull = "missing";
        start.image = "floci/floci:latest";
        start.detach = false;
        return start.call();
    }
}
