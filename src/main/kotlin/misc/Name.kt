package misc

import io.vavr.control.Either
import io.vavr.kotlin.right

@JvmInline
value class Name(val s: String) {
  val length: Int
    @get:JvmName("getLength")
    get() = s.length
}

fun getAsName(): Either<String, Name> = right(Name("Kotlin"))
