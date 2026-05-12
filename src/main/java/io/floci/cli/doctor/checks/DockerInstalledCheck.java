package io.floci.cli.doctor.checks;

import io.floci.cli.docker.DockerClient;
import io.floci.cli.doctor.Check;
import io.floci.cli.doctor.CheckResult;

public class DockerInstalledCheck implements Check {

    @Override
    public CheckResult run(String endpoint, String container) {
        if (!DockerClient.isInstalled()) {
            return CheckResult.fail("docker.installed",
                    "docker binary not found in PATH",
                    "Install Docker Desktop from https://docs.docker.com/get-docker/");
        }
        try {
            String version = new DockerClient().dockerVersion();
            return CheckResult.ok("docker.installed", "Docker " + version + " detected");
        } catch (Exception e) {
            return CheckResult.ok("docker.installed", "Docker detected");
        }
    }
}
