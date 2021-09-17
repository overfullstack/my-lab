package ga.overfullstack

import java.util.AbstractMap.SimpleImmutableEntry
import java.util.function.Function

fun <T> someFun(
  entry: SimpleImmutableEntry<out String, out T>,
  fn1: Function<in T, out String>
) {
  TODO()
}

fun <T> someFun(
  member: Member<T>
) {
  val entry: SimpleImmutableEntry<String, T> = member.entry;
  val fn1: Function<T, String> = member.fn1;
  TODO()
}

fun caller(container: Container) {
  //someFun(container.member.entry, container.member.fn1)
  someFun(container.member)
  listOf(1,2,3).chunked(2)
}

