package cartesian;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junitpioneer.jupiter.CartesianAnnotationConsumer;

import java.lang.reflect.Array;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class IntArgumentsProvider implements CartesianAnnotationConsumer<CartesianIntSource>, ArgumentsProvider {

    private Object[] arguments;

    @Override
    public void accept(CartesianIntSource source) {
        List<Object> arrays = Stream.<Object>of(source.numbers()).collect(toList());
        Object originalArray = arrays.get(0);
        arguments = IntStream
                .range(0, Array.getLength(originalArray)) //
                .mapToObj(index -> Array.get(originalArray, index)) //
                .toArray();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(Arguments.of(arguments));
    }

}
