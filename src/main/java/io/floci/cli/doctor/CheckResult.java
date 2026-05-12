package io.floci.cli.doctor;

public record CheckResult(
        String name,
        CheckStatus status,
        String message,
        String fix) {

    public static CheckResult ok(String name, String message) {
        return new CheckResult(name, CheckStatus.ok, message, null);
    }

    public static CheckResult warn(String name, String message, String fix) {
        return new CheckResult(name, CheckStatus.warn, message, fix);
    }

    public static CheckResult fail(String name, String message, String fix) {
        return new CheckResult(name, CheckStatus.fail, message, fix);
    }
}
