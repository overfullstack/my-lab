package ga.overfullstack.immutable.style

import org.immutables.value.Value

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
@Value.Style(
  typeImmutable = "*",
  typeAbstract = ["*Def"],
  builder = "configure",
  build = "off",
  depluralize = true,
  add = "",
  visibility = Value.Style.ImplementationVisibility.PUBLIC
)
annotation class Config

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
@Value.Style(
  typeImmutable = "*",
  typeAbstract = ["*Def"],
  builder = "configure",
  build = "done",
  depluralize = true,
  add = "",
  visibility = Value.Style.ImplementationVisibility.PUBLIC
)
annotation class StepConfig
