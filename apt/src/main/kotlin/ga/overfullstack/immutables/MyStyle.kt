package ga.overfullstack.immutables

import org.immutables.value.Value

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
@Value.Style(
  typeImmutable = "*",
  typeAbstract = ["*Def"],
  builder = "configure",
  build = "done",
  depluralize = true,
  add = "",
  visibility = Value.Style.ImplementationVisibility.PACKAGE)
annotation class MyStyle
