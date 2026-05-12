package io.floci.cli.doctor.checks;

import io.floci.cli.doctor.Check;
import io.floci.cli.doctor.CheckResult;

public class AwsCliEndpointCheck implements Check {

    @Override
    public CheckResult run(String endpoint, String container) {
        // Check if aws CLI is installed first
        if (!isAwsCliInstalled()) {
            return CheckResult.ok("aws.cli.endpoint", "aws CLI not installed — skipped");
        }
        String envVar = System.getenv("AWS_ENDPOINT_URL");
        if (envVar != null && !envVar.isBlank()) {
            return CheckResult.ok("aws.cli.endpoint", "AWS_ENDPOINT_URL=" + envVar);
        }
        return CheckResult.warn("aws.cli.endpoint",
                "AWS_ENDPOINT_URL is not set",
                "export AWS_ENDPOINT_URL=" + endpoint);
    }

    private boolean isAwsCliInstalled() {
        try {
            Process p = new ProcessBuilder("aws", "--version").start();
            return p.waitFor() == 0;
        } catch (Exception e) {
            return false;
        }
    }
}
