package dsl

import dsl.Direction.left
import dsl.DirectionSpeedReceiver.right
import org.junit.jupiter.api.Test

internal class RobotTest {
    @Test
    fun robotTest() {
        Robot operate {
            it turns left
            it runs Speed.fast
            it turns right
        }
    }
}
