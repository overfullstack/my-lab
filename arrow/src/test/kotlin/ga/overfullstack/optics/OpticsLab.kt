package ga.overfullstack.optics

class OpticsLab {

  /*@Test
  fun optics() {
    val me = Person(
      "Alejandro", 35,
      Address(Street("Kotlinstraat", 1), City("Hilversum", "Netherlands"))
    )

    Person.name.get(me) shouldBe "Alejandro"

    val meAfterBirthdayParty = Person.age.modify(me) { it + 1 }
    Person.age.get(meAfterBirthdayParty) shouldBe 36

    val newAddress = Address(Street("Kotlinplein", null), City("Amsterdam", "Netherlands"))
    val meAfterMoving = Person.address.set(me, newAddress)
    Person.address.get(meAfterMoving) shouldBe newAddress
  }*/

  /*@Test
  fun `optics composition`() {
    val personCity: Lens<Person, String> = Person.address compose Address.city compose City.name

    val me =
      Person(
        "Alejandro",
        35,
        Address(Street("Kotlinstraat", 1), City("Hilversum", "Netherlands"), listOf(1, 2))
      )

    personCity.get(me) shouldBe "Hilversum"
    val meAtTheCapital = personCity.set(me, "Amsterdam")
    meAtTheCapital.address.city.name shouldBe "Amsterdam"
  }

  @Test
  fun `optics copy to modify multiple fields`() {
    val me =
      Person(
        "Alejandro",
        35,
        Address(Street("Kotlinstraat", 1), City("Hilversum", "Netherlands"), listOf(1, 2))
      )
    val meAfterMoving1 = me.moveToAmsterdamInside()
    val meAfterMoving2 = me.moveToAmsterdamInside()
  }

  fun Person.moveToAmsterdamCopy(): Person = copy {
    Person.address.city.name set "Amsterdam"
    Person.address.city.country set "Netherlands"
    Person.address.coordinates set listOf(2, 3)
  }

  fun Person.moveToAmsterdamInside(): Person = copy {
    inside(Person.address.city) {
      City.name set "Amsterdam"
      City.country set "Netherlands"
    }
  }*/
}
