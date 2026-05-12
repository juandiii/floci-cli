package io.floci.cli.output;

public final class Ansi {

    public static final String RESET  = "[0m";
    public static final String BOLD   = "[1m";
    public static final String GREEN  = "[32m";
    public static final String YELLOW = "[33m";
    public static final String RED    = "[31m";
    public static final String CYAN   = "[36m";
    public static final String GRAY   = "[90m";
    public static final String GOLD   = "[38;5;214m";

    private static boolean enabled = true;

    private Ansi() {}

    public static void disable() {
        enabled = false;
    }

    public static String apply(String code, String text) {
        if (!enabled) return text;
        return code + text + RESET;
    }

    public static String green(String text)  { return apply(GREEN, text); }
    public static String yellow(String text) { return apply(YELLOW, text); }
    public static String red(String text)    { return apply(RED, text); }
    public static String cyan(String text)   { return apply(CYAN, text); }
    public static String gray(String text)   { return apply(GRAY, text); }
    public static String bold(String text)   { return apply(BOLD, text); }
    public static String gold(String text)   { return apply(GOLD, text); }

    public static boolean isEnabled() { return enabled; }
}
