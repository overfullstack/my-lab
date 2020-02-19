import org.junit.jupiter.api.Test

/* gakshintala created on 12/24/19 */

class ClosureLab {
    class State(k: Int) {
        var result = -1
        val closureWrapper = {
            var kth = 0
            {
                kth++
                if (kth == k) {
                    result = k
                    kth = 0
                }
            }
        }
    }
    

    @Test
    fun `closure wrapper as class member should access instance state`() {
        val k = 3
        val state = State(k)
        println(state.result)
        someOtherFun(state.closureWrapper())
        println(state.result)
        repeat(3) { state.closureWrapper()() }
        println(state.result)
    }

    private inline fun someOtherFun(closure: () -> Unit) {
        repeat(3) { closure() }
        repeat(3) { closure() }
    }

    @Test
    fun `closure inside function should access local variable`() {
        var k = 3
        val closure = {
            k--
        }
        someFun(closure)
        println()
        repeat(3) { print(closure()) }
    }

    private fun someFun(closure: () -> Int) {
        repeat(3) { print(closure()) }
        println()
        repeat(3) { print(closure()) }
    }
    
    @Test
    fun foo() {
        fooFun {
            if (it == 3) {
                println()
                return
            } // non-local return directly to the caller of foo()
            print(it)
        }
    }

    private inline fun fooFun(forEachFn: (Int) -> Unit) {
        listOf(1, 2, 3, 4, 5).forEach (forEachFn)
        println("this point is unreachable")
    }
}