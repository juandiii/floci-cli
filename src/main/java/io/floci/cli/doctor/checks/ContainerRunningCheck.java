package io.floci.cli.doctor.checks;

import io.floci.cli.docker.DockerClient;
import io.floci.cli.docker.DockerException;
import io.floci.cli.doctor.Check;
import io.floci.cli.doctor.CheckResult;

public class ContainerRunningCheck implements Check {

    @Override
    public CheckResult run(String endpoint, String container) {
        try {
            var info = new DockerClient().inspectContainer(container);
            if (info.isEmpty()) {
                return CheckResult.warn("container.running",
                        "Container '" + container + "' not found",
                        "floci start");
            }
            String state = info.get().state();
            if ("running".equals(state)) {
                return CheckResult.ok("container.running", "Container '" + container + "' is running");
            }
            return CheckResult.fail("container.running",
                    "Container '" + container + "' exists but state is '" + state + "'",
                    "floci start  (or 'floci stop && floci start' to restart)");
        } catch (DockerException e) {
            return CheckResult.warn("container.running", "Could not inspect container: " + e.getMessage(), null);
        }
    }
}
