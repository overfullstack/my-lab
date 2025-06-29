package ga.overfullstack.immutables;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.immutables.value.Value;
import org.immutables.value.Value.Style.ImplementationVisibility;

@Target({ElementType.PACKAGE, ElementType.TYPE})
@Retention(RetentionPolicy.CLASS) // Make it class retention for incremental compilation
@Value.Style(
		// typeImmutable = "*", // No prefix or suffix for generated immutable type
		// typeAbstract = "*Def", // 'Def' suffix will be detected and trimmed
		builder = "configure", // construct builder using 'new' instead of factory method
		build = "done", // rename 'build' method on builder to 'create'
		depluralize =
				true, // automatic depluralization of attribute names for collection and map attributes
		add = "",
		visibility = ImplementationVisibility.PACKAGE) // Generate what gets exposed
public @interface PersonStyle {}
