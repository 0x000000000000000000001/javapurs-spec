    // Port of Assertions.js: unsafeStringify renders a value the way
    // JSON.stringify(x, null, 2) does, which the assertion messages use.
    public static Object unsafeStringify = (java.util.function.Function<Object, Object>) (value) ->
        __json(value, 0);

    private static String __jsonIndent(int depth) { return " ".repeat(depth); }

    private static String __jsonNumber(double n) {
        if (n == Math.rint(n) && Math.abs(n) < 1e21) return Long.toString((long) n);
        return Double.toString(n).replace('E', 'e');
    }

    private static String __jsonEscape(String s) {
        StringBuilder builder = new StringBuilder();
        for (char c : s.toCharArray()) {
            switch (c) {
                case '"': builder.append("\\\""); break;
                case '\\': builder.append("\\\\"); break;
                case '\n': builder.append("\\n"); break;
                case '\r': builder.append("\\r"); break;
                case '\t': builder.append("\\t"); break;
                default:
                    if (c < 0x20) builder.append(String.format("\\u%04x", (int) c));
                    else builder.append(c);
            }
        }
        return builder.toString();
    }

    private static String __json(Object value, int depth) {
        if (value == null) return "null";
        if (value instanceof String) return "\"" + __jsonEscape((String) value) + "\"";
        if (value instanceof Boolean) return value.toString();
        if (value instanceof Number) return __jsonNumber(((Number) value).doubleValue());
        if (value instanceof Object[]) {
            Object[] items = (Object[]) value;
            if (items.length == 0) return "[]";
            java.util.List<String> lines = new java.util.ArrayList<>();
            for (Object item : items) lines.add(__jsonIndent(depth + 2) + __json(item, depth + 2));
            return "[\n" + String.join(",\n", lines) + "\n" + __jsonIndent(depth) + "]";
        }
        if (value instanceof java.util.Map) {
            java.util.Map<String, Object> map = (java.util.Map<String, Object>) value;
            if (map.isEmpty()) return "{}";
            java.util.List<String> lines = new java.util.ArrayList<>();
            for (java.util.Map.Entry<String, Object> entry : map.entrySet()) {
                lines.add(__jsonIndent(depth + 2) + "\"" + __jsonEscape(entry.getKey()) + "\": " + __json(entry.getValue(), depth + 2));
            }
            return "{\n" + String.join(",\n", lines) + "\n" + __jsonIndent(depth) + "}";
        }
        // Constructors and other objects expose their fields like JS objects.
        java.util.List<String> fields = new java.util.ArrayList<>();
        for (java.lang.reflect.Field field : value.getClass().getFields()) {
            try {
                if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) continue;
                if (field.getName().equals("__order")) continue;
                fields.add(__jsonIndent(depth + 2) + "\"" + field.getName() + "\": " + __json(field.get(value), depth + 2));
            } catch (ReflectiveOperationException ignored) { }
        }
        if (fields.isEmpty()) return "{}";
        return "{\n" + String.join(",\n", fields) + "\n" + __jsonIndent(depth) + "}";
    }
