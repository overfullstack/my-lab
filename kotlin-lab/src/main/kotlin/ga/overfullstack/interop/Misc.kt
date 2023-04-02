package ga.overfullstack.interop

val sum: Int.(Int) -> Int = { other -> plus(other) }

fun calc(op1: Int, op2: Int, operation: Int.(Int) -> Int): Int = operation(op1, op2)

fun nameLen(bean: Bean, fn: Bean.() -> Int) = fn(bean) 
