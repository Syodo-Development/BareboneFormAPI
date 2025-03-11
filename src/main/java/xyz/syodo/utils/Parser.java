package xyz.syodo.utils;

import java.util.UUID;

public class Parser {
    public static <T> T parseValue(String str, ParserFunction<T> parser, T defaultValue) {
        try {
            return parser.parse(str.trim());
        } catch (Exception ignore) {
            return defaultValue;
        }
    }

    public interface ParserFunction<T> {
        T parse(String str) throws Exception;
    }

    public static final ParserFunction<Float> FLOAT_PARSER = Float::parseFloat;
    public static final ParserFunction<Double> DOUBLE_PARSER = Double::parseDouble;
    public static final ParserFunction<Integer> INTEGER_PARSER = Integer::parseInt;
    public static final ParserFunction<Long> LONG_PARSER = Long::parseLong;
    public static final ParserFunction<UUID> UUID_PARSER = UUID::fromString;
}
