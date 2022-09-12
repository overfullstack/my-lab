package ga.overfullstack.immutables;

import ga.overfullstack.immutables.ImmutablePerson.Builder;
import org.immutables.value.Value;

@PersonStyle
@Value.Immutable
public interface Person {
  int age();

  boolean isEmployed();

  @Value.Default
  default boolean isMarried() {
    return false;
  }

  /**
   * Control what gets exposed
   */
  static Builder configure() {
    return ImmutablePerson.configure();
  }
}
