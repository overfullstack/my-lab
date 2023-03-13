class SuccessConfig private constructor(successType: String, validationConfig: String? = null) {
  val successType: String = successType
  val validationConfig: String? = validationConfig

  companion object {
    @JvmStatic
    fun successType(successType: String): SuccessConfig {
      return SuccessConfig(successType, null)
    }

    @JvmStatic
    fun validateIfSuccess(successType: String, validationConfig: String): SuccessConfig {
      return SuccessConfig(successType, validationConfig)
    }
  }
}
