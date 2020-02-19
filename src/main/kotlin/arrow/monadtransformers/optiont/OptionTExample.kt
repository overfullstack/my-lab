/* gakshintala created on 2/15/20 */
package arrow.monadtransformers.optiont

import arrow.core.Option
import arrow.core.Some
import arrow.core.extensions.fx
import arrow.fx.rx2.ObservableK
import arrow.fx.rx2.extensions.fx
import arrow.fx.rx2.extensions.observablek.monad.monad
import arrow.fx.rx2.fix
import arrow.mtl.OptionT
import arrow.mtl.extensions.fx


data class Country(val code: Option<String>)
data class Address(val id: Int, val country: Option<Country>)
data class Person(val name: String, val address: Option<Address>)

fun getCountryCode(maybePerson : Option<Person>): Option<String> =
        maybePerson.flatMap { person ->
            person.address.flatMap { address ->
                address.country.flatMap { country ->
                    country.code
                }
            }
        }

fun getCountryCodeFx(maybePerson : Option<Person>): Option<String> =
        Option.fx {
            val (person) = maybePerson
            val (address) = person.address
            val (country) = address.country
            val (code) = country.code
            code
        }

val personDB: Map<Int, Person> = mapOf(
        1 to Person(
                name = "Alfredo Lambda",
                address = Some(
                        Address(
                                id = 1,
                                country = Some(
                                        Country(
                                                code = Some("ES")
                                        )
                                )
                        )
                )
        )
)

val adressDB: Map<Int, Address> = mapOf(
        1 to Address(
                id = 1,
                country = Some(
                        Country(
                                code = Some("ES")
                        )
                )
        )
)

fun findPerson(personId : Int) : ObservableK<Option<Person>> =
        ObservableK.just(Option.fromNullable(personDB.get(personId))) //mock impl for simplicity

fun findCountry(addressId : Int) : ObservableK<Option<Country>> =
        ObservableK.just(
                Option.fromNullable(adressDB.get(addressId)).flatMap { it.country }
        ) //mock impl for simplicity

fun getCountryCode(personId: Int) =
        findPerson(personId).map { maybePerson ->
            maybePerson.map { person ->
                person.address.map { address ->
                    findCountry(address.id).map { maybeCountry ->
                        maybeCountry.map { country ->
                            country.code
                        }
                    }
                }
            }
        }

val lifted = { personId: Int -> getCountryCode(personId) }

fun getCountryCodeObservableFx(personId: Int): ObservableK<Option<String>> =
        ObservableK.fx {
            val maybePerson = findPerson(personId).bind()
            val person = maybePerson.fold(
                    { ObservableK.raiseError<Person>(NoSuchElementException("...")) },
                    { ObservableK.just(it) }
            ).bind()
            val address = person.address.fold(
                    { ObservableK.raiseError<Address>(NoSuchElementException("...")) },
                    { ObservableK.just(it) }
            ).bind()
            val maybeCountry = findCountry(address.id).bind()
            val country = maybeCountry.fold(
                    { ObservableK.raiseError<Country>(NoSuchElementException("...")) },
                    { ObservableK.just(it) }
            ).bind()
            country.code
        }

fun getCountryCodeOptionT(personId: Int): ObservableK<Option<String>> =
        OptionT.fx(ObservableK.monad()) {
            val person = !OptionT(findPerson(personId))
            val address = !OptionT(ObservableK.just(person.address))
            val country = !OptionT(findCountry(address.id))
            val code = !OptionT(ObservableK.just(country.code))
            code
        }.value().fix()
