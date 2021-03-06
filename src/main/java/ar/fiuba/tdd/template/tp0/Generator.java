package ar.fiuba.tdd.template.tp0;

import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by paulo on 24/03/16.
 */
public class Generator {
    private static final Random RANDOM = new Random();
    private static final int START_ASCII = 33;
    private static final int END_ASCII = 127;
    private int maxLength;

    public Generator(int maxLength) {
        this.maxLength = maxLength;
    }

    public Stream<String> identity(String str) {
        return Stream.of(str);
    }

    public Stream<String> appendRandom(String str) {
        return RANDOM
                .ints(START_ASCII, END_ASCII)
                .mapToObj(i -> str + (char) i)
                .limit(maxLength);
    }

    public String notEmptyApply(String str, Function<String, String> fn) {
        if (str.isEmpty()) {
            return str;
        } else {
            return fn.apply(str);
        }
    }

    public Stream<String> repeatLast(String astr) {
        return Stream.of(notEmptyApply(astr, str -> str + str.charAt(str.length() - 1)));

    }

    public Stream<String> removeLast(String astr) {
        return Stream.of(notEmptyApply(astr, str -> str.substring(0, str.length() - 1)));
    }

    public Function<String, Stream<String>> seq(Function<String, Stream<String>>... fns) {
        return str -> Stream.of(fns).flatMap(f -> f.apply(str));
    }

    public Function<String, Stream<String>> appendChar(char chr) {
        return str -> Stream.of(str + chr);
    }

    public Function<String, Stream<String>> appendAlternatives(List<Character> alternatives) {
        return str -> alternatives.stream().map(chr -> str + chr);
    }
}
