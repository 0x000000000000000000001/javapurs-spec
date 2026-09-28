    // Port of Assertions/String.js.
    public static Object _startsWith = (java.util.function.Function<Object, Object>) (subs) ->
        (java.util.function.Function<Object, Object>) (str) ->
            ((String) str).startsWith((String) subs);

    public static Object _endsWith = (java.util.function.Function<Object, Object>) (subs) ->
        (java.util.function.Function<Object, Object>) (str) ->
            ((String) str).endsWith((String) subs);
