/* gakshintala created on 4/4/20 */
package arrow.fam

import arrow.core.Option
import arrow.core.Tuple2

fun getFirstName(userName: String): Option<String> = Option("firstName")
fun getLastName(userName: String): Option<String> = Option("lastName")

fun getFullName(userName: String) = getFirstName(userName).product(getLastName(userName))
        .map { names: Tuple2<String, String> -> "${names.a}  ${names.b}" }

fun <A, B> Option<A>.product(fb: Option<B>): Option<Tuple2<A, B>> =
        ap(fb.map { b: B -> { a: A -> Tuple2(a, b) } })

fun main() {
    println(getFullName(""))
}
