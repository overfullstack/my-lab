package labs;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class IOMonad {

    public static void main(String[] args) {
        // performIO is called only once, and only here
        functionalMain().performIO();
    }

    /**
     * An IO operation that results in a value of type T.
     */
    @FunctionalInterface
    interface IO<T> {
        /**
         * Must only be called by the execution environment!
         */
        T performIO();

        /**
         * Combines this IO operation with another IO operation which depends
         * on the result of this operation. (This is the monadic bind
         * operation.)
         */
        default <R> IO<R> flatMap(Function<T, IO<R>> f) {
            // Do not call performIO here: enforce lazy evaluation by returning
            // a wrapper, which will trigger the evaluation
            return () -> f.apply(this.performIO()).performIO();
        }

        /**
         * An IO operation that does not perform any IO and returns the given
         * value. (This is the monadic return operation.)
         */
        static <T> IO<T> value(T value) {
            return () -> value;
        }

        /**
         * An IO operation that does nothing and returns nothing.
         */
        static IO<Void> noop() {
            return value(null);
        }

        /**
         * Returns an IO operation that performs this operation, ignores its
         * result, and then performs the given operation and returns its
         * result.
         */
        default <R> IO<R> andThen(IO<R> io) {
            return flatMap(ignoredResult -> io);
        }

        /**
         * Combines a list of IO operations into a single IO operation which
         * executes the operations in the list sequentially, ignoring their
         * results.
         */
        static IO<Void> sequence(List<IO<?>> ios) {
            return ios.stream().reduce(noop(), IO::andThen)
                    .andThen(noop());
        }


        // ------ the implementations of IO<T> ------

        static IO<Void> print(Object s) {
            return () -> {
                System.out.println(s);
                return null;
            };
        }

        static IO<String> read() {
            return () -> new Scanner(System.in).next();
        }
    }

    static IO<Void> functionalMain() {
        // print "hello world"
        IO<Void> printHelloWorld = IO.print("Hello, world");

        // print a list of numbers
        var list = Arrays.asList(1, 2, 3);
        IO<Void> printNumbers = IO.sequence(list.stream()
                .map(IO::print)
                .collect(Collectors.toList()));

        // read a string from the console, then echo it
        IO<Void> echo = IO.print("Please enter your name:").andThen(
                IO.read().flatMap(name -> IO.print("Hello, " + name)));

        return IO.sequence(Arrays.asList(
                printHelloWorld,
                printNumbers,
                echo));
    }
}
