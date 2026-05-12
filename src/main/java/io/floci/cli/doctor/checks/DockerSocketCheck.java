package io.floci.cli.doctor.checks;

import io.floci.cli.docker.DockerClient;
import io.floci.cli.doctor.Check;
import io.floci.cli.doctor.CheckResult;

import java.nio.file.Files;
import java.nio.file.Path;

public class DockerSocketCheck implements Check {

    @Override
    public CheckResult run(String endpoint, String container) {
        String socketPath = DockerClient.socketPath();
        String os = System.getProperty("os.name", "").toLowerCase();
        if (os.contains("win")) {
            return CheckResult.ok("docker.socket", "Windows named pipe (" + socketPath + ")");
        }
        if (Files.exists(Path.of(socketPath))) {
            return CheckResult.ok("docker.socket", socketPath + " accessible");
        }
        String fix = os.contains("mac")
                ? "Open Docker Desktop — the socket is created when Docker Desktop is running"
                : "sudo chmod 666 /var/run/docker.sock  OR  add your user to the docker group";
        return CheckResult.fail("docker.socket", socketPath + " not found or not accessible", fix);
    }
}
