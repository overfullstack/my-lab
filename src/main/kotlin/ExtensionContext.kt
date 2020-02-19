class B {
    fun printB() = println("B")
}

class A {
    fun B.doBSomething() {
        printB()
    }
}

fun main() {
    val a = A()
    val b = B()
    with(a) {
        b.doBSomething() // this will work
    }
    someHigherFun { b.doBSomething() }
    // b.doBSomething() // won't compile
}

fun someHigherFun(f: A.() -> Unit) = f(A())
