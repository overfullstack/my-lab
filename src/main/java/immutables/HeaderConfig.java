package immutables;

import org.immutables.value.Value;
import org.immutables.value.Value.Style.ImplementationVisibility;

import java.util.List;
import java.util.function.Function;

/*@Value.Style(stagedBuilder = true, depluralize = true, visibility = ImplementationVisibility.PACKAGE, build = "prepare")
@Value.Immutable*/
public abstract class HeaderConfig<HeaderValidatableT, FailureT> {
    /*public abstract List<Function<HeaderValidatableT, List<?>>> batchMappers();

    public static <HeaderValidatableT, FailureT> Builder<HeaderValidatableT, FailureT> toBuild() {
        return ImmutableHeaderConfig.builder();
    }
    public interface Builder<HeaderValidatableT, FailureT> {
        Builder batchMappers(List<Function<HeaderValidatableT, List<?>>> batchMappers);
        HeaderConfig<HeaderValidatableT, FailureT> prepare();
    }*/
}
