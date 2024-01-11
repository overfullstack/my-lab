package ga.overfullstack.immutable

import ga.overfullstack.immutable.style.Config
import java.lang.reflect.Type
import org.immutables.value.Value

@Config
@Value.Immutable
internal interface KickDef {

  fun templatePathsInOrder(): List<String>

  fun environmentPath(): String?

  fun dynamicEnvironment(): Map<String, String>

  fun stepNameToErrorType(): Map<String, Type>

  fun hooks(): Set<Set<String>>

  @Value.Derived fun hooksFlattened(): List<String> = hooks().flatten()

  @Value.Default fun validationStrategy(): ValidationStrategy = ValidationStrategy.FAIL_FAST

  @Value.Default fun customAdaptersForResponse(): Set<Any> = emptySet()

  fun typesInResponseToIgnore(): Set<Class<out Any>>

  @Value.Default fun insecureHttp(): Boolean = false

  fun stepConfig(): StepConfigDef?
}

enum class ValidationStrategy {
  FAIL_FAST,
}
