import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.Tuple2
import arrow.core.extensions.either.applicativeError.raiseError
import arrow.core.extensions.option.applicativeError.applicativeError
import arrow.core.extensions.validated.applicativeError.raiseError

fun main() {
    println(Some { a: Int -> a + 3 } apply Some(2))
    // => Some(5)

    println(Some(2).ap(Some { a: Int -> a + 3 }))
    // => Some(5)
    
    println(Some(2).product(Some(3))
            .map { "${it.a} + ${it.b}" })
    // => Some(2 + 3)

    println(None.product(Some(3)))
    // => None
    
    println(Some(3).product(None))
    // => None

    val array = arrayOf<(Int) -> Int>({ it + 3 }, { it * 2 }) apply arrayOf(1, 2, 3)
    println("[ ${array.joinToString()} ]")
    // => [ 4, 5, 6, 2, 4, 6 ]

    // Option.Some(3) map ::curriedAddition map Option.Some(2)
    // => Compiler error!

    println(Some(3).map(::curriedAddition) apply Some(2))
    // => Some(5)

    println(Some(3).map(::curriedTimes) apply Some(5))
    // => Some(15)

    println(Some(3).map(curry(::tripleProduct)) apply Some(5) apply Some(4))
    // => Some(60)
}

inline infix fun <A, reified B> Array<(A) -> B>.apply(a: Array<A>) =
        Array(this.size * a.size) {
            this[it / a.size](a[it % a.size])
        }

fun curriedAddition(a: Int): (Int) -> Int = { b: Int ->
    a + b
}

fun curriedTimes(a: Int): (Int) -> Int = { b: Int ->
    a * b
}

fun tripleProduct(a: Int, b: Int, c: Int) = a * b * c

fun <A, B, C, D> curry(f: (A, B, C) -> D): (A) -> (B) -> (C) -> D = { a -> { b -> { c -> f(a, b, c) } } }

infix fun <A, B> Option<(A) -> B>.apply(f: Option<A>): Option<B> = when (this) {
    is None -> None
    is Some -> f.flatMap { a -> this.map { it(a) } }
}

fun <A, B> Option<A>.product(fb: Option<B>): Option<Tuple2<A, B>> =
        ap(fb.map { b: B -> { a: A -> Tuple2(a, b) } })
