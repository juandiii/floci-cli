package io.floci.cli.commands.az;

import io.floci.cli.AzGlobalOptions;
import io.floci.cli.output.Printer;
import picocli.CommandLine.*;

import java.util.concurrent.Callable;

@Command(
        name = "restart",
        description = "Stop and restart the Floci Azure container",
        mixinStandardHelpOptions = true
)
public class AzRestartCommand implements Callable<Integer> {

    @Mixin
    AzGlobalOptions global;

    @Override
    public Integer call() {
        Printer printer = global.printer();

        AzStopCommand stop = new AzStopCommand();
        stop.global = global;
        stop.remove = false;
        stop.timeout = 10;
        int stopResult = stop.call();
        if (stopResult != 0) return stopResult;

        try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        AzStartCommand start = new AzStartCommand();
        start.global = global;
        start.port = 4577;
        start.pull = "missing";
        start.image = "floci/floci-az:latest";
        start.detach = false;
        return start.call();
    }
}
