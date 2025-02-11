package duke;

@FunctionalInterface
interface ThrowingFunction<T, R> {
    R apply(T t) throws Exception;
}
