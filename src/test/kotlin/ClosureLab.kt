import org.junit.jupiter.api.Test

/* gakshintala created on 12/24/19 */

class ClosureLab {
    @Test
    fun `clousure should access outer state`() {
        var k = 3
        someFun {
            k--
            if (k == 0) {
                print("exhausted")
            }
        }
    }

    private fun someFun(closure: () -> Unit) {
        repeat(3) { closure() }
    }
}