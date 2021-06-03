package immutables;

import org.immutables.value.Value;

import java.util.List;

@Value.Style(stagedBuilder = true)
@Value.Immutable
public interface Person {
    List<String> name();
    int age();
    boolean isEmployed();
}
