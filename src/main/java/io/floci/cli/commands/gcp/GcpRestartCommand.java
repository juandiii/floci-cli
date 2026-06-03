package io.floci.cli.commands.gcp;

import io.floci.cli.GcpGlobalOptions;
import io.floci.cli.output.Printer;
import picocli.CommandLine.*;

import java.util.concurrent.Callable;

@Command(
        name = "restart",
        description = "Stop and restart the Floci GCP container",
        mixinStandardHelpOptions = true
)
public class GcpRestartCommand implements Callable<Integer> {

    @Mixin
    GcpGlobalOptions global;

    @Override
    public Integer call() {
        Printer printer = global.printer();

        GcpStopCommand stop = new GcpStopCommand();
        stop.global = global;
        stop.remove = false;
        stop.timeout = 10;
        int stopResult = stop.call();
        if (stopResult != 0) return stopResult;

        try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        GcpStartCommand start = new GcpStartCommand();
        start.global = global;
        start.port = 4588;
        start.pull = "missing";
        start.image = "floci/floci-gcp:latest";
        start.detach = false;
        return start.call();
    }
}