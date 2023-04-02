package ga.overfullstack.interop

object State {
  const val ON_MELTED_MESSAGE = "I melted"
  const val ON_FROZEN_MESSAGE = "I froze"
  const val ON_VAPORIZED_MESSAGE = "I vaporized"
  const val ON_CONDENSED_MESSAGE = "I condensed"

  sealed class State {
    object Solid : State()
    object Liquid : State()
    object Gas : State()
  }

  sealed class Event {
    object OnMelted : Event()
    object OnFrozen : Event()
    object OnVaporized : Event()
    object OnCondensed : Event()
  }

  sealed class SideEffect {
    object LogMelted : SideEffect()
    object LogFrozen : SideEffect()
    object LogVaporized : SideEffect()
    object LogCondensed : SideEffect()
  }

  interface Logger {
    fun log(message: String)
  }
}
