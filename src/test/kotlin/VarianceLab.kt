import org.junit.jupiter.api.Test

/* gakshintala created on 4/20/20 */
class VarianceLab {
    @Test
    fun varianceBoth() {
        val a: VarianceBothOut<Int, String> = VarianceBothOut()
    }
}

class VarianceBothOut<out A, out B>
