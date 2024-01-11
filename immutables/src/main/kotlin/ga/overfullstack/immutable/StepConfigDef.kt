package ga.overfullstack.immutable

import ga.overfullstack.immutable.style.StepConfig
import org.immutables.value.Value

@StepConfig
@Value.Immutable
internal interface StepConfigDef {
  fun folder(): String?

  fun nest(): StepConfigDef?

  fun runOnlySteps(): Set<String>

  fun skipSteps(): Set<String>
}
