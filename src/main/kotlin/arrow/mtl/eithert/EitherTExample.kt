package arrow.mtl.eithert

import arrow.core.*
import arrow.fx.rx2.ForObservableK
import arrow.fx.rx2.ObservableK
import arrow.fx.rx2.extensions.fx
import arrow.fx.rx2.extensions.observablek.monad.monad
import arrow.fx.rx2.fix
import arrow.mtl.EitherT
import arrow.mtl.extensions.eithert.monad.monad
import arrow.mtl.value

data class Country(val code: String)
data class Address(val id: Int, val country: Option<Country>)
data class Person(val id: Int, val name: String, val address: Option<Address>)

sealed class BizError {
    data class PersonNotFound(val personId: Int) : BizError()
    data class AddressNotFound(val personId: Int) : BizError()
    data class CountryNotFound(val addressId: Int) : BizError()
}

typealias PersonNotFound = BizError.PersonNotFound
typealias AddressNotFound = BizError.AddressNotFound
typealias CountryNotFound = BizError.CountryNotFound

val personDB: Map<Int, Person> = mapOf(
        1 to Person(
                id = 1,
                name = "Alfredo Lambda",
                address = Some(
                        Address(
                                id = 1,
                                country = Some(
                                        Country(
                                                code = "ES"
                                        )
                                )
                        )
                )
        )
)

val addressDB: Map<Int, Address> = mapOf(
        1 to Address(
                id = 1,
                country = Some(
                        Country(
                                code = "ES"
                        )
                )
        )
)

fun findPerson(personId: Int): ObservableK<Either<BizError, Person>> =
        ObservableK.just(
                Option.fromNullable(personDB[personId]).toEither { PersonNotFound(personId) }
        ) //mock impl for simplicity

fun findPerson2(personId: Int): ObservableK<Either<BizError, Person>> =
        ObservableK.just(
                PersonNotFound(personId).left()
        )

fun findCountry(addressId: Int): ObservableK<Either<BizError, Country>> =
        ObservableK.just(
                Option.fromNullable(addressDB[addressId])
                        .flatMap { it.country }
                        .toEither { CountryNotFound(addressId) }
        ) //mock impl for simplicity

fun getCountryCode(personId: Int): ObservableK<Either<BizError, String>> =
        ObservableK.fx {
            val person = !findPerson(personId)
            val address = person.fold(
                    { it.left() },
                    { it.address.toEither { AddressNotFound(personId) } }
            )
            val maybeCountry = !address.fold(
                    { ObservableK.just(it.left()) },
                    { findCountry(it.id) }
            )
            val code = maybeCountry.fold(
                    { it.left() },
                    { it.code.right() }
            )
            code
        }

fun getCountryCodeWithMonadTransformer(personId: Int): ObservableK<Either<BizError, String>> =
        EitherT.monad<BizError, ForObservableK>(ObservableK.monad()).fx.monad {
            val person = !EitherT(findPerson2(personId))
            val address = !EitherT(ObservableK.just(
                    person.address.toEither { AddressNotFound(personId) }
            ))
            val address2 = person.address.toEither { AddressNotFound(personId) }
            val country = !EitherT(findCountry(address.id))
            country.code
        }.value().fix()

fun main() {
    println(getCountryCodeWithMonadTransformer(1).observable.blockingFirst())
}
