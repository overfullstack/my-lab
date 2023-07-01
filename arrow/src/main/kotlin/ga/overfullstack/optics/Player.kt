package ga.overfullstack.optics

import arrow.optics.Lens

data class Player(val health: MutableList<Int>)

fun main() {
  val playerLens: Lens<Player, MutableList<Int>> =
    Lens(get = { player -> player.health }, set = { player, value -> player.copy(health = value) })

  val player = Player(mutableListOf(70))
  val list = playerLens.get(player)
  list.add(80)
  println(player)
}
