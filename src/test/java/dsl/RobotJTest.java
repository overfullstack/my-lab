package dsl;

import kotlin.Unit;
import org.junit.jupiter.api.Test;

class RobotJTest {
    @Test
    void robotTest() {
          Robot.Companion.operate(robot -> {
              robot.turns(Direction.left);
              return Unit.INSTANCE;
          });      
    }
}
