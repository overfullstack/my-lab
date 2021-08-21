package immutables;

import org.immutables.value.Value;

@Value.Style(visibility = Value.Style.ImplementationVisibility.PACKAGE)
@Value.Immutable
public interface Point {
  public static Point of(double x, double y) {
    return ImmutablePoint.of(x, y);
  }

  public static Builder builder() {
    return ImmutablePoint.builder();
  }

  @Value.Parameter
  public abstract double x();

  @Value.Parameter
  public abstract double y();

  // Signatures of abstract methods should match to be
  // overridden by implementation builder
  interface Builder {
    Builder x(double x);

    Builder y(double y);

    Point build();
  }
}
