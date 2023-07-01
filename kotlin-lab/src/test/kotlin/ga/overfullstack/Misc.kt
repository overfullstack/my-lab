package ga.overfullstack

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.util.Optional

class Misc :
  StringSpec({
    "testOptionalGroupBy" {
      val list = listOf(Optional.of("a"), Optional.of("a"), Optional.of("c"))
      val group = list.groupBy { it }
      println(group)
    }

    "sub string after last" {
      "product-setup|>pre|>Login to ProductPricingAdmin".substringAfterLast("|>") shouldBe
        "Login to ProductPricingAdmin"
    }

    "vararg test" { someFun("a") }
  })

fun someFun(a: String) = print(a)

fun someFun(vararg a: String) = print(a)
