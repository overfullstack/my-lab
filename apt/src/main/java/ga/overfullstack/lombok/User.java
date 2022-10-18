package ga.overfullstack.lombok;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@ToString
public class User {
  @NonNull private String name;
  private int age;
}

@SuperBuilder
@Getter
@Setter
@ToString(callSuper = true)
class Admin extends User {
  private int id;
}