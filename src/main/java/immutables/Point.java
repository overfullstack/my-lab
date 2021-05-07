package immutables;

import org.immutables.value.Value;
import org.immutables.value.Value.Style.ImplementationVisibility;

import java.util.List;

@Value.Style(visibility = ImplementationVisibility.PACKAGE, build = "prepare", depluralize = true, add = "")
@Value.Immutable
public abstract class Point {
  public abstract List<String> xs();
  public abstract double y();

  public static Builder toBuild() {
    return ImmutablePoint.builder();
  }
  // Signatures of abstract methods should match to be
  // overridden by implementation builder
  public interface Builder {
    Builder xs(Iterable<String> x);
    Builder x(String x);
    Builder y(double y);
    Point prepare();
  }
}
