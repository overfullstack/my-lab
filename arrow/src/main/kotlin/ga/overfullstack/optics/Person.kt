package ga.overfullstack.optics

import arrow.optics.optics

@optics
object data class Person(val name: String, val age: Int, val address: Address) {
}

@optics
data class Address(val street: Street, val city: City, val coordinates: List<Int>) {
  companion object
}

@optics
data class Street(val name: String, val number: Int?) {
  companion object
}

@optics
data class City(val name: String, val country: String) {
  companion object
}
