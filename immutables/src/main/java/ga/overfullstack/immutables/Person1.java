package ga.overfullstack.immutables;

import ga.overfullstack.immutables.ImmutablePerson1.Builder;
import java.util.List;
import org.immutables.value.Value;

@PersonStyle
@Value.Immutable
public interface Person1 {
	int age();

	boolean isEmployed();

	List<String> hobbies();

	@Value.Default
	default boolean isMarried() {
		return false;
	}

	/** Control what gets exposed */
	static Builder configure() {
		return ImmutablePerson1.configure();
	}
}
