package ga.overfullstack

val sum: Int.(Int) -> Int = { other -> plus(other) }

fun calc(op1: Int, op2: Int, operation: Int.(Int) -> Int): Int = operation(op1, op2)
