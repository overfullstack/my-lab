package ga.overfullstack.immutables;

import org.immutables.value.Value;

/** Expressive factory methods */
@Value.Immutable
public interface Point {
  @Value.Parameter
  double x();

  @Value.Parameter
  double y();

  static Point origin() {
    return ImmutablePoint.of(0, 0);
  }

  static Point of(double x, double y) {
    return ImmutablePoint.of(x, y);
  }

  static Point fromPolar(double r, double t) {
    return ImmutablePoint.of(r * Math.cos(t), r * Math.sin(t));
  }
}
