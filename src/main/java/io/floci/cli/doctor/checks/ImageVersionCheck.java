package io.floci.cli.doctor.checks;

import io.floci.cli.docker.DockerClient;
import io.floci.cli.docker.DockerException;
import io.floci.cli.doctor.Check;
import io.floci.cli.doctor.CheckResult;

public class ImageVersionCheck implements Check {

    private static final String IMAGE = "floci/floci";
    private static final String MIN_VERSION = "1.5.0";

    @Override
    public CheckResult run(String endpoint, String container) {
        try {
            DockerClient docker = new DockerClient();
            if (!docker.isImagePresent(IMAGE)) {
                return CheckResult.warn("image.version", IMAGE + " not present — version cannot be checked",
                        "floci start --pull always");
            }
            // Try reading version label from the image
            String version = readImageLabel(docker, IMAGE);
            if (version == null) {
                return CheckResult.ok("image.version", "Image version unknown (no label); assuming compatible");
            }
            if (meetsMinimum(version, MIN_VERSION)) {
                return CheckResult.ok("image.version", "Server image version " + version + " (>= " + MIN_VERSION + ")");
            }
            return CheckResult.warn("image.version",
                    "Server image version " + version + " is below minimum " + MIN_VERSION,
                    "docker pull " + IMAGE + ":latest");
        } catch (DockerException e) {
            return CheckResult.warn("image.version", "Could not check image version: " + e.getMessage(), null);
        }
    }

    private String readImageLabel(DockerClient docker, String image) {
        try {
            String raw = runDockerInspect(image);
            return raw.isBlank() ? null : raw.trim();
        } catch (Exception e) {
            return null;
        }
    }

    private String runDockerInspect(String image) throws Exception {
        Process p = new ProcessBuilder("docker", "inspect", "--format",
                "{{index .Config.Labels \"org.opencontainers.image.version\"}}", image).start();
        String out = new String(p.getInputStream().readAllBytes()).trim();
        p.waitFor();
        return out;
    }

    public static boolean meetsMinimum(String version, String minimum) {
        try {
            int[] v = parseSemVer(version);
            int[] m = parseSemVer(minimum);
            for (int i = 0; i < 3; i++) {
                if (v[i] > m[i]) return true;
                if (v[i] < m[i]) return false;
            }
            return true;
        } catch (Exception e) {
            return true;
        }
    }

    private static int[] parseSemVer(String v) {
        String[] parts = v.replaceAll("[^0-9.]", "").split("\\.", 3);
        int[] result = new int[3];
        for (int i = 0; i < Math.min(parts.length, 3); i++) {
            try { result[i] = Integer.parseInt(parts[i]); } catch (NumberFormatException ignored) {}
        }
        return result;
    }
}
