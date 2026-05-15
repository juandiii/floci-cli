package io.floci.cli.doctor.checks;

import io.floci.cli.docker.DockerClient;
import io.floci.cli.docker.DockerException;
import io.floci.cli.doctor.Check;
import io.floci.cli.doctor.CheckResult;

public class ImagePresentCheck implements Check {

    private final String image;

    public ImagePresentCheck() {
        this("floci/floci");
    }

    public ImagePresentCheck(String image) {
        this.image = image;
    }

    @Override
    public CheckResult run(String endpoint, String container) {
        try {
            DockerClient docker = new DockerClient();
            if (docker.isImagePresent(image)) {
                return CheckResult.ok("image.present", image + " image present locally");
            }
            return CheckResult.fail("image.present",
                    image + " not pulled locally",
                    "floci start --pull always");
        } catch (DockerException e) {
            return CheckResult.warn("image.present", "Could not check image presence: " + e.getMessage(), null);
        }
    }
}
