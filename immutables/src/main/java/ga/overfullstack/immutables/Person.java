package ga.overfullstack.immutables;

import org.immutables.value.Value;
import org.immutables.value.Value.Style.ImplementationVisibility;

@Value.Immutable
@Value.Style(visibility = ImplementationVisibility.PRIVATE)
public interface Person {
	String getName();

	String getAddress();
}
