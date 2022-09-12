package ga.overfullstack.lombok;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter 
@Setter
@ToString
public class SomePojo {
    @NonNull
    private String name;
    private int age;
}
