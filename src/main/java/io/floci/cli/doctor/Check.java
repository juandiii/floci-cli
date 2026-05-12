package io.floci.cli.doctor;

@FunctionalInterface
public interface Check {
    CheckResult run(String endpoint, String container);
}
