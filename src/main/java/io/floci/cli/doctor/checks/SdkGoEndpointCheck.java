package io.floci.cli.doctor.checks;

import io.floci.cli.doctor.Check;
import io.floci.cli.doctor.CheckResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SdkGoEndpointCheck implements Check {

    @Override
    public CheckResult run(String endpoint, String container) {
        Path goMod = Path.of("go.mod");
        if (!Files.exists(goMod)) {
            return CheckResult.ok("sdk.go.endpoint", "No go.mod in CWD — skipped");
        }
        try {
            String content = Files.readString(goMod);
            if (!content.contains("aws-sdk-go-v2")) {
                return CheckResult.ok("sdk.go.endpoint", "aws-sdk-go-v2 not in go.mod — skipped");
            }
            return CheckResult.warn("sdk.go.endpoint",
                    "aws-sdk-go-v2 detected — use BaseEndpoint instead of deprecated EndpointResolver",
                    "See https://aws.github.io/aws-sdk-go-v2/docs/configuring-sdk/endpoints/ for migration guide");
        } catch (IOException e) {
            return CheckResult.warn("sdk.go.endpoint", "Could not read go.mod", null);
        }
    }
}
