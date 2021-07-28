package dsl

import dsl.Direction.LEFT
import dsl.DirectionSpeedReceiver.right
import org.junit.jupiter.api.Test

internal class RobotTest {
    @Test
    fun robotTest() {
        Robot operate {
            it turns LEFT
            it runs Speed.FAST
            it turns right
        }
    }
}
