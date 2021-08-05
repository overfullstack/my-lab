package immutables;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link Point}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutablePoint.builder()}.
 * Use the static factory method to create immutable instances:
 * {@code ImmutablePoint.of()}.
 */
@Generated(from = "Point", generator = "Immutables")
@SuppressWarnings({"all"})
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
final class ImmutablePoint implements Point {
  private final double x;
  private final double y;

  private ImmutablePoint(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * @return The value of the {@code x} attribute
   */
  @Override
  public double x() {
    return x;
  }

  /**
   * @return The value of the {@code y} attribute
   */
  @Override
  public double y() {
    return y;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Point#x() x} attribute.
   * A value strict bits equality used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for x
   * @return A modified copy of the {@code this} object
   */
  public final ImmutablePoint withX(double value) {
    if (Double.doubleToLongBits(this.x) == Double.doubleToLongBits(value)) return this;
    return new ImmutablePoint(value, this.y);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Point#y() y} attribute.
   * A value strict bits equality used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for y
   * @return A modified copy of the {@code this} object
   */
  public final ImmutablePoint withY(double value) {
    if (Double.doubleToLongBits(this.y) == Double.doubleToLongBits(value)) return this;
    return new ImmutablePoint(this.x, value);
  }

  /**
   * This instance is equal to all instances of {@code ImmutablePoint} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutablePoint
        && equalTo((ImmutablePoint) another);
  }

  private boolean equalTo(ImmutablePoint another) {
    return Double.doubleToLongBits(x) == Double.doubleToLongBits(another.x)
        && Double.doubleToLongBits(y) == Double.doubleToLongBits(another.y);
  }

  /**
   * Computes a hash code from attributes: {@code x}, {@code y}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + Double.hashCode(x);
    h += (h << 5) + Double.hashCode(y);
    return h;
  }

  /**
   * Prints the immutable value {@code Point} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "Point{"
        + "x=" + x
        + ", y=" + y
        + "}";
  }

  /**
   * Construct a new immutable {@code Point} instance.
   * @param x The value for the {@code x} attribute
   * @param y The value for the {@code y} attribute
   * @return An immutable Point instance
   */
  public static ImmutablePoint of(double x, double y) {
    return new ImmutablePoint(x, y);
  }

  /**
   * Creates an immutable copy of a {@link Point} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable Point instance
   */
  public static ImmutablePoint copyOf(Point instance) {
    if (instance instanceof ImmutablePoint) {
      return (ImmutablePoint) instance;
    }
    return ImmutablePoint.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutablePoint ImmutablePoint}.
   * <pre>
   * ImmutablePoint.builder()
   *    .x(double) // required {@link Point#x() x}
   *    .y(double) // required {@link Point#y() y}
   *    .build();
   * </pre>
   * @return A new ImmutablePoint builder
   */
  public static ImmutablePoint.Builder builder() {
    return new ImmutablePoint.Builder();
  }

  /**
   * Builds instances of type {@link ImmutablePoint ImmutablePoint}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "Point", generator = "Immutables")
  public static final class Builder implements Point.Builder {
    private static final long INIT_BIT_X = 0x1L;
    private static final long INIT_BIT_Y = 0x2L;
    private long initBits = 0x3L;

    private double x;
    private double y;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code Point} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(Point instance) {
      Objects.requireNonNull(instance, "instance");
      x(instance.x());
      y(instance.y());
      return this;
    }

    /**
     * Initializes the value for the {@link Point#x() x} attribute.
     * @param x The value for x 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder x(double x) {
      this.x = x;
      initBits &= ~INIT_BIT_X;
      return this;
    }

    /**
     * Initializes the value for the {@link Point#y() y} attribute.
     * @param y The value for y 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder y(double y) {
      this.y = y;
      initBits &= ~INIT_BIT_Y;
      return this;
    }

    /**
     * Builds a new {@link ImmutablePoint ImmutablePoint}.
     * @return An immutable instance of Point
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutablePoint build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutablePoint(x, y);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_X) != 0) attributes.add("x");
      if ((initBits & INIT_BIT_Y) != 0) attributes.add("y");
      return "Cannot build Point, some of required attributes are not set " + attributes;
    }
  }
}
