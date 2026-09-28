    // Port of Console.js: the console reporter writes straight to stdout.
    public static Object write = (java.util.function.Function<Object, Object>) (s) ->
        (java.util.function.Supplier<Object>) () -> { System.out.print((String) s); return null; };
