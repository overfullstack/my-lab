package ga.overfullstack.immutable.style

import org.immutables.value.Value
import org.immutables.value.Value.Style.ImplementationVisibility.PUBLIC

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
@Value.Style(
  typeImmutable = "*",
  typeAbstract = ["*Def"],
  builder = "configure",
  build = "off",
  put = "*",
  add = "*",
  depluralize = true,
  depluralizeDictionary = ["templatePath:templatePathsInOrder"],
  visibility = PUBLIC
)
annotation class Config

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
@Value.Style(
  typeImmutable = "*",
  typeAbstract = ["*Def"],
  builder = "configure",
  build = "done",
  put = "*",
  add = "*",
  depluralize = true,
  visibility = PUBLIC
)
annotation class StepConfig
