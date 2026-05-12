package io.floci.cli.doctor.checks;

import io.floci.cli.doctor.Check;
import io.floci.cli.doctor.CheckResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SdkRustS3XmlCheck implements Check {

    @Override
    public CheckResult run(String endpoint, String container) {
        Path cargoToml = Path.of("Cargo.toml");
        if (!Files.exists(cargoToml)) {
            return CheckResult.ok("sdk.rust.s3.xml", "No Cargo.toml in CWD — skipped");
        }
        try {
            String content = Files.readString(cargoToml);
            if (!content.contains("aws-sdk-s3")) {
                return CheckResult.ok("sdk.rust.s3.xml", "aws-sdk-s3 not in Cargo.toml — skipped");
            }
            return CheckResult.warn("sdk.rust.s3.xml",
                    "aws-sdk-s3 detected — known issue: XmlDecodeError on S3 error responses",
                    "See https://github.com/floci-io/floci/issues/11 for workaround");
        } catch (IOException e) {
            return CheckResult.warn("sdk.rust.s3.xml", "Could not read Cargo.toml", null);
        }
    }
}
