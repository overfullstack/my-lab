package ga.overfullstack.immutables

import ga.overfullstack.immutables.style.MyStyle
import org.immutables.value.Value
import org.jetbrains.annotations.Nullable
import java.lang.reflect.Type

@MyStyle
@Value.Immutable
internal interface KickDef {
  fun templatePath(): String

  @Nullable
  fun environmentPath(): String?

  @SkipNulls
  fun dynamicEnvironment(): Map<String, String>

  @SkipNulls
  fun stepNameToErrorType(): Map<String, Type>

  @Value.Default
  fun validationStrategy(): ValidationStrategy = ValidationStrategy.FAIL_FAST

  @SkipNulls
  fun customAdaptersForResponse(): List<Any>

  @SkipNulls
  fun typesInResponseToIgnore(): Set<Class<out Any>>
  
  @Value.Default
  fun insecureHttp(): Boolean = false
  
}

enum class ValidationStrategy {
  FAIL_FAST,
}

private annotation class SkipNulls
