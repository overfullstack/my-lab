package ga.overfullstack.immutable

import org.immutables.value.Value

@Config
@Value.Immutable
internal interface PQPayloadDef {
  fun qliCount(): Int?

  fun bundleConfig(): BundleConfigDef?

  fun productTypeRatio(): Map<String, Int>

  @Value.Check
  fun check() {
    require(listOfNotNull(qliCount(), bundleConfig()).size == 1) {
      "Supply either qliCount or bundleConfig, but not both"
    }
    require(qliCount()?.let { it < 0 } ?: true) {
      "'nonEmptyNumbers' should have at least one number"
    }
  }
}

@Config
@Value.Immutable
internal interface BundleConfigDef {
  fun parentQLICount(): Int

  fun relationshipCountPerParentQLI(): Int
}

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
@Value.Style(
  typeImmutable = "*",
  typeAbstract = ["*Def"],
  builder = "configure",
  build = "done",
  depluralize = true,
  add = "",
  visibility = Value.Style.ImplementationVisibility.PUBLIC,
)
annotation class Config
