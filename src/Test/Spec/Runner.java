    // Port of Runner.js: process.exit for the command-line runner. The test
    // harness reads the JVM exit code, so exiting here is the same contract.
    public static Object exit = (java.util.function.Function<Object, Object>) (code) ->
        (java.util.function.Supplier<Object>) () -> { System.exit(((Number) code).intValue()); return null; };
