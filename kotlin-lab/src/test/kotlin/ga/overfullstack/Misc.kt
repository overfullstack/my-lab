package ga.overfullstack

import io.exoquery.pprint
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.io.path.Path
import kotlin.random.Random

class Misc :
  StringSpec({
    "some Test" {
      someFun()
    }
    
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

    "joinToString" {
      emptyList<String>().joinToString(separator = ", ", postfix = ",") shouldBe ","
    }

    "split" { "abc".split(",") shouldBe listOf("abc") }

    "path matches" { Path("a/b/c").endsWith("a/c") shouldBe true }
  }) {

  @Test
  fun `print data class`() {
    val p = Person(Name("Joe", "Bloggs"), 42)
    println(pprint(p))
  }

  @Test
  fun `empty list any`() {
    emptyList<Any>().any { true } shouldBe false
  }

  @Test
  fun `random hex`() {
    println("${getRandomHex()}${getRandomHex()}${getRandomHex()}")
  }

  @Test
  fun `zip unequal strings`() {
    "abc".zip("abcd").takeWhile { it.first == it.second }.size shouldBe 3
    "abc".zip("dabc").takeLastWhile { it.first == it.second }.size shouldBe 3
  }

  fun getRandomHex() = Random.nextInt(255).toString(16).uppercase()
}

fun someFun(a: String) = print(a)

fun someFun(vararg a: String) = print(a)

data class Name(val first: String, val last: String)

data class Person(val name: Name, val age: Int)
