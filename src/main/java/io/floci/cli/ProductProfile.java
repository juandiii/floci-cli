package io.floci.cli;

public record ProductProfile(
        String name,
        String defaultImage,
        String defaultContainer,
        int defaultPort,
        String endpointEnvVar,
        String containerEnvVar,
        String persistPath,
        String servicesEnvVar
) {
    public static final ProductProfile AWS = new ProductProfile(
            "aws", "floci/floci:latest", "floci", 4566,
            "FLOCI_ENDPOINT", "FLOCI_CONTAINER", "/var/lib/floci", "FLOCI_SERVICES"
    );

    public static final ProductProfile AZ = new ProductProfile(
            "az", "floci/floci-az:latest", "floci-az", 4577,
            "FLOCI_AZ_ENDPOINT", "FLOCI_AZ_CONTAINER", "/app/data", "FLOCI_AZ_SERVICES"
    );
}
