package dsl

class Robot {
  infix fun turns(direction: Direction) = println("Robot turns $direction")
  
  infix fun runs(speed: Speed) = println("Robot runs $speed")

  companion object {
    infix fun operate(instructions: (Robot) -> Unit) =
      Robot().run { instructions(this) }
  }
}

enum class Direction { LEFT, RIGHT }
enum class Speed { FAST }

object DirectionSpeedReceiver {
  val left = Direction.LEFT
  val right = Direction.RIGHT
  val fast = Speed.FAST
}
