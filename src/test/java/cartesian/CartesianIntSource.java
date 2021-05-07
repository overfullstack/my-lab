package cartesian;

import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CartesianIntSource.CartesianIntSources.class)
@ArgumentsSource(IntArgumentsProvider.class)
public @interface CartesianIntSource {

    int[] numbers() default {};

    @Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    @interface CartesianIntSources {
        CartesianIntSource[] value();
    }
}
