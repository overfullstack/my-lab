package ga.overfullstack.lombok.generics;

import com.google.common.reflect.TypeToken;
import java.lang.reflect.Type;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(buildMethodName = "prepare", builderMethodName = "toValidate", toBuilder = true)
public abstract class BaseValidationConfig<ValidatableT, FailureT> {
  protected final Type type1 = new TypeToken<ValidatableT>(getClass()) {}.getType(); // or getRawType() to return Class<? super T>  
  protected final Type type2 = new TypeToken<FailureT>(getClass()) {}.getType();
}
