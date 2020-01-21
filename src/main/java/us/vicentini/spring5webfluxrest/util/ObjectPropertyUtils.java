package us.vicentini.spring5webfluxrest.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;
import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectPropertyUtils {

    public static <T> boolean copyIfNonNull(Supplier<T> s, Consumer<T> c) {
        T value = s.get();
        if (value != null) {
            c.accept(value);
            return true;
        }
        return false;
    }
}
