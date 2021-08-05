package immutables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link HeaderConfig}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableHeaderConfig.builder()}.
 */
@Generated(from = "HeaderConfig", generator = "Immutables")
@SuppressWarnings({"all"})
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
final class ImmutableHeaderConfig<HeaderValidatableT, FailureT>
    extends HeaderConfig<HeaderValidatableT, FailureT> {
  private final List<Function<HeaderValidatableT, List<?>>> batchMappers;

  private ImmutableHeaderConfig(List<Function<HeaderValidatableT, List<?>>> batchMappers) {
    this.batchMappers = batchMappers;
  }

  /**
   * @return The value of the {@code batchMappers} attribute
   */
  @Override
  public List<Function<HeaderValidatableT, List<?>>> batchMappers() {
    return batchMappers;
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link HeaderConfig#batchMappers() batchMappers}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  @SafeVarargs @SuppressWarnings("varargs")
  public final ImmutableHeaderConfig<HeaderValidatableT, FailureT> withBatchMappers(Function<HeaderValidatableT, List<?>>... elements) {
    List<Function<HeaderValidatableT, List<?>>> newValue = createUnmodifiableList(false, createSafeList(Arrays.asList(elements), true, false));
    return new ImmutableHeaderConfig<>(newValue);
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link HeaderConfig#batchMappers() batchMappers}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of batchMappers elements to set
   * @return A modified copy of {@code this} object
   */
  public final ImmutableHeaderConfig<HeaderValidatableT, FailureT> withBatchMappers(Iterable<? extends Function<HeaderValidatableT, List<?>>> elements) {
    if (this.batchMappers == elements) return this;
    List<Function<HeaderValidatableT, List<?>>> newValue = createUnmodifiableList(false, createSafeList(elements, true, false));
    return new ImmutableHeaderConfig<>(newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableHeaderConfig} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableHeaderConfig<?, ?>
        && equalTo((ImmutableHeaderConfig<?, ?>) another);
  }

  private boolean equalTo(ImmutableHeaderConfig<?, ?> another) {
    return batchMappers.equals(another.batchMappers);
  }

  /**
   * Computes a hash code from attributes: {@code batchMappers}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + batchMappers.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code HeaderConfig} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "HeaderConfig{"
        + "batchMappers=" + batchMappers
        + "}";
  }

  /**
   * Creates an immutable copy of a {@link HeaderConfig} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param <HeaderValidatableT> generic parameter HeaderValidatableT
   * @param <FailureT> generic parameter FailureT
   * @param instance The instance to copy
   * @return A copied immutable HeaderConfig instance
   */
  public static <HeaderValidatableT, FailureT> ImmutableHeaderConfig<HeaderValidatableT, FailureT> copyOf(HeaderConfig<HeaderValidatableT, FailureT> instance) {
    if (instance instanceof ImmutableHeaderConfig<?, ?>) {
      return (ImmutableHeaderConfig<HeaderValidatableT, FailureT>) instance;
    }
    return ImmutableHeaderConfig.<HeaderValidatableT, FailureT>builder()
        .batchMappers(instance.batchMappers())
        .prepare();
  }

  /**
   * Creates a builder for {@link ImmutableHeaderConfig ImmutableHeaderConfig}.
   * <pre>
   * ImmutableHeaderConfig.&amp;lt;HeaderValidatableT, FailureT&amp;gt;builder()
   *    .batchMapper|batchMappers(function.Function&amp;lt;HeaderValidatableT, List&amp;lt;?&amp;gt;&amp;gt;) // {@link HeaderConfig#batchMappers() batchMappers} elements
   *    .prepare();
   * </pre>
   * @param <HeaderValidatableT> generic parameter HeaderValidatableT
   * @param <FailureT> generic parameter FailureT
   * @return A new ImmutableHeaderConfig builder
   */
  public static <HeaderValidatableT, FailureT> ImmutableHeaderConfig.Builder<HeaderValidatableT, FailureT> builder() {
    return new ImmutableHeaderConfig.Builder<>();
  }

  /**
   * Builds instances of type {@link ImmutableHeaderConfig ImmutableHeaderConfig}.
   * Initialize attributes and then invoke the {@link #prepare()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "HeaderConfig", generator = "Immutables")
  public static final class Builder<HeaderValidatableT, FailureT>
      implements HeaderConfig.Builder<HeaderValidatableT, FailureT> {
    private final List<Function<HeaderValidatableT, List<?>>> batchMappers = new ArrayList<Function<HeaderValidatableT, List<?>>>();

    private Builder() {
    }

    /**
     * Adds one element to {@link HeaderConfig#batchMappers() batchMappers} list.
     * @param element A batchMappers element
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder<HeaderValidatableT, FailureT> batchMapper(Function<HeaderValidatableT, List<?>> element) {
      this.batchMappers.add(Objects.requireNonNull(element, "batchMappers element"));
      return this;
    }

    /**
     * Adds elements to {@link HeaderConfig#batchMappers() batchMappers} list.
     * @param elements An array of batchMappers elements
     * @return {@code this} builder for use in a chained invocation
     */
    @SafeVarargs @SuppressWarnings("varargs")
    public final Builder<HeaderValidatableT, FailureT> batchMappers(Function<HeaderValidatableT, List<?>>... elements) {
      for (Function<HeaderValidatableT, List<?>> element : elements) {
        this.batchMappers.add(Objects.requireNonNull(element, "batchMappers element"));
      }
      return this;
    }


    /**
     * Adds elements to {@link HeaderConfig#batchMappers() batchMappers} list.
     * @param elements An iterable of batchMappers elements
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder<HeaderValidatableT, FailureT> batchMappers(Iterable<? extends Function<HeaderValidatableT, List<?>>> elements) {
      for (Function<HeaderValidatableT, List<?>> element : elements) {
        this.batchMappers.add(Objects.requireNonNull(element, "batchMappers element"));
      }
      return this;
    }

    /**
     * Builds a new {@link ImmutableHeaderConfig ImmutableHeaderConfig}.
     * @return An immutable instance of HeaderConfig
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableHeaderConfig<HeaderValidatableT, FailureT> prepare() {
      return new ImmutableHeaderConfig<>(createUnmodifiableList(true, batchMappers));
    }
  }

  private static <T> List<T> createSafeList(Iterable<? extends T> iterable, boolean checkNulls, boolean skipNulls) {
    ArrayList<T> list;
    if (iterable instanceof Collection<?>) {
      int size = ((Collection<?>) iterable).size();
      if (size == 0) return Collections.emptyList();
      list = new ArrayList<>();
    } else {
      list = new ArrayList<>();
    }
    for (T element : iterable) {
      if (skipNulls && element == null) continue;
      if (checkNulls) Objects.requireNonNull(element, "element");
      list.add(element);
    }
    return list;
  }

  private static <T> List<T> createUnmodifiableList(boolean clone, List<T> list) {
    switch(list.size()) {
    case 0: return Collections.emptyList();
    case 1: return Collections.singletonList(list.get(0));
    default:
      if (clone) {
        return Collections.unmodifiableList(new ArrayList<>(list));
      } else {
        if (list instanceof ArrayList<?>) {
          ((ArrayList<?>) list).trimToSize();
        }
        return Collections.unmodifiableList(list);
      }
    }
  }
}
