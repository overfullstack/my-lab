package ga.overfullstack.immutable

import ga.overfullstack.immutable.style.MyStyle
import org.immutables.value.Value
import java.lang.reflect.Type

@MyStyle
@Value.Immutable
internal interface KickDef {
  fun templatePath(): String?

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

  fun stepConfig(): StepConfigDef

}

@MyStyle
@Value.Immutable
internal interface StepConfigDef {
  fun folder(): String

  fun step(): String?
}

enum class ValidationStrategy {
  FAIL_FAST,
}

private annotation class SkipNulls
